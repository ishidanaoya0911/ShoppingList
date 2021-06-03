package com.zdrv.app.domain;

import java.util.List;

import lombok.Data;

@Data
public class ItemListForm {
	// 表示する商品情報のリスト
	private List<Item> itemList;

	// Itemクラスの同名メソッドをリスト全体に適応する
	public void setKeywordsFromList() {
		for (Item item : itemList) {
			item.setKeywordsFromList();
		}
	}

}
