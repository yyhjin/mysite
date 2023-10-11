package com.poscodx.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public SiteVo find() {
		return sqlSession.selectOne("site.find");
	}
	
	public boolean update(SiteVo vo) {
		int count = sqlSession.update("site.update", vo);
		return count == 1;
	}

}
