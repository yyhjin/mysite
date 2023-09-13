package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;

public class WriteAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UserVo userVo = (UserVo) request.getSession().getAttribute("authUser");
		Long userNo = userVo.getNo();
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		int groupNo = Integer.parseInt(request.getParameter("group"));
		int orderNo = 1;
		int depth = 0;
		
		// 게시글 작성
		if(groupNo == 0) {
			groupNo = new BoardDao().getMaxGroup() + 1;
			orderNo = 1;
			depth = 1;
		}
		// 답글 작성
//		else {
//			헤당 글의 no도 넘겨받아야 할 것 같음, no로 depth, order_no 찾아서 처리//
//		
//			orderNo = new BoardDao().getMax
//		}
		
		BoardVo vo = new BoardVo(); 
		vo.setUserNo(userNo);
		vo.setTitle(title);
		vo.setContents(content);
		vo.setGroupNo(groupNo);
		vo.setOrderNo(orderNo);
		vo.setDepth(depth);
		
		new BoardDao().insert(vo);

		response.sendRedirect("/mysite02/board");
	}

}
