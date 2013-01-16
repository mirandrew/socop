/**
 * Пакет модельных классов
 */
package ru.socop.model;

/**
 * Модельный класс отражающий базовые свойства отзыва
 * @author Andrew Mironov
 */
public class OpNode {
	// тип отзыва
	public String type;
	// ссылка на ресурс
	public String uri;
	// короткий текст
	public String shortText;
	// длинный текст
	public String longText;
	// полный текст
	public String fullText;
	// значение из html
	public String id;
	
	public void fillOpNode(String type, String uri, String shortText, String longText, String fullText) {
		this.type = type;
		this.uri = uri;
		this.shortText = shortText;
		this.longText = longText;
		this.fullText = fullText;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getShortText() {
		return shortText;
	}

	public void setShortText(String shortText) {
		this.shortText = shortText;
	}

	public String getLongText() {
		return longText;
	}

	public void setLongText(String longText) {
		this.longText = longText;
	}

	public String getFullText() {
		return fullText;
	}

	public void setFullText(String fullText) {
		this.fullText = fullText;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
