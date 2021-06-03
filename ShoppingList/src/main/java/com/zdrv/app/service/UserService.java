package com.zdrv.app.service;

import javax.servlet.http.HttpSession;

import com.zdrv.app.domain.User;

public interface UserService {

	Boolean checkLogin(User input, HttpSession session);

}
