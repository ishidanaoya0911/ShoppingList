package com.zdrv.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zdrv.app.domain.Item;
import com.zdrv.app.domain.ItemList;
import com.zdrv.app.domain.ItemListForm;
import com.zdrv.app.service.ItemService;

@Controller
@RequestMapping("/list")
public class ListController {
	@Autowired
	private ItemService itemService;

	@GetMapping
	public String top() {
		// 購入履歴にリダイレクト
		return "redirect:/list/" + ItemList.ID_HISTORY;
	}

	@GetMapping("/{listId}")
	public String list(
			@PathVariable Integer listId,
			Model model) {
		// 渡されたリストIDが不正なら強制リダイレクト
		if (!ItemList.checkListId(listId)) {
			return "redirect:/list/" + ItemList.ID_HISTORY;
		}

		ItemListForm itemListForm = new ItemListForm();
		itemListForm.setItemList(itemService.getItemListById(listId));
		itemListForm.setKeywordsFromList();
		model.addAttribute("listId", listId);
		model.addAttribute("listNames", ItemList.NAMES);
		model.addAttribute("itemListForm", itemListForm);
		return "itemList";
	}

	@PostMapping("/{listId}")
	public String listPost(
			@PathVariable Integer listId,
			@ModelAttribute ItemListForm itemListForm,
			Model model) {
		// 渡されたリストIDが不正なら強制リダイレクト
		if (!ItemList.checkListId(listId)) {
			return "redirect:/list/" + ItemList.ID_HISTORY;
		}

		// チェックボックスにチェックがついている商品を削除
		for (Item item : itemListForm.getItemList()) {
			if (item.getIsDelete()) {
				System.out.println("delete:" + item);
				itemService.deleteItem(item.getId());
			}
		}
		return "redirect:/list/" + listId;
	}

}
