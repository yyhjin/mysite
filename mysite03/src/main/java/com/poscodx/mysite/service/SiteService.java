package com.poscodx.mysite.service;

import org.springframework.stereotype.Service;

import com.poscodx.mysite.vo.SiteVo;

@Service
public class SiteService {
	
	public SiteVo getSite() {
		SiteVo vo = new SiteVo();
		return vo;
	}
	
}
