package com.jsp.action.pds;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.jsp.action.Action;
import com.jsp.controller.FileUploadResolver;
import com.jsp.dto.AttachVO;
import com.jsp.dto.PdsVO;
import com.jsp.service.PdsService;
import com.jsp.util.GetUploadPath;
import com.jsp.util.MultipartHttpServletRequestParser;

public class PdsModifyAction implements Action{
	private PdsService pdsService;
	
	public void setPdsService(PdsService pdsService) {
		this.pdsService = pdsService;
	}
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url="pds/modify_success";
		
		PdsVO pds = modifyAttaches(request,response);
		pdsService.modify(pds);
		
		request.setAttribute("pds", pds);
		
		return url;
	}
	// 수정,등록할때 필요
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
	private static final int MAX_REQUEST = 1024 * 1024 * 200;
	
	private PdsVO modifyAttaches(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException,Exception{
		PdsVO pds = null;
		
		MultipartHttpServletRequestParser multi = new MultipartHttpServletRequestParser(request, MEMORY_THRESHOLD, MAX_FILE_SIZE, MAX_REQUEST);
		
		//파일 삭제 및 DB삭제(delteFile찾아서 for문 돌린다음 번호를 서비스에서 찾아서 attach를 통해 파일 경로를 찾아서  파일의 존재를 찾아서 삭제후 서비스에서도 삭제)
		String[] deleteFiles = multi.getParameterValues("deleteFile");
		if(deleteFiles != null && deleteFiles.length > 0) {
			for(String anoStr : deleteFiles) {
				int ano = Integer.parseInt(anoStr);
				AttachVO attach = pdsService.getAttachByAno(ano);
				
				String filePath = attach.getUploadPath() + File.separator + attach.getFileName();
				File targetFile = new File(filePath);
				
				if(targetFile.exists()) {
					targetFile.delete(); //파일 삭제
				}
				pdsService.removeAttachByAno(ano); // DB 삭제 
			}
		}
		// 추가된 파일 저장
		FileItem[] fileItems = multi.getFileItems("uploadFile");
		
		String uploadPath = GetUploadPath.getUploadPath("pds.upload");
		List<File> fileList = FileUploadResolver.fileUpload(fileItems, uploadPath);
		
		List<AttachVO> attachList = new ArrayList<AttachVO>();
		if(fileList.size() > 0)for(File file: fileList) {
			AttachVO attach = new AttachVO();
			attach.setFileName(file.getName());
			attach.setUploadPath(uploadPath);
			attach.setFileType(file.getName().substring(file.getName().lastIndexOf(".")+1));
			
			attachList.add(attach);
		}
		
		pds = new PdsVO();
		pds.setPno(Integer.parseInt(multi.getParameter("pno")));
		pds.setTitle(multi.getParameter("title"));
		pds.setWriter(multi.getParameter("writer"));
		pds.setContent(multi.getParameter("content"));
		pds.setAttachList(attachList);
		
		return pds;
	}
}
