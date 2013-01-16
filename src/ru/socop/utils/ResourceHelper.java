/**
 * 
 */
package ru.socop.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.socop.model.OpNode;
import ru.socop.model.QuadNode;
import ru.socop.model.ResourceNode;

/**
 * Класс для вспомогательной работы с ресурсами
 * @author Andrew Mironov
 * 
 */
public class ResourceHelper {
	/*
	 * Метод, вытягивающий список комментариев(нодов) из списка ресурсов
	 */
	public static List<OpNode> resToNodTransform(List<ResourceNode> resources) {
		if (resources == null)
			return null;
		
		List<OpNode> nodes = new ArrayList<OpNode>();
		for (ResourceNode resource : resources)
			nodes.addAll(resource.getOpNodes());
		
		return nodes;
	}
	
	/*
	 * Метод, вытягивающий список нодов нужного типа из общего списка
	 */
	public static List<OpNode> getTypeNodes(List<OpNode> allNodes, String type) {
		if (allNodes == null)
			return null;
		
		List<OpNode> typeNodes = new ArrayList<OpNode>();
		for (OpNode node : allNodes)
			if (node.type == type)
				typeNodes.add(node);
			
		return typeNodes;
	}
	
	/*
	 * Метод, вытягивающий список квадра-нодов из общего списка
	 */
	public static List<QuadNode> getQuadNodes(List<OpNode> allNodes) {
		if (allNodes == null)
			return null;
		
		List<OpNode> badNodes = getTypeNodes(allNodes, "bad");
		List<OpNode> goodNodes = getTypeNodes(allNodes, "good");
		// список результатов
		List<QuadNode> quadNodes = new ArrayList<QuadNode>();
		
		Iterator<OpNode> badIterator = badNodes.iterator();
		Iterator<OpNode> goodIterator = goodNodes.iterator();

		while (badIterator.hasNext() || goodIterator.hasNext()) {
			QuadNode quadNode = new QuadNode();
			if (badIterator.hasNext())
				quadNode.bad1 = badIterator.next();
			if (badIterator.hasNext())
				quadNode.bad2 = badIterator.next();
			if (goodIterator.hasNext())
				quadNode.good1 = goodIterator.next();
			if (goodIterator.hasNext())
				quadNode.good2 = goodIterator.next();
			quadNodes.add(quadNode);
		}
		
		return quadNodes;
	}
}
