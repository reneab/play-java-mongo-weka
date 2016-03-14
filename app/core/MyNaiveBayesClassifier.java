package core;

import weka.classifiers.bayes.NaiveBayes;

public class MyNaiveBayesClassifier extends MyClassifier {

	/**
	 * Initializes a {@link MyClassifier} object using a {@link NaiveBayes} classifier
	 * @param model a {link WekaModel}
	 */
	public MyNaiveBayesClassifier(WekaModel model) {
		super(new NaiveBayes(), model);
	}

	@Override
	public String getName() {
		return "Naive Bayes";
	}
	
}
