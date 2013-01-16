/**
 * Пакет для вспомогательных средств
 */
package ru.socop.utils;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



import ru.socop.model.QuadNode;

/**
 * Класс, реализующий работу с генерацией клиентской XML по DOM модели
 * @author Andrew Mironov
 */
public class DomClientXmlFormer {
	
	protected List<QuadNode> _quadNodes;
	
	public DomClientXmlFormer(List<QuadNode> quadNodes) {
		_quadNodes = quadNodes;
	}
	
	/*
	 * Метод, выдающий клиентское XML в виде строки
	 * page - начиная с какого номера берём квадра-ноды
	 * legth - сколько квадра-нодов берём 
	 * goodpc - процент положительных отзывов
	 * badpc - процент отрицательных отзывов
	 */
	public String getXmlString(int page, int length, double goodpc, double badpc) {
			if ( _quadNodes == null)
				return null;
			if (_quadNodes.size() < 1)
				return null;
			if ((page + length) > _quadNodes.size())
				page = _quadNodes.size() - length;
			
			// output xml строка
			String xmlString = "";
			
			try {
				
				// создаём документ
				DocumentBuilderFactory docbfac = DocumentBuilderFactory.newInstance();
				DocumentBuilder docb = docbfac.newDocumentBuilder();
				Document doc = docb.newDocument();
				
				// создаём xml дерево
				
				// рутовый элемент
				Element op = doc.createElement("op");
				op.setAttribute("amount", String.valueOf(length));
				op.setAttribute("maxPages", String.valueOf(_quadNodes.size()));
				op.setAttribute("goodpc", String.valueOf(goodpc));
				op.setAttribute("badpc", String.valueOf(badpc));
				op.setAttribute("page", String.valueOf(page));
				op.setAttribute("length", String.valueOf(length));
				doc.appendChild(op);
				
				// внедряем cell-элементы (по числу length)
				for (int i = 0; i < length; i++) {
					Element cell = doc.createElement("cell");
					
					// соответствующий по номеру квадра-нод. 
					QuadNode quadNode = _quadNodes.get(page + i);
					// заполняем cell и добавляем к руту
					fillCellElement(cell, doc, quadNode);
					op.appendChild(cell);			
				}
				
	            //Переводим XML в String

	            //настраиваем transformer
	            TransformerFactory transfac = TransformerFactory.newInstance();
	            Transformer trans = transfac.newTransformer();
	            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	            trans.setOutputProperty(OutputKeys.INDENT, "yes");

	            //create string from xml tree
	            StringWriter sw = new StringWriter();
	            StreamResult result = new StreamResult(sw);
	            DOMSource source = new DOMSource(doc);
	            trans.transform(source, result);
	            xmlString = sw.toString();
			}
			catch (Exception e) {}			

		return xmlString;
	}
	
	protected void fillCellElement(Element cell, Document doc, QuadNode quadNode) {
		// внедряем в cell ровно 4 отзыва				

		// 1-ый плохой отзыв
		Element com = doc.createElement("com");
		if (quadNode.bad1 != null) {
			com.setAttribute("empty", "false");
			com.setAttribute("type", "bad");
			com.setAttribute("short", quadNode.bad1.shortText);
			com.setAttribute("long", quadNode.bad1.longText);
			com.setAttribute("link", quadNode.bad1.uri);
			com.setAttribute("opID", quadNode.bad1.id);
			cell.appendChild(com);
		}
		else {
			com.setAttribute("empty", "true");
			com.setAttribute("type", "");
			com.setAttribute("short", "");
			com.setAttribute("long", "");
			com.setAttribute("link", "");
			com.setAttribute("opID", "");
			cell.appendChild(com);
		}
		
		// 2-ой плохой отзыв
		com = doc.createElement("com");
		if (quadNode.bad2 != null) {
			com.setAttribute("empty", "false");
			com.setAttribute("type", "bad");
			com.setAttribute("short", quadNode.bad2.shortText);
			com.setAttribute("long", quadNode.bad2.longText);
			com.setAttribute("link", quadNode.bad2.uri);
			com.setAttribute("opID", quadNode.bad2.id);
			cell.appendChild(com);
		}
		else {
			com.setAttribute("empty", "true");
			com.setAttribute("type", "");
			com.setAttribute("short", "");
			com.setAttribute("long", "");
			com.setAttribute("link", "");
			com.setAttribute("opID", "");
			cell.appendChild(com);
		}
		
		// 1-ой хороший отзыв
		com = doc.createElement("com");
		if (quadNode.good1 != null) {
			com.setAttribute("empty", "false");
			com.setAttribute("type", "good");
			com.setAttribute("short", quadNode.good1.shortText);
			com.setAttribute("long", quadNode.good1.longText);
			com.setAttribute("link", quadNode.good1.uri);
			com.setAttribute("opID", quadNode.good1.id);
			cell.appendChild(com);
		}
		else {
			com.setAttribute("empty", "true");
			com.setAttribute("type", "");
			com.setAttribute("short", "");
			com.setAttribute("long", "");
			com.setAttribute("link", "");
			com.setAttribute("opID", "");
			cell.appendChild(com);
		}
		
		// 2-ой хороший отзыв
		com = doc.createElement("com");
		if (quadNode.good2 != null) {
			com.setAttribute("empty", "false");
			com.setAttribute("type", "good");
			com.setAttribute("short", quadNode.good2.shortText);
			com.setAttribute("long", quadNode.good2.longText);
			com.setAttribute("link", quadNode.good2.uri);
			com.setAttribute("opID", quadNode.good2.id);
			cell.appendChild(com);
		}
		else {
			com.setAttribute("empty", "true");
			com.setAttribute("type", "");
			com.setAttribute("short", "");
			com.setAttribute("long", "");
			com.setAttribute("link", "");
			com.setAttribute("opID", "");
			cell.appendChild(com);
		}
	}
}
