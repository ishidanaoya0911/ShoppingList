package com.zdrv.app.domain;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.validation.Errors;

import lombok.Data;

@Data
public class Item {
	// キーワードの最大数
	public static final int MAX_KEYWORDS = 5;

	private Integer id;

	// リストID
	private Integer listId;

	@NotNull
	private Date date;

	@NotBlank
	@Size(max = 255)
	private String name;

	@Min(0)
	private Integer price;

	@Size(max = 1000)
	private String url;

	@Size(max = 1000)
	private String memo;

	// 検索キーワードのリスト
	@Valid
	@Size(max = MAX_KEYWORDS)
	private List<Keyword> keywordList;

	// キーワードを検索用文字列に変換して格納
	private String keywords;

	// データ削除用チェックボックス
	private Boolean isDelete;

	// キーワードをを元に検索用文字列の設定
	public void setKeywordsFromList() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < keywordList.size(); i++) {
			if (i > 0) {
				sb.append(" ");
			}
			sb.append(keywordList.get(i).getWord());
		}

		keywords = sb.toString();
	}

	// フィールドに対する追加バリデーション
	public void additionalValidation(Errors errors) {
		// 商品名が全角含む空白文字のみで入力されていないか
		if (name.matches("[\\s|　]+")) {
			errors.rejectValue("name", "error.name.whitespace_only");
		}

		// URLが正しい形式かどうか
		if (!url.matches("(https?://[\\w!\\?/\\+\\-_~=;\\.,\\*&@#\\$%\\(\\)'\\[\\]]+)?")) {
			errors.rejectValue("url", "error.url.incorrect_format");
		}

		// キーワード
		for (int i = 0; i < keywordList.size(); i++) {
			String field = "keywordList[" + i + "].word";
			String word = keywordList.get(i).getWord();

			// 入力がなければ処理をスキップ
			if (word.equals("")) {
				continue;
			}

			// 全角含む空白文字が含まれていないか
			if (word.matches(".*[\\s|　]+.*")) {
				errors.rejectValue(field, "error.keyword.contain_whitespace");
			}

			// 今までの入力に同じキーワードがないか
			for (int j = 0; j < i; j++) {
				if (word.equals(keywordList.get(j).getWord())) {
					errors.rejectValue(field, "error.keyword.same_word");
					break;
				}
			}
		}
	}

}
