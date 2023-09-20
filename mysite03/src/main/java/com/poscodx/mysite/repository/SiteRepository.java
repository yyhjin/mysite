package com.poscodx.mysite.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	@Autowired
	private SiteRepository siteRepository;
	
	public SiteVo find() {
		return null;
	}
	
	public void update(SiteVo vo) {
		
	}
	
}
