package ru.socop.model;

import java.util.ArrayList;
import java.util.List;

public class FakeData {
	/*
	 * Функция для заполнения  фейкового спсика квадра-нодов
	 */
	public static List<QuadNode> getFakeQuadNodes() {
		List<QuadNode> quadNodes = new ArrayList<QuadNode>();
		
		
		for (int i = 0; i < 11; i++) {
			QuadNode quadNode = new QuadNode();
			
			OpNode opNode = new OpNode();
			opNode.fillOpNode("bad", "http://ya.ru", ("Полный ужас! " + String.valueOf(i + 1)), "Ipsum dol consectetuer adipiscing elit.", "Ipsum dol consectetuer adipiscing elit.");
			quadNode.bad1 = opNode;
			
			opNode = new OpNode();
			opNode.fillOpNode("bad", "http://google.ru", "Ужасное обслуживание!", "Suspendisse quam sem, consequat at, commodo vitae,", "Suspendisse quam sem, consequat at, commodo vitae,");
			quadNode.bad2 = opNode;
			
			opNode = new OpNode();
			opNode.fillOpNode("good", "http://bing.ru", "Великолепно! Нет слов!", "Ipsum dol consectetuer adipiscing elit.", "Ipsum dol consectetuer adipiscing elit.");
			quadNode.good1 = opNode;
			
			opNode = new OpNode();
			opNode.fillOpNode("good", "http://www.cdezign.ru/", "Хочу ходить сюда снова и снова!", "Suspendisse quam sem, consequat at, commodo vitae,", "Suspendisse quam sem, consequat at, commodo vitae,");
			quadNode.good2 = opNode;
			
			quadNodes.add(quadNode);
		}
		
		return quadNodes;
	}
}
