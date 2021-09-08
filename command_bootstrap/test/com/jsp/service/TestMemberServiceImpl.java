package com.jsp.service;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.jsp.dao.MemberDAO;
import com.jsp.dao.MockMemberDAO;
import com.jsp.datasource.MockSqlSessionFactory;

public class TestMemberServiceImpl {	

	
	//private SqlSessionFactory sqlSessionFactory= new OracleMybatisSqlSessionFactory(); //검증된 object	
	private SqlSessionFactory sqlSessionFactory = new MockSqlSessionFactory();
	
	private MemberDAO memberDAO;
	private MemberServiceImpl memberService;
	
	@Before
	public void init() {
		memberDAO = new MockMemberDAO();  //검증된 DAO
		
		
		memberService = new MemberServiceImpl();
		memberService.setSqlSessionFactory(sqlSessionFactory);
		memberService.setMemberDAO(memberDAO);
	}
	
	@Test
	public void testLogin()throws Exception{
		String testID = "mimi",testPwd="mimi";
		
		memberService.login(testID, testPwd);
	}
}






