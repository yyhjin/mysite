package com.poscodx.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.UserRepository;
import com.poscodx.mysite.vo.UserVo;

@Service
public class UserService {
//	@Autowired
//	private MailSender mailSender;

	@Autowired
	private UserRepository userRepository;
	
	public void addUser(UserVo userVo) {
		System.out.println(userVo);

		userRepository.insert(userVo);
		
		// 방금 insert된 데이터의 primary key가 매핑됨 (*user.xml 참고)
		System.out.println(userVo);
		
//		mailSender.send(vo.getEmail(), "", "");
	}

	public UserVo getUser(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	public UserVo getUser(Long no) {
		return userRepository.findByNo(no);
	}

	public void update(UserVo userVo) {
		userRepository.update(userVo);
	}
	
	
}
