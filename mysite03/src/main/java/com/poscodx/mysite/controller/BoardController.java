package com.poscodx.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.PaginationVo;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = {"", "/{page}"})
	public String list(@PathVariable(value="page", required=false) Integer page, 
						@RequestParam(value="k", required=false, defaultValue="") String keyword,
						Model model) {

		if(page == null) page = 1;
		
		/* 페이징 */
		int pageSize = 5;
		int listSize = boardService.findAllCount(keyword);
		int totalPage = listSize / pageSize;
		if(listSize%pageSize != 0) totalPage++;

		int curPage = page;
		int startPage = (curPage - 1) / pageSize * pageSize + 1;
		int endPage = startPage + pageSize - 1;
		if (endPage > totalPage)
			endPage = totalPage;
				
		/* 페이지 번호에 해당하는 리스트 */		
		List<BoardVo> list = boardService.findAll((curPage-1)*5, keyword);
		model.addAttribute("list", list);
		model.addAttribute("keyword", keyword);
		
		PaginationVo pageVo = new PaginationVo();
		pageVo.setPageSize(pageSize);
		pageVo.setTotalPage(totalPage);
		pageVo.setStartPage(startPage);
		pageVo.setEndPage(endPage);
		pageVo.setCurPage(curPage);
		
		model.addAttribute("pageVo", pageVo);
		return "board/list";
	}
	
	@RequestMapping("/view/{no}/{page}")
	public String view(@PathVariable("no") long no, 
						@PathVariable("page") int page,
						Model model) {
		
		BoardVo boardVo = boardService.findByNo(no);
		boardVo.setNo(no);
		boardService.updateHit(no);
				
		model.addAttribute("vo", boardVo);
		model.addAttribute("curPage", page);
		
		return "board/view";
	}
	
	@RequestMapping("/delete/{no}/{page}")
	public String delete(@PathVariable long no,
						@PathVariable int page,
						Model model) {
		
		boardService.deleteByNo(no);
		
		model.addAttribute("p", page);
		return "redirect:/board";
	}
	
	@RequestMapping(value={"/write", "/write/{no}/{page}"}, method=RequestMethod.GET) 
	public String write(@PathVariable(value="no", required=false) Long no, 
						@PathVariable(value="page", required=false) Integer page, 
						Model model) {
		if(no == null) {
			return "board/write";			
		}
		else {
			System.out.println("답글");
			model.addAttribute("n", no);
			model.addAttribute("p", page);
			return "board/write";
		}
	}
	
	@RequestMapping(value="/write/{no}/{page}", method=RequestMethod.POST)
	public String write(HttpSession session,
						@PathVariable("no") Integer boardNo,
						@PathVariable("page") Integer page,
						BoardVo boardVo) {
		
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		Long userNo = userVo.getNo();
		
		int groupNo = -1;
		int orderNo = 1;
		int depth = 0;
		int curPage = 1;
		
		// 게시글 작성
		if(boardNo == null) {
			groupNo = boardService.getMaxGroup() + 1;
			orderNo = 1;
			depth = 1;
		}
		// 답글 작성
		else {
			System.out.println(boardNo);
			
			BoardVo parentVo = boardService.findByNo(boardNo);
			groupNo = parentVo.getGroupNo();
			orderNo = parentVo.getOrderNo()+1;
			depth  = parentVo.getDepth()+1;
			curPage = page;
			
			boardService.updateOrderNo(groupNo, parentVo.getOrderNo());
		}
		
		boardVo.setUserNo(userNo);
		boardVo.setGroupNo(groupNo);
		boardVo.setOrderNo(orderNo);
		boardVo.setDepth(depth);
				
		boardService.insert(boardVo);
		
		return "redirect:/board/"+curPage;
	}
	
	@RequestMapping(value="/modify/{no}/{page}", method=RequestMethod.GET) 
	public String modify(@PathVariable Long no, 
						@PathVariable Integer page, 
						Model model) {
		
		BoardVo boardVo = boardService.findByNo(no);
		model.addAttribute("vo", boardVo);
		model.addAttribute("curPage", page);
		
		return "board/modify";
	}
	
	@RequestMapping(value="/modify/{no}/{page}", method=RequestMethod.POST) 
	public String modify(@PathVariable Long no, 
						@PathVariable Integer page, 
						BoardVo boardVo) {
		
		boardVo.setNo(no);
		boardService.updateBoard(boardVo);
		
		return "redirect:/board/view/"+no+"/"+page;
	}
	
	
	
}
