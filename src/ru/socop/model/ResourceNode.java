/**
 * Пакет модельных классов
 */
package ru.socop.model;

import java.util.List;

/**
 * Модельный класс, описывающий отзывы в рамках конкретного ресурса
 * @author Andrew Mironov
 */
public class ResourceNode {
	// количество положительных отзывов
	private int pos;
	// количество отрицательных
	private int neg;
	// ссылка на ресурс
	private String url;
	// список нодов
	private List<OpNode> opNodes;
	/**
	 * метод выдаёт количество положительных отзывов 
	 * @return int - число положительных отзывов
	 */
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	/**
	 * метод выдаёт количество отрицательных отзывов 
	 * @return int - число отрицательных отзывов
	 */
	public int getNeg() {
		return neg;
	}
	public void setNeg(int neg) {
		this.neg = neg;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<OpNode> getOpNodes() {
		return opNodes;
	}
	public void setOpNodes(List<OpNode> opNodes) {
		this.opNodes = opNodes;
	}
	
}
