package com.fang.dao;

import com.fang.model.Member;

public interface MemberDAO {
	
	public Member selectMemberByEmail(String email);
	
	public void insertMember(Member member);
	
	public Member selectMemberByEmailAndPassword(Member member);
	
	public void updateMemberByPrimaryKey(Member member);
	
}
