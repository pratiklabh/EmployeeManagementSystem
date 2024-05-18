package com.bway.springproject.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class GalleryController {
	
	
	@GetMapping("/gallery")
	public String getGallery(Model model, HttpSession session ) {
		
		if(session.getAttribute("validuser") == null) {
		
			return "LoginForm";
			
		}
		
		
		String[] imgList = new File("src/main/resources/static/image").list();
		//right click on "image" and properties and copy path and paste above
		
		model.addAttribute("imgList",imgList);
		return "Gallery";
		
	}

}
