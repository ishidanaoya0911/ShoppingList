package com.zdrv.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zdrv.app.domain.Keyword;

@Mapper
public interface KeywordDao {

	List<Keyword> selectAll();

	List<Keyword> selectAllByItemId(Integer itemId);

	Keyword selectByWord(String word);

	void insert(Keyword keyword);

	void delete(Integer id);

	void insertRelation(
			@Param("itemId") Integer itemId,
			@Param("keywordId") Integer keywordId);

	void deleteRelation(Integer itemId);

}
