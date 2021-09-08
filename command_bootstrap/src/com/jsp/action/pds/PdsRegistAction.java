package com.jsp.action.pds;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.controller.FileUploadResolver;
import com.jsp.controller.XSSResolver;
import com.jsp.dto.AttachVO;
import com.jsp.dto.PdsVO;
import com.jsp.exception.NotMultipartFormDataException;
import com.jsp.service.PdsService;
import com.jsp.util.ExceptionLoggerHelper;
import com.jsp.util.GetUploadPath;
import com.jsp.util.MultipartHttpServletRequestParser;

public class PdsRegistAction implements Action{
	private PdsService pdsService;

	public void setPdsService(PdsService pdsService) {
		this.pdsService = pdsService;
	}
	// 1.입력: commons-fileupload.jar 패키지를 이용하여 file
	final private int MEMORY_THRESHOLD = 1024  * 1024 * 3;
	final private int MAX_FILE_SIZE = 1024  * 1024 * 40;
	final private int MAX_REQUEST_SIZE = 1024  * 1024 * 200;
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url="pds/regist_success";
		
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");
		
		MultipartHttpServletRequestParser multi = null;
		List<AttachVO> attachList = null;
		try {
		multi = new MultipartHttpServletRequestParser(request,MEMORY_THRESHOLD,MAX_FILE_SIZE,MAX_REQUEST_SIZE);
		
		//실제 저장 경로를 설정
		String uploadPath = GetUploadPath.getUploadPath("pds.upload");
		
		//파일 저장후 List<File>을 리턴..
		List<File> fileList = FileUploadResolver.fileUpload(multi.getFileItems("uploadFile"), uploadPath);
	
		//List<File> -> List<AttachVO>
		if(fileList != null) {
			attachList = new ArrayList<AttachVO>();
			for(File file : fileList) {
				//DBㅔ 저장할 attach에 file 내용 추가
				AttachVO attach = new AttachVO();
				attach.setFileName(file.getName());
				attach.setUploadPath(uploadPath);
				attach.setFileType(file.getName().substring(file.getName().lastIndexOf(".")+1));
				
				attachList.add(attach);
			}
		}
		
		}catch(NotMultipartFormDataException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
			ExceptionLoggerHelper.write(request, e, new FileUploadResolver());
		}catch(Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		
		PdsVO pds = new PdsVO();
		pds.setTitle(multi.getXSSParameter("title"));
		pds.setContent(multi.getParameter("content"));
		pds.setWriter(multi.getParameter("writer"));
		pds.setAttachList(attachList);
		
		try {
			pdsService.regist(pds);
			
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionLoggerHelper.write(request, e, pdsService);
			throw e;
		}
		
		return url;
	}

}
