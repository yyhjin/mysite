package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		long no = Long.parseLong(request.getParameter("n"));
		int curPage = Integer.parseInt(request.getParameter("p"));

		BoardVo boardVo = new BoardDao().findByNo(no);
		request.setAttribute("vo", boardVo);
		request.setAttribute("curPage", curPage);
		WebUtil.forward("board/modify", request, response);
	}

}
