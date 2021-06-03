package com.zdrv.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zdrv.app.domain.Item;

@Mapper
public interface ItemDao {

	List<Item> selectAll();

	List<Item> selectAllByListId(Integer listId);

	Item selectById(Integer id);

	void insert(Item item);

	void update(Item item);

	void delete(Integer id);

}
