package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class BoardListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		/* 페이징 */
		int pageSize = 5;
		int listSize = new BoardDao().findAllCount();
		int totalPage = listSize / pageSize;
		if(listSize%pageSize != 0) totalPage++;

		int curPage = Integer.parseInt(request.getParameter("p"));
		int startPage = (curPage - 1) / pageSize * pageSize + 1;
		int endPage = startPage + pageSize - 1;
		if (endPage > totalPage)
			endPage = totalPage;
		
		System.out.println("total: " + totalPage + ", start: "+startPage+", end: "+endPage + ", current: "+curPage);
		
		/* 페이지에 맞는 리스트 */
		List<BoardVo> list = new BoardDao().findAll((curPage-1)*5);
		request.setAttribute("list", list);
		
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("curPage", curPage);
		
		WebUtil.forward("board/list", request, response);
	}
}
