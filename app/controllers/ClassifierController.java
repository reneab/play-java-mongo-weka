package controllers;

import javax.inject.Inject;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Arrays;

import views.html.classifierPage;
import beans.Adult;
import core.DataModelDetails;
import core.MyClassifier;
import core.MyNaiveBayesClassifier;
import core.WekaModel;
import dao.MongoDao;

public class ClassifierController extends Controller {

	@Inject private FormFactory formFactory;
	@Inject private WekaModel model;
	@Inject private MongoDao dao;

	private MyClassifier classifier;
	private boolean success;
	
	/**
	 * Initializes the {@link MyClassifier} and trains it on the training set
	 * @return
	 */
	public Result init() {
		model.fillTrainingSet(dao.getTrainingSet());
		
//		classifier = new MyRandomForestClassifier(model);
		classifier = new MyNaiveBayesClassifier(model);
		
		// train the classifier
		success = classifier.train();
		
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
	 * To evaluate the {@link MyClassifier} on the test set
	 * @return
	 */
	public Result evaluate() {
		// fill model with test set
		model.fillTestSet(dao.getTestSet());
		// evaluate the classifier
		return ok(classifier.evaluate(false));
	}
	
	/**
	 * To use the {@link MyClassifier} on the test set
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
