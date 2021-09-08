package com.jsp.action.notice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.controller.XSSResolver;
import com.jsp.dto.NoticeVO;
import com.jsp.service.NoticeService;

public class NoticeModifyAction implements Action {
private NoticeService noticeService;
	
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url="redirect:notice/detail.do?from=modify&nno="+request.getParameter("nno");
		
		XSSResolver.parseXSS(request);
		
		int nno = Integer.parseInt(request.getParameter("nno"));
		String title = (String)request.getAttribute("XSStitle");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		 NoticeVO vo = new NoticeVO();
		 vo.setNno(nno);
		 vo.setTitle(title);
		 vo.setWriter(writer);
		 vo.setContent(content);
		
		 noticeService.modify(vo);
		 
		
		return url;
	}

}
