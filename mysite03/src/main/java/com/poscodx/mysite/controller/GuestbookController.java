package com.poscodx.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.GuestbookService;
import com.poscodx.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("")
	public String main(Model model) {
		List<GuestbookVo> list = guestbookService.getContentsList();
		model.addAttribute("list", list);
		
		return "guestbook/main";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST)
	public String delete(@PathVariable("no") Long no, @RequestParam String password) {
		guestbookService.deleteContents(no, password);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@ModelAttribute GuestbookVo vo) {
		/* ModelAttribute 생략 가능
		 * @ModelAttribute : 화면에서 넘어온 파라미터들을 이용해서 DTO 생성 후 바로 받을 수 있다.
		*/
		guestbookService.addContents(vo);
		return "redirect:/guestbook";
	}
	
	
	
}
