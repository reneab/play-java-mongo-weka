package classifiers;

import model.WekaModel;
import weka.classifiers.bayes.NaiveBayes;

public class MyNaiveBayesClassifier extends AbstractClassifier {

	/**
	 * Initializes a {@link AbstractClassifier} object using a {@link NaiveBayes} classifier
	 * Fast but not very accurate on this dataset, as it requires more uniform distributions of the data
	 * 
	 * @param model a {link WekaModel} instance
	 */
	public MyNaiveBayesClassifier(WekaModel model) {
		super(new NaiveBayes(), model);
	}

	@Override
	public String getName() {
		return "Naive Bayes";
	}
	
}
