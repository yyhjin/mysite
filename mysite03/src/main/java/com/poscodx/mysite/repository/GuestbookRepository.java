package com.poscodx.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
//	@Autowired
//	private DataSource dataSource;
//	dataSource.getConnection();
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}

	public String findPasswordByNo(long no) {
		String password = sqlSession.selectOne("guestbook.findPasswordByNo", no);
		return password;
	}
	
	public boolean insert(GuestbookVo vo) {
		int count = sqlSession.insert("guestbook.insert", vo);
		return count == 1;
	}

	public boolean deleteByNo(Long no) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		
		int count = sqlSession.delete("guestbook.deleteByNo", map);
		return count == 1;
	}
	
}
