package ru.socop.web;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.xml.bind.Element;

import org.apache.log4j.Logger;
import org.eclipse.jdt.internal.compiler.ast.ContinueStatement;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import com.sun.jmx.trace.Trace;

import ru.socop.model.FakeData;
import ru.socop.model.OpNode;
import ru.socop.model.QuadNode;
import ru.socop.model.ResourceNode;
import ru.socop.opineval.BayesClassifier;
import ru.socop.opineval.Classifier;
import ru.socop.opineval.FileHelper;
import ru.socop.opineval.OpenLink;
import ru.socop.opineval.OpinionsFinder;
import ru.socop.opineval.ResourceNodeCreator;
import ru.socop.opineval.SVMClassifier2Steps3Classes;
import ru.socop.utils.DomClientXmlFormer;
//import ru.socop.utils.NodeListAnalizer;
import ru.socop.utils.ResourceHelper;

/**
 * Servlet реагирующий на поисковый запрос пользователя
 */

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ru.socop.web.SearchServlet.class);
	private static final int numberOfThreads = 10;
	private Boolean isDumped = true;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// получаем параметры запроса
		String searchString = request.getParameter("text");
		String linksString = request.getParameter("l");
		//ResourceNodeCreator creator = new ResourceNodeCreator((SVMClassifier2Steps3Classes)getServletContext().getAttribute("svmClassifier"), numberOfThreads);
		ResourceNodeCreator creator = new ResourceNodeCreator((BayesClassifier)getServletContext().getAttribute("bayesClassifier"), numberOfThreads);
		int page = 0;
		int length = 0;
		if (request.getParameter("page") != null && request.getParameter("length") != null) {
			page = Integer.parseInt(request.getParameter("page"));
			length = Integer.parseInt(request.getParameter("length"));
		
		
		// OpinEval Bayes
//			logger.info("Start doing work");
//			Classifier c1 = new BayesClassifier();
//			String path = getServletContext().getRealPath("web-inf/classes/1.classifier");
//			logger.info("Classifier path is " + path);
//			
//			BayesClassifier bayes = null;
//			try {
//				c1 = c1.load(path);
//				bayes = (BayesClassifier)c1;
//				bayes.setClassificationThreashold(2);
//				bayes.setFeatureThreshhold(3);
//			}
//			catch (Exception e) {
//				logger.error("Can't load 1.classifier");
//			}
			
			
			
			logger.info("Creating dump of useres opinions");
			String dumpFilePath = getServletContext().getRealPath("WEB-INF/assets/dump.txt");
			logger.info("Reading an existing dump of opinions");
			List<String> dumpList = null;
			try {
				if (isDumped)
					dumpList = FileHelper.read(dumpFilePath);
			}
			catch (Exception e) {
				logger.error("Can't load dump file");
			}
			Set<String> dumpSet = new HashSet<String>();
			List<String> auxList = new ArrayList<String>();
			if (isDumped) {
				if (dumpList != null)
					dumpSet = new HashSet<String>(dumpList);
			}
					 
			 FileWriter fr = new FileWriter(dumpFilePath, true);
				
			// cycling on links and making resourceNodes
			// список ресурсов (будет заполняться ядром)
			List<ResourceNode> resoureNodes = new ArrayList<ResourceNode>();
			List<OpNode> allOpNodes = new ArrayList<OpNode>();
			String[] linksArray = linksString.split(";");
			// счётчики всех отзывов по  типам
			int negCounter = 0;
			int posConter = 0;
			Boolean debug = false;
			ResourceNode[] resNodes = null;
			if (!debug) {
//				OpenLink.setTimeout(3000);
//				OpinionsFinder.setMinimumWordsNumber(8);
//				resNodes = creator.createInSeveralThreads(linksArray, searchString, ResourceNodeCreator.ALL);
//				for (ResourceNode rn : resNodes) {
//					if (rn != null && (rn.getNeg() != 0 || rn.getPos() != 0)) {
//						allOpNodes.addAll(rn.getOpNodes());
//						posConter += rn.getPos();
//						negCounter += rn.getNeg();
//						if (isDumped) {
//							for (OpNode opNode : rn.getOpNodes())
//								if (!dumpSet.contains(opNode.fullText)) {
//									fr.append(opNode.fullText + "\n");
//									dumpSet.add(opNode.fullText);
//								}
//						}
//					}
//				}
				for (String link : linksArray) {
					//String linkPart = link.attr("abs:href").substring(0, 18);
					ResourceNode rn = null;
					//if (linkPart.compareTo("http://nigmaru.com") != 0 && linkPart.compareTo("http://direct.yand") != 0) {
					try {
						//OpenLink.setTimeout(3000);
						OpinionsFinder.setMinimumWordsNumber(6);
						rn = creator.create(link, searchString, ResourceNodeCreator.ALL);
					}
					catch (Exception e) {
						logger.error("Rnc error " + link);
						continue;
					}
					if (rn != null && (rn.getNeg() != 0 || rn.getPos() != 0)) {
						
						if (isDumped) {
							for (OpNode opNode : rn.getOpNodes())
								if (!dumpSet.contains(opNode.fullText)) {
									fr.append(opNode.fullText + "\n");
									dumpSet.add(opNode.fullText);
								}
						}
						
						allOpNodes.addAll(rn.getOpNodes());
						posConter += rn.getPos();
						negCounter += rn.getNeg();
					}
						
				}
			}
			
			fr.flush();
			fr.close();
			
			int allCounter = posConter + negCounter;
			double goodpc = 0; 
			double badpc =  0;
			if (allCounter > 0) {
				goodpc = Math.round(posConter * 100 / allCounter);
				badpc = 100 - goodpc;
			}
			logger.info("Goodpc " + goodpc);
			List<QuadNode> quadNodesList;
			// Теперь мы можем вытянуть долгожданный список квадра-нодов
			if (!debug)
				quadNodesList = ResourceHelper.getQuadNodes(allOpNodes);
			else {
				// мы в дебаг-режиме
				quadNodesList = FakeData.getFakeQuadNodes();
				goodpc = 54;
				badpc = 100 - 54;
			}
			
			 // формирования ответа
			response.setContentType("text/xml; charset=UTF-8");
			 response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			// формируем xml
			DomClientXmlFormer xmlFormer = new DomClientXmlFormer(quadNodesList);
			String xmlString = xmlFormer.getXmlString(page, length, goodpc, badpc);
			logger.info("Work's been done  ");
			out.println(xmlString);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		//String searchString = request.getParameter("text");
	}

}
