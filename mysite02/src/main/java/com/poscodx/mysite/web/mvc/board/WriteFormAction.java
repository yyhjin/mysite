package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class WriteFormAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getParameter("n") == null) {
			WebUtil.forward("board/write", request, response);
		}
		else {
			long no = Long.parseLong(request.getParameter("n"));
			int curPage = Integer.parseInt(request.getParameter("p"));
			
			request
				.getRequestDispatcher("/WEB-INF/views/board/write.jsp?n=" + no + "&p=" + curPage)
				.forward(request, response);
		}
		
	}

}
