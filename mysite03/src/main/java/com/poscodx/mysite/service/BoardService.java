package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	BoardRepository boardRepository;

	public int findAllCount(String search) {
		return boardRepository.findAllCount(search);
	}

	public List<BoardVo> findAll(int page, String keyword) {
		return boardRepository.findAll(page, keyword);
	}

	public BoardVo findByNo(long no) {
		return boardRepository.findByNo(no);
	}

	public void updateHit(long no) {
		boardRepository.updateHit(no);
	}

	public void deleteByNo(long no) {
		boardRepository.deleteByNo(no);
	}

	public int getMaxGroup() {
		return boardRepository.getMaxGroup();
	}

	public void updateOrderNo(int groupNo, int orderNo) {
		boardRepository.updateOrderNo(groupNo, orderNo);
	}

	public void insert(BoardVo boardVo) {
		boardRepository.insert(boardVo);
	}

	public void updateBoard(BoardVo boardVo) {
		boardRepository.updateBoard(boardVo);
	}
	
	
	
}
