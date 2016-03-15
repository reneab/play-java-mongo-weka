package controllers;

import java.util.Arrays;

import javax.inject.Inject;

import model.Adult;
import model.DataModelDetails;
import model.WekaModel;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.classifierPage;
import classifiers.AbstractClassifier;
import classifiers.MyBaggingClassifier;
import classifiers.MyJ48Classifier;
import classifiers.MyLogitBoostClassifier;
import classifiers.MyRandomForestClassifier;
import dao.MongoDao;

public class ClassifierController extends Controller {

	@Inject private FormFactory formFactory;
	@Inject private WekaModel model;
	@Inject private MongoDao dao;

	private AbstractClassifier classifier;
	private boolean success;
	
	/**
	 * Initializes the {@link AbstractClassifier} and trains it on the training set
	 * @return
	 */
	public Result init() {
		model.fillTrainingSet(dao.getTrainingSet());
		
		// a set of Classifiers with the parameters I found give good accuracy results
		
//		classifier = new MyRandomForestClassifier(model, 150);
//		classifier = new MyNaiveBayesClassifier(model);
//		classifier = new MyDecisionTabeClassifier(model);
//		classifier = new MyBaggingClassifier(model, 70, 100);
//		classifier = new MyLogitBoostClassifier(model, 20);
		classifier = new MyJ48Classifier(model, 0.1f, 3);
		
		// train the classifier
		success = classifier.train(true);
		
		Form<Adult> adultForm = formFactory.form(Adult.class);
		// We pass the form and model details in order to build the select boxes

		return ok(classifierPage.render(success, classifier.getName(), adultForm, 
				Arrays.asList(DataModelDetails.WORKCLASS_ATTR),
				Arrays.asList(DataModelDetails.EDUCATION_ATTR),
				Arrays.asList(DataModelDetails.MARITAL_STATUS_ATTR),
				Arrays.asList(DataModelDetails.OCCUPATION_ATTR),
				Arrays.asList(DataModelDetails.RELATIONSHIP_ATTR),
				Arrays.asList(DataModelDetails.RACE_ATTR),
				Arrays.asList(DataModelDetails.SEX_ATTR),
				Arrays.asList(DataModelDetails.NATIVE_COUNTRY_ATTR)
				));
    }
	
	/**
	 * To evaluate the {@link AbstractClassifier} on the test set
	 * @return a new page with the String summary of the evaluation
	 */
	public Result evaluate() {
		// fill model with test set
		model.fillTestSet(dao.getTestSet());
		// evaluate the classifier
		return ok(classifier.evaluate());
	}
	
	/**
	 * To cross-validate the {@link AbstractClassifier}
	 * @return a new page with the String summary of the evaluation
	 */
	public Result crossValidate() {
		// cross-validate the classifier 10 folds
		return ok(classifier.crossValidate(10));
	}
	
	/**
	 * To use the {@link AbstractClassifier} on the test set
	 * @return the same page, but with an error message in case of failure, 
	 * and the result as String in case of success
	 */
	public Result classify() {
		// getting the request form
		Form<Adult> adultForm = formFactory.form(Adult.class).bindFromRequest();
		try {
			// generating an Adult object from the form
			Adult adult = adultForm.get();
			
			// building an Instance and classifying it using our classifier
			String classify = classifier.classify(model.getInstanceFromAdult(adult));
			flash("success", classify);
		} catch (IllegalStateException e) {
			flash("error", e.getMessage());
		}
		
		// renders the same page, with the form still filled
		return ok(classifierPage.render(success, classifier.getName(), adultForm, 
				Arrays.asList(DataModelDetails.WORKCLASS_ATTR),
				Arrays.asList(DataModelDetails.EDUCATION_ATTR),
				Arrays.asList(DataModelDetails.MARITAL_STATUS_ATTR),
				Arrays.asList(DataModelDetails.OCCUPATION_ATTR),
				Arrays.asList(DataModelDetails.RELATIONSHIP_ATTR),
				Arrays.asList(DataModelDetails.RACE_ATTR),
				Arrays.asList(DataModelDetails.SEX_ATTR),
				Arrays.asList(DataModelDetails.NATIVE_COUNTRY_ATTR)
				));
	}
	
}
