package com.zdrv.app.domain;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Keyword {
	private Integer id;

	@Size(max = 20)
	private String word;

}
