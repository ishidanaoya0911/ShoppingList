package com.zdrv.app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.zdrv.app.domain.User;

@Mapper
public interface UserDao {

	User selectByLoginId(String loginId);

}
