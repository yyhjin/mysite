package com.poscodx.mysite.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
import com.poscodx.mysite.service.UserService;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result, Model model) {
		if(result.hasErrors()) {
//			List<ObjectError> list = result.getAllErrors();
//			for(ObjectError error: list) {
//				System.out.println(error);
//			}
			
			model.addAllAttributes(result.getModel());
//			model.addAttribute("userVo", userVo);  // 얘를 없애기 위해서 @ModelAttribute 붙여준 것
			return "user/join";
		}
		
		userService.addUser(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping("/auth")
	public void auth() {	
	}
	   
	@RequestMapping("/logout")
	public void logout() {

	}
	
	/* Interceptor에서 처리 */
//	@RequestMapping(value="/auth", method=RequestMethod.POST)
//	public String auth(@RequestParam(value="email", required=true, defaultValue="") String email, 
//						@RequestParam(value="password", required=true, defaultValue="") String password,
//						HttpSession session, 
//						Model model) {
//		
//		UserVo authUser = userService.getUser(email, password);
//		if(authUser == null) {
//			model.addAttribute("email", email);
//			return "user/login";
//		}
//		
//		/* 인증 처리 */
//		session.setAttribute("authUser", authUser);
//		
//		return "redirect:/";
//	}
//	
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		session.removeAttribute("authUser");
//		session.invalidate();
//		
//		return "redirect:/";
//	}
	
	
//	@Auth
//	@RequestMapping(value="/update", method=RequestMethod.GET)
//	public String update(HttpSession session, Model model) {
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		
//		// Access Control (접근 제어)
//		// url로 접근하려 하는 경우 막기 위한 것. 구식적인 방법
//		if(authUser == null) {
//			return "redirect:/user/login";
//		}
//		/* --------------------------------------- */
//		
//		UserVo userVo = userService.getUser(authUser.getNo());
//		model.addAttribute("userVo", userVo);
//		
//		return "user/update";
//	}
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {
		UserVo userVo = userService.getUser(authUser.getNo());
		model.addAttribute("userVo", userVo);
		
		return "user/update";
	}
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, @ModelAttribute UserVo userVo) {		
		userVo.setNo(authUser.getNo());
		userService.update(userVo);
		
		// 세션을 레퍼런싱해서 가져오기 때문에 authUser 바꿔줘도 세션 값이 바뀜
		// authUser 세션값을 복사해서 가져오는 것이 아님. new를 사용한 것이 아니기 때문에 레퍼런스타입임
		// 기본적으로 레퍼런스 타입을 사용한다
		authUser.setName(userVo.getName());
		
		return "redirect:/user/update";
	}
	
	// 이건 컨트롤러 별로 만들어야 하기 때문에 코드 중복 발생
	// Controller에 모인 Exception을 한번에 처리. (Exception.class)-모든 에러
	// 따라서 tomcat 에러는 볼 수 없을 것임(500 error)
//	@ExceptionHandler(Exception.class)
//	public String handlerException() {	
//		return "error/exception";
//	}
	
}
