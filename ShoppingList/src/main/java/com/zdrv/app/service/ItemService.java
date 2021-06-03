package com.zdrv.app.service;

import java.util.List;

import com.zdrv.app.domain.Item;

public interface ItemService {

	List<Item> getItemList();

	List<Item> getItemListById(Integer listId);

	Item getItemById(Integer id);

	void addItem(Item item);

	void editItem(Item item);

	void deleteItem(Integer id);

}
