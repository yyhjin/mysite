package com.poscodx.mysite.web.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.GuestbookDao;
import com.poscodx.web.mvc.Action;

public class DeleteAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String no = request.getParameter("no");
		String password = request.getParameter("password");
		
		if(password.equals(new GuestbookDao().findPasswordByNo(Integer.parseInt(no)))) {
			new GuestbookDao().deleteByNo(no);	
		}
		
		response.sendRedirect("/mysite02/guestbook");
	}

}
