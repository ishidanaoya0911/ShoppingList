package com.zdrv.app.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class User {
	private Integer id;

	@NotBlank
	@Size(max = 20)
	private String loginId;

	@NotBlank
	private String loginPass;

}
