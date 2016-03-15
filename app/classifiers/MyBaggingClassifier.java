package classifiers;

import model.WekaModel;
import weka.classifiers.meta.Bagging;

public class MyBaggingClassifier extends AbstractClassifier {

	/**
	 * A classifier that uses the {@link Bagging} algorithm :
	 * Bagging (Bootstrap Aggregating) is an ensemble method that creates separate samples of the training 
	 * dataset and creates a classifier for each sample. The results of these multiple classifiers are 
	 * then combined (such as averaged or majority voting). The trick is that each sample of the training dataset 
	 * is different, giving each classifier that is trained, a subtly different focus and perspective on the problem.
	 * 
	 * The more iterations there are, the more accurate it will be, but will get slower
	 * 
	 * @param model a {@link WekaModel} instance
	 * @param numIterations the number of iterations to run the algorithm
	 * @param bagSizePercent the size of the bage as a percentage of the total dataset
	 */
	public MyBaggingClassifier(WekaModel model, int numIterations, int bagSizePercent) {
		super(new Bagging(), model);
		
		// setting parameters
		Bagging bagging = (Bagging) this.classifier;
		bagging.setNumIterations(numIterations);
		bagging.setBagSizePercent(bagSizePercent);
	}

	@Override
	public String getName() {
		return "Bagging";
	}

}
