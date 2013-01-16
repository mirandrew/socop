package ru.socop.listeners;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ru.socop.opineval.BayesClassifier;
import ru.socop.opineval.Classifier;
import ru.socop.opineval.SVMClassifier2Steps3Classes;

public class GeneralListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
//		String path = contextEvent.getServletContext().getRealPath("WEB-INF/assets/2.classifier");
//		SVMClassifier2Steps3Classes svmClassifier = new SVMClassifier2Steps3Classes();
//		Classifier classifier = null;
//		try {
//			classifier = svmClassifier.load(path);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		contextEvent.getServletContext().setAttribute("svmClassifier", classifier);
		
		String path = contextEvent.getServletContext().getRealPath("WEB-INF/assets/4.classifier");
		BayesClassifier bayesClassifier = new BayesClassifier();
		Classifier classifier = null;
		try {
			classifier = bayesClassifier.load(path);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		contextEvent.getServletContext().setAttribute("bayesClassifier", classifier);
	}

}
