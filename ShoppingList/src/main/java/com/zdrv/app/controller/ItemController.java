package com.zdrv.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zdrv.app.domain.Item;
import com.zdrv.app.domain.ItemList;
import com.zdrv.app.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;

	@InitBinder
	public void initBinderForm(WebDataBinder binder) {
		var dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@GetMapping("/{id}")
	public String detail(
			@PathVariable Integer id,
			Model model) {
		Item item = itemService.getItemById(id);
		item.setKeywordsFromList();
		System.out.println("item:" + item);

		model.addAttribute("item", item);
		model.addAttribute("listNames", ItemList.NAMES);
		return "item";
	}

	@GetMapping("/add/{listId}")
	public String add(
			@PathVariable Integer listId,
			Model model) {
		// 渡されたリストIDが不正なら強制リダイレクト
		if (!ItemList.checkListId(listId)) {
			return "redirect:/list/" + ItemList.ID_HISTORY;
		}

		Item item = new Item();
		item.setListId(listId);

		addAttributesForm(model, item, listId, "登録");
		return "itemForm";
	}

	@PostMapping("/add/*")
	public String addPost(
			@Valid Item item,
			Errors errors,
			Model model) {
		System.out.println("add post:" + item);

		// 追加バリデーション
		item.additionalValidation(errors);

		Integer listId = item.getListId();
		if (errors.hasErrors()) {
			addAttributesForm(model, item, listId, "登録");
			return "itemForm";
		}

		// チェックリストへの追加時は登録日時を更新
		if (listId == ItemList.ID_CHECK) {
			item.setDate(new Date());
		}

		itemService.addItem(item);
		System.out.println("add:" + item);

		return "redirect:/list/" + listId;
	}

	@GetMapping("/edit/{id}")
	public String editGet(
			@PathVariable int id,
			Model model) {
		Item item = itemService.getItemById(id);
		System.out.println("edit get:" + item);

		addAttributesForm(model, item, item.getListId(), "編集");
		return "itemForm";
	}

	@PostMapping("/edit/*")
	public String editPost(
			@Valid Item item,
			Errors errors,
			Model model) {
		System.out.println("edit post:" + item);

		// 追加バリデーション
		item.additionalValidation(errors);

		Integer listId = item.getListId();
		if (errors.hasErrors()) {
			addAttributesForm(model, item, listId, "編集");
			return "itemForm";
		}

		// チェックリストへの編集時は登録日時を更新
		if (listId == ItemList.ID_CHECK) {
			item.setDate(new Date());
		}

		itemService.editItem(item);
		System.out.println("edit:" + item);

		return "redirect:/list/" + listId;
	}

	@GetMapping("/copy/{id}")
	public String copyGet(
			@PathVariable int id,
			Model model) {
		Item item = itemService.getItemById(id);
		System.out.println("copy get:" + item);

		addAttributesForm(model, item, item.getListId(), "複製");
		return "itemForm";
	}

	@PostMapping("/copy/*")
	public String copyPost(
			@Valid Item item,
			Errors errors,
			Model model) {
		System.out.println("copy post:" + item);

		// 追加バリデーション
		item.additionalValidation(errors);

		Integer listId = item.getListId();
		if (errors.hasErrors()) {
			addAttributesForm(model, item, listId, "複製");
			return "itemForm";
		}

		// チェックリストの複製時は登録日時を更新
		if (listId == ItemList.ID_CHECK) {
			item.setDate(new Date());
		}

		itemService.addItem(item); // 新規追加
		System.out.println("copy:" + item);

		return "redirect:/list/" + listId;
	}

	@GetMapping("/move/{id}")
	public String moveGet(
			@PathVariable int id,
			Model model) {
		Item item = itemService.getItemById(id);
		System.out.println("move get:" + item);

		// 表示用に日時情報を更新
		item.setDate(new Date());

		addAttributesForm(model, item, ItemList.ID_HISTORY, "移動");
		return "itemForm";
	}

	@PostMapping("/move/*")
	public String movePost(
			@Valid Item item,
			Errors errors,
			Model model) {
		System.out.println("move post:" + item);

		// 追加バリデーション
		item.additionalValidation(errors);

		if (errors.hasErrors()) {
			addAttributesForm(model, item, ItemList.ID_HISTORY, "移動");
			return "itemForm";
		}

		item.setListId(ItemList.ID_HISTORY);

		itemService.editItem(item);
		System.out.println("edit:" + item);

		return "redirect:/list/" + ItemList.ID_HISTORY;
	}

	// itemFormを表示する際に必要な処理
	private void addAttributesForm(Model model, Item item, Integer listIdToSave, String formName) {
		// チェック中リストに対する編集時は表示用に現在日時をセット
		if (listIdToSave == ItemList.ID_CHECK) {
			item.setDate(new Date());
		}

		// 表示用のデータをModelに渡す
		model.addAttribute("item", item);
		model.addAttribute("maxKeywords", Item.MAX_KEYWORDS);
		model.addAttribute("listNames", ItemList.NAMES);
		model.addAttribute("listIdToSave", listIdToSave);
		model.addAttribute("formName", formName);
	}

}
