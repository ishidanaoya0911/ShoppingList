package com.zdrv.app.domain;

public class ItemList {
	// enumにするほうがいいらしいのだが勉強不足
	// リストIDを定義する定数
	public static final int ID_HISTORY = 0;
	public static final int ID_CHECK = 1;

	// リストの表示名
	public static final String[] NAMES = {
			"購入履歴",
			"チェック中リスト",
	};

	// リストIDが適正か判定する
	public static boolean checkListId(Integer listId) {
		switch (listId) {
		case ID_HISTORY:
		case ID_CHECK:
			return true;
		default:
			return false;
		}
	}

}
