package classifiers;

import model.WekaModel;
import weka.classifiers.trees.J48;

public class MyJ48Classifier extends AbstractClassifier {

	/**
	 * A {@link J48} classifier based on the C4.5 decision tree
	 * 
	 * @param model @param model a {@link WekaModel} instance
	 * @param confidenceFactor the confidence factor
	 * @param minNumObject the minimum number of objects for which to create a leaf
	 */
	public MyJ48Classifier(WekaModel model, float confidenceFactor, int minNumObject) {
		super(new J48(), model);
		
		// set parameters of the model
		J48 j48 = (J48) this.classifier;
		j48.setConfidenceFactor(confidenceFactor);
		j48.setMinNumObj(minNumObject);
	}

	@Override
	public String getName() {
		return "J48 Decision Tree";
	}
	
}
