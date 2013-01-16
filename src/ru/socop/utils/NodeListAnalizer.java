package ru.socop.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import analizer.Evaluater;

import ru.socop.model.NodeTypes;
import ru.socop.model.OpNode;

/**
 * Класс для анализа списка нодов (доп проверок; вторичных алгоритмов типизации)
 * @author Andrew Mironov
 * 
 */
public class NodeListAnalizer {
	private static Logger logger = Logger.getLogger(Logger.class);
	
	protected List<OpNode> _nodeList;
	protected String _filePath;
	protected Evaluater _evaluator = new Evaluater();
	
	public NodeListAnalizer(List<OpNode> nodeList) {
		_nodeList = nodeList;
		
	}
	
	public NodeListAnalizer(List<OpNode> nodeList, String filePath) {
		_nodeList = nodeList;
		_filePath = filePath;
	}
	
	public void loadDict() throws FileNotFoundException {
		if (_filePath != null)
			_evaluator.load(_filePath);
	}
	
	public void loadDict(String filePath) throws FileNotFoundException {
		_filePath = filePath;
		_evaluator.load(_filePath);
	}
	
	/**
	 * Функция анализа списка нодов на основании предопределённого словаря 
	 * @return обработанный список нодов
	 */
	public List<OpNode> analiseByPreDict() throws IOException, NullPointerException, IndexOutOfBoundsException {
		List<OpNode> rewarkNodeList = new ArrayList<OpNode>();
		NodeTypes resType;
		if (_nodeList != null)
			for (OpNode opNode : _nodeList) {
				resType = evaluateTextByPreDict(opNode.fullText);
				OpNode resNode = new OpNode();
				String textType;
				if (resType != null)
					textType = resType.name().toLowerCase();
				else
					textType = NodeTypes.NONE.name().toLowerCase(); 
				resNode.fillOpNode(textType, opNode.uri, opNode.shortText, opNode.longText, opNode.fullText);
				rewarkNodeList.add(resNode);
			}
		else 
			throw new IOException("threre is no lists of OpNodes");
			
		return rewarkNodeList;
	}
	
	protected NodeTypes evaluateTextByPreDict(String text) throws NullPointerException, IndexOutOfBoundsException {
		Double value = 0.0;
		if (_evaluator != null) {
			value = _evaluator.evaluate(text);
			if (value == null)
				return null;
			if (value > 0)
				return NodeTypes.GOOD;
			else if (value < 0)
				return NodeTypes.BAD;
			else
				return NodeTypes.NEUTRAL;
		}
		return null;
	}
}
