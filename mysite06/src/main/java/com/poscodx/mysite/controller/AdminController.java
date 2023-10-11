package com.poscodx.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.FileUploadService;
import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

@Auth(Role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	// spring container
	@Autowired
	private ApplicationContext applicationContext;
	
	// tomcat container. interceptor  사용할 떄 필요
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping("")
	public String main(Model model) {
		SiteVo siteVo = siteService.getSite();
		model.addAttribute("siteVo", siteVo);
		return "admin/main";  
	}
	
	@RequestMapping(value="/main/update", method=RequestMethod.POST)
	public String update(@RequestParam("file") MultipartFile file, SiteVo vo) {
		String url = fileUploadService.restore(file);
		vo.setProfile(url);
		siteService.updateSite(vo);
		
		SiteVo newVo = siteService.getSite();
		
		/* ServletContext 이용한 방법 */
		servletContext.setAttribute("siteVo", newVo);
		
		/* ApplicationContext 이용한 방법 */
		SiteVo site = applicationContext.getBean(SiteVo.class);
//		site.setTitle(newVo.getTitle());
//		site.setWelcome(newVo.getWelcome());
//		site.setProfile(newVo.getProfile());
//		site.setDescription(newVo.getDescription());
		BeanUtils.copyProperties(newVo, site);  // 위의 주석 코드 이걸로 대체 가능, newVo의 값을 site에 copy해오는 것
		
		return "redirect:/admin";  
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";  
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";  
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";  
	}
	
	
}
