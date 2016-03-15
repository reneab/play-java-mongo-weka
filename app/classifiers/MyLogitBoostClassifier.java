package classifiers;

import model.WekaModel;
import weka.classifiers.meta.LogitBoost;

public class MyLogitBoostClassifier extends AbstractClassifier {

	/**
	 * A {@link LogitBoost} algorithm by Weka
	 * Class for performing additive logistic regression. This class performs classification 
	 * using a regression scheme as the base learner
	 * 
	 * @param model a {@link WekaModel} instance
	 * @param numIterations the number of iterations to run internal cross validation
	 */
	public MyLogitBoostClassifier(WekaModel model, int numIterations) {
		super(new LogitBoost(), model);
		
		LogitBoost logitBoost = (LogitBoost) this.classifier;
		logitBoost.setNumIterations(numIterations);
	}

	@Override
	public String getName() {
		return "LogitBoost";
	}

}
