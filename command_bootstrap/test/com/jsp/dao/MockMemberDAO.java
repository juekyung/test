package com.jsp.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jsp.dto.MemberVO;

public class MockMemberDAO implements MemberDAO {

	@Override
	public MemberVO selectMemberById(SqlSession session, String id) throws SQLException {
		
		MemberVO member=null;
		
		if(id.equals("mimi")) {
			member = new MemberVO();
			member.setId("mimi");
			member.setPwd("mimi");
			member.setName("mimi");
		}
		
		return member;
	}

	@Override
	public List<MemberVO> selectMemberList(SqlSession session) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertMember(SqlSession session, MemberVO member) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMember(SqlSession session, MemberVO member) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMember(SqlSession session, String id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabledMember(SqlSession session, String id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enabledMember(SqlSession session, String id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
