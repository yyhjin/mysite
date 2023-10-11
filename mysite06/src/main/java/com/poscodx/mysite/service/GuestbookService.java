package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.GuestbookRepository;
import com.poscodx.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestbookRepository guestbookRepository;

	public List<GuestbookVo> getContentsList() {
		return guestbookRepository.findAll();
	}
	
	public Boolean deleteContents(Long no, String password) {
		boolean result = false;
		if(password.equals(guestbookRepository.findPasswordByNo(no))) {
			result = guestbookRepository.deleteByNo(no);	
		}
		return result;
	}
	
	public Boolean addContents(GuestbookVo vo) {
		boolean result = guestbookRepository.insert(vo);
		return result;
	}
	
}
