package com.zdrv.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zdrv.app.dao.ItemDao;
import com.zdrv.app.dao.KeywordDao;
import com.zdrv.app.domain.Item;
import com.zdrv.app.domain.Keyword;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemDao ItemDao;
	@Autowired
	private KeywordDao keywordDao;

	@Override
	public List<Item> getItemList() {
		List<Item> itemList = ItemDao.selectAll();
		for (Item item : itemList) {
			item.setKeywordsFromList();
		}
		return itemList;
	}

	@Override
	public List<Item> getItemListById(Integer listId) {
		List<Item> itemList = ItemDao.selectAllByListId(listId);
		return itemList;
	}

	@Override
	public Item getItemById(Integer id) {
		Item item = ItemDao.selectById(id);
		return item;
	}

	@Override
	public void addItem(Item item) {
		ItemDao.insert(item);
		addKeywordList(item);
	}

	@Override
	public void editItem(Item item) {
		ItemDao.update(item);
		// 現在設定されている関連情報を全削除
		keywordDao.deleteRelation(item.getId());
		addKeywordList(item);
	}

	@Override
	public void deleteItem(Integer id) {
		ItemDao.delete(id);
		keywordDao.deleteRelation(id);
	}

	// 関連情報を含めてキーワードを登録
	private void addKeywordList(Item item) {
		List<Keyword> keywordList = item.getKeywordList();

		// リストからキーワードが設定されてない要素を削除
		keywordList.removeIf(keyword -> keyword.getWord().isBlank());
		System.out.println("keywordList:" + keywordList);

		for (Keyword keyword : keywordList) {
			// キーワードが登録されているか検索
			Keyword search = keywordDao.selectByWord(keyword.getWord());
			// 登録がない場合
			if (search == null) {
				// キーワード自体を新規追加
				keywordDao.insert(keyword);
			}
			else {
				// IDをコピー
				keyword.setId(search.getId());
			}
			System.out.println("set keyword:" + keyword);

			// 商品とキーワードの関連情報を追加
			keywordDao.insertRelation(item.getId(), keyword.getId());
		}
	}

}
