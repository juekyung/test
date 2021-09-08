package com.jsp.action.reply;

import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsp.action.Action;
import com.jsp.request.PageMaker;
import com.jsp.request.ReplyRegistCommand;
import com.jsp.request.SearchCriteria;
import com.jsp.service.ReplyService;

public class ReplyRegistAction implements Action{
	private ReplyService replyService;
	
	public void setReplyService(ReplyService replyService) {
		this.replyService = replyService;
	}
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url=null;
		
		ObjectMapper mapper = new ObjectMapper();
		
		ReplyRegistCommand replyReq = mapper.readValue(request.getReader(), ReplyRegistCommand.class);
		
		try {
			
			replyService.registReply(replyReq.toReplyVO());
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	
		//위 아래를 나눈이유는 위에는 등록을 실패했을시 에러를 내보내고 아래는 등록시 페이지번호를 부여해주는거이므로 구분을 줘서 try catch를 해준거임
		int realEndPage = 1;
		String result="";
		try {
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(new SearchCriteria());
			pageMaker.setTotalCount(replyService.getReplyListCount(replyReq.toReplyVO().getBno()));
			
			realEndPage = pageMaker.getRealEndPage();
			
			result="SUCCESS,";
			
		}catch(SQLException e) {
			e.printStackTrace();
			result="Fail,";
		}
		PrintWriter out = response.getWriter();
		out.print(result+realEndPage);
		return url;
	}
	
}
