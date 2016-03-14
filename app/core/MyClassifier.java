package core;

import play.Logger;
import play.Logger.ALogger;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

/**
 * An abstract classifier that can be trained, evaluated and used to classify a {@link Instance}
 * @author René
 *
 */
public abstract class MyClassifier {
	
	private static final ALogger LOG = Logger.of(MyClassifier.class);
	
	private Classifier classifier;
	private WekaModel model;

	MyClassifier(Classifier classifier, WekaModel model) {
		this.classifier = classifier;
		this.model = model;
	}
	
	/**
	 * Abstract method
	 * @return the name of the classifier
	 */
	public abstract String getName();
	
	/**
	 * Method to train the classifier on the training set of the {@link WekaModel}
	 * @return true if training has succeeded, false if not
	 */
	public boolean train() {
		LOG.info("Building " + getName() + " classifier...");
		boolean success = false;
		
		try {
			// training classifier on training set
			classifier.buildClassifier(model.getTrainingSet());
			LOG.info("Successfully built " + getName() + " classifier!");
			success = true;

		} catch (Exception e) {
			LOG.error("Error while building classifier " + getName() + " - " + e.getMessage());
		}
		
		return success;
	}
	
	/**
	 * Method to evaluate the classifier on the test set of the {@link WekaModel}
	 * @param replaceMissing boolean to indicate whether to replace missing values on data sets before evaluating
	 * @return a string summary of the evaluation
	 */
	public String evaluate(boolean replaceMissing) {
			LOG.info("Running evaluation on " + getName() + " classifier...");
			// replace missing values if requested
			Instances trainingSet = replaceMissing ? replaceMissingValues(model.getTrainingSet()) : model.getTrainingSet();
			Instances testSet = replaceMissing? replaceMissingValues(model.getTestSet()) : model.getTestSet();
			
			Evaluation evaluation;
			try {
				// building evaluation
				evaluation = new Evaluation(trainingSet);
				// evaluating classifier on test set
				evaluation.evaluateModel(classifier, testSet);
				LOG.info("Successfully ran evaluation on test set!");
				
				String summaryString = evaluation.toSummaryString(
						"============ RESULTS : " + getName() + " ============", true);
				LOG.info(summaryString);
				return summaryString;
	
			} catch (Exception e) {
				String errorMsg = "Could not proceed with evaluation of classifier : " + getName() + " - " + e.getMessage();
				LOG.error(errorMsg);
				return errorMsg;
			}
	}
	
	/**
	 * A method that "cleans" a dataset by replacing missing values with computed values
	 * @param dirty a set of {@link Instance}
	 * @return a set of clean {@link Instance}
	 */
	private Instances replaceMissingValues(Instances dirty) {
		LOG.info("Replacing missing values...");
		ReplaceMissingValues replace = new ReplaceMissingValues();
	    try {
			replace.setInputFormat(dirty);
			return Filter.useFilter(dirty, replace); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return dirty;
	}
	
	/**
	 * A method to use the classifier to classify an {@link Instance} object 
	 * @param instance the instance to classify
	 * @return the String value of the result class
	 */
	public String classify(Instance instance) {
		
		instance.setDataset(model.getTrainingSet());
		try {
			instance.setClassValue(classifier.classifyInstance(instance));
			return instance.toString(instance.classAttribute());
		} catch (Exception e) {
			return "Unable to proceed with classification - " + e.getMessage();
		}
	}
	
}
