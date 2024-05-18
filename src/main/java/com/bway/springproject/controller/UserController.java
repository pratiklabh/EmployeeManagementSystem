package com.bway.springproject.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.springproject.model.User;
import com.bway.springproject.service.UserService;
import com.bway.springproject.utils.VerifyRecaptcha;

import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;

@Log

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping({ "/", "/login" })
	public String user() {
		return "LoginForm";
	}

	@PostMapping("/login")
	public String postLogin(@ModelAttribute User user, Model model, HttpSession session,
			@RequestParam("g-recaptcha-response") String gcapCode) throws IOException {

		if (VerifyRecaptcha.verify(gcapCode)) {

			user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
			User usr = userService.userLogin(user.getEmail(), user.getPassword());

			if (usr != null) {

				log.info("------User Login Success------");

				session.setAttribute("validuser", usr);
				session.setMaxInactiveInterval(120);

				model.addAttribute("uname", usr.getFname());
				return "Home";
			}else {
				log.info("-------- Login Failed --------");
				model.addAttribute("error", "user not found");
				return "LoginForm";
			}

		}

		log.info("-------- Login Failed --------");
		model.addAttribute("error", "Are You Robot?");
		return "LoginForm";
	}

	@GetMapping("/signup")
	public String getSignup() {
		return "SignupForm";
	}

	@PostMapping("/signup")
	public String postSignup(@ModelAttribute User user) {

		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userService.userSignup(user);
		return "LoginForm";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {

		log.info("-------Logout Success-------");
		session.invalidate(); // kills session
		return "LoginForm";
	}

}
