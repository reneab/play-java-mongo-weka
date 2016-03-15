package classifiers;

import model.WekaModel;
import weka.classifiers.trees.RandomForest;

public class MyRandomForestClassifier extends AbstractClassifier {
	
	/**
	 * Initializes a {@link AbstractClassifier} object using a {@link RandomForest} classifier
	 * 
	 * Accurate but slow (and more trees you set, the more accurate and the slower it gets) 
	 * 
	 * @param model a {link WekaModel} instance
	 */
	public MyRandomForestClassifier(WekaModel model, int numTrees) {
		super(new RandomForest(), model);
		
		// sets Number of Trees parameter
		RandomForest randomForest = (RandomForest) this.classifier;
		randomForest.setNumTrees(numTrees);
	}

	@Override
	public String getName() {
		return "Random Forest";
	}

}
