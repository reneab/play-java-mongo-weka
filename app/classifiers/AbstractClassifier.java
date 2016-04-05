package classifiers;

import java.util.Random;

import model.WekaModel;
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
public abstract class AbstractClassifier {
	
	private static final ALogger LOG = Logger.of(AbstractClassifier.class);
	
	protected Classifier classifier;
	private WekaModel model;

	AbstractClassifier(Classifier classifier, WekaModel model) {
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
	public boolean train(boolean replaceMissing) {
		LOG.info("Building " + getName() + " classifier...");

		Instances trainingSet = replaceMissing ? replaceMissingValues(model.getTrainingSet()) : model.getTrainingSet();
		boolean success = false;
		
		try {
			// training classifier on training set
			classifier.buildClassifier(trainingSet);
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
	 * @return a {@code String} summary of the evaluation
	 */
	public String evaluate() {
		LOG.info("Running evaluation on " + getName() + " classifier...");
		
		try {
			// building evaluation
			Evaluation evaluation = new Evaluation(model.getTrainingSet());
			// evaluating classifier on test set
			evaluation.evaluateModel(classifier, model.getTestSet());

			String summary = getFullSummaryString(evaluation);
			
			LOG.info(summary);
			return summary;

		} catch (Exception e) {
			String errorMsg = "Could not proceed with evaluation of classifier : " + getName() + " - " + e.getMessage();
			LOG.error(errorMsg);
			return errorMsg;
		}
	}

	
	/**
	 * A method that "cleans" a dataset by replacing missing values using weka {@link ReplaceMissingValues}
	 * @param dirty a set of {@link Instance}
	 * @return a set of clean {@link Instance}
	 */
	private Instances replaceMissingValues(Instances dirty) {
		ReplaceMissingValues replacer = new ReplaceMissingValues();
		LOG.info(replacer.globalInfo());
	    try {
			replacer.setInputFormat(dirty);
			Instances instancesFiltered = Filter.useFilter(dirty, replacer);
			return instancesFiltered; 
		} catch (Exception e) {
			LOG.error("Error while replacing missing values: " + e.getMessage());
			return dirty;
		}
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
	
	/**
	 * A method to cross-validate the classifier using Weka {@link Evaluation} object
	 * @param folds the number of folds to perform the cross-validation
	 * @return a {@code String} summary of the validation
	 */
	public String crossValidate(int folds) {
		LOG.info("Running cross-validation " + folds + " folds on " + getName()
				+ " classifier...");
		try {
			Evaluation eval = new Evaluation(model.getTrainingSet());
			eval.crossValidateModel(classifier, model.getTrainingSet(), 10,
					new Random(1));

			String summaryString = getFullSummaryString(eval);

			LOG.info(summaryString);
			return summaryString;

		} catch (Exception e) {
			String errorMsg = "Could not proceed with evaluation of classifier : " + getName() + " - " + e.getMessage();
			LOG.error(errorMsg);
			return errorMsg;
		}
	}
	
	private String getFullSummaryString(Evaluation evaluation) throws Exception {

		StringBuilder summaryBuilder = new StringBuilder();
		summaryBuilder.append("================ EVALUATION RESULTS : " + getName() + " ====================\n\n");
		summaryBuilder.append(evaluation.toMatrixString());
		summaryBuilder.append("\n");
		summaryBuilder.append(evaluation.toSummaryString(true));
		summaryBuilder.append("\n");
		summaryBuilder.append(evaluation.toClassDetailsString());
		return summaryBuilder.toString();
	}
	
}
