package com.zdrv.app.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zdrv.app.domain.User;
import com.zdrv.app.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService service;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	// ログイン
	@GetMapping("/login")
	public String loginGet(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/login")
	public String loginPost(@Valid User user, Errors errors, HttpSession session) {
		if (errors.hasErrors()) {
			return "login";
		}

		if (!service.checkLogin(user, session)) {
			// Errorsオブジェクトにグローバルエラー情報を追加する
			errors.reject("error.user.login");
			return "login";
		}

		return "redirect:/list/";
	}

	// ログアウト
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

}
