package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> findAll(int page, String keyword) {
		Map<String, Object> map = new HashMap<>();
		map.put("page", page);
		map.put("keyword", keyword);
		
		List<BoardVo> list = sqlSession.selectList("board.findAll", map);
		return list;
	}
	
	public int findAllCount(String keyword) {
		int count = sqlSession.selectOne("board.findAllCount", keyword);
		return count;
	}
	
	public BoardVo findByNo(long no) {
		BoardVo boardVo = sqlSession.selectOne("board.findByNo", no);
		return boardVo;
	}

	public int getMaxGroup() {
		int max = sqlSession.selectOne("board.getMaxGroup");
		return max;
	}
	
	public boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
		return count == 1;
	}

	public boolean updateHit(long no) {
		int count = sqlSession.update("board.updateHit", no);
		return count == 1;
	}
	
	public boolean updateBoard(BoardVo vo) {
		int count = sqlSession.update("board.updateBoard", vo);
		return count == 1;
	}
	
	public boolean updateOrderNo(int groupNo, int orderNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("groupNo", groupNo);
		map.put("orderNo", orderNo);
		
		int count = sqlSession.update("board.updateOrderNo", map);
		return count > 0;
	}

	
	public boolean deleteByNo(long no) {
		int count = sqlSession.delete("board.deleteByNo", no);
		return count == 1;
	}
	
}
