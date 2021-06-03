package com.zdrv.app.service;

import java.util.List;

import com.zdrv.app.domain.Keyword;

public interface KeywordService {

	List<Keyword> getKeywordList();

	List<Keyword> getKeywordListByItemId(Integer itemId);

}
