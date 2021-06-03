package com.zdrv.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zdrv.app.dao.KeywordDao;
import com.zdrv.app.domain.Keyword;

@Service
@Transactional
public class KeywordServiceImpl implements KeywordService {
	@Autowired
	private KeywordDao keywordDao;

	@Override
	public List<Keyword> getKeywordList() {
		return keywordDao.selectAll();
	}

	@Override
	public List<Keyword> getKeywordListByItemId(Integer itemId) {
		return keywordDao.selectAllByItemId(itemId);
	}

}
