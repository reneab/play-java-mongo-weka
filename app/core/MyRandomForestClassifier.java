package core;

import weka.classifiers.trees.RandomForest;

public class MyRandomForestClassifier extends MyClassifier {
	
	/**
	 * Initializes a {@link MyClassifier} object using a {@link RandomForest} classifier
	 * @param model a {link WekaModel}
	 */
	public MyRandomForestClassifier(WekaModel model) {
		super(new RandomForest(), model);
	}

	@Override
	public String getName() {
		return "Random Forest";
	}

}
