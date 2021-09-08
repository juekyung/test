package com.jsp.action.member;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.dto.MemberVO;
import com.jsp.service.MemberService;
import com.jsp.util.GetUploadPath;

public class MemberRemoveAction implements Action {
	

	private MemberService memberService;
	public void setMemberService(MemberService memberService) {
		this.memberService=memberService;
	}
	
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url="member/remove_success";
		
		String id = request.getParameter("id");
		
		MemberVO member = memberService.getMember(id);
		
		//이미지 삭제
		String savedPath=GetUploadPath.getUploadPath("member.picture.upload");
		String fileName=member.getPicture();
		File picture = new File(savedPath,fileName);
		
		if(picture.exists()) {
			picture.delete();
		}
		
		//DB삭제
		memberService.remove(id);
		
		//로그인 유저 확인
		// 삭제되는 회원이 로그인 회원인경우 로그아웃 해야함.
		MemberVO loginUser = (MemberVO) request.getSession().getAttribute("loginUser");
		if (loginUser.getId().equals(member.getId())) {
			request.getSession().invalidate(); //session 갱신
		}
		
		return url;
	}

}
