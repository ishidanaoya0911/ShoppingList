package com.zdrv.app.service;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zdrv.app.dao.UserDao;
import com.zdrv.app.domain.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao dao;

	@Override
	public Boolean checkLogin(User input, HttpSession session) {
		User user = dao.selectByLoginId(input.getLoginId());

		// ログインIDが間違い
		if (user == null) {
			return false;
		}

		// パスワードが間違い
		if (!BCrypt.checkpw(input.getLoginPass(), user.getLoginPass())) {
			return false;
		}

		// セッションにログインIDを格納
		session.setAttribute("loginId", user.getLoginId());
		return true;
	}

}
