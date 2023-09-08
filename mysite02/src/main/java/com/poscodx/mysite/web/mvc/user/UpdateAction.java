package com.poscodx.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class UpdateAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		UserVo userVo = new UserVo();
		userVo.setNo(Long.parseLong(no));
		userVo.setName(name);
		userVo.setEmail(email);
		userVo.setPassword(password);
		userVo.setGender(gender);
		
		boolean result = new UserDao().update(userVo);
		
		if(result) {
			UserVo vo = new UserDao().findByEmailAndPassword(email, password);
			
			HttpSession session = request.getSession();
			session.setAttribute("authUser", vo);
			
			UserVo userInfo = new UserDao().findByNo(vo.getNo());
			request.setAttribute("userInfo", userInfo);
			
			WebUtil.forward("user/updateform", request, response);
		}
	}

}
