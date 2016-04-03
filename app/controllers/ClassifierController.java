package controllers;

import java.util.Arrays;
import java.util.HashMap;

import javax.inject.Inject;

import model.Adult;
import model.DataModelDetails;
import model.WekaModel;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.classifierPage;
import views.html.home;
import classifiers.AbstractClassifier;
import classifiers.ClassifiersEnum;
import classifiers.MyBaggingClassifier;
import classifiers.MyDecisionTableClassifier;
import classifiers.MyJ48Classifier;
import classifiers.MyLogitBoostClassifier;
import classifiers.MyNaiveBayesClassifier;
import classifiers.MyRandomForestClassifier;
import dao.MongoDao;

public class ClassifierController extends Controller {

	@Inject private FormFactory formFactory;
	@Inject private WekaModel model;
	@Inject private MongoDao dao;

	private AbstractClassifier classifier;
	private boolean success;
	
	/**
	 * Fills the {@link WekaModel} with training data and renders the initial form
	 * @return
	 */
	public Result init() {
		// fill model with training data from the DAO
		model.fillTrainingSet(dao.getTrainingSet());
		
		// pass initial form parameters
		HashMap<String, Object> initialData = new HashMap<>();
		initialData.put("replace", true);
		DynamicForm classifierForm = formFactory.form().fill(initialData);
		
		// send the names of classifier and the classifiers list
		return ok(home.render(classifierForm, ClassifiersEnum.names()));
	}
	
	/**
	 * Initializes the {@link AbstractClassifier} and trains it on the training set
	 * @return
	 */
	public Result train() {
		DynamicForm classifierForm = formFactory.form().bindFromRequest();
		Boolean replace = Boolean.valueOf(classifierForm.get("replace"));

		ClassifiersEnum classifierEnum = ClassifiersEnum.valueOf(classifierForm.get("classifier"));
		// Create the classifier from an Enum, with the parameters I found give good accuracy results
		switch (classifierEnum) {
			case J48:
				classifier = new MyJ48Classifier(model, 0.1f, 3);
				break;
			case Bagging:
				classifier = new MyBaggingClassifier(model, 70, 100);
				break;
			case LogitBoost:
				classifier = new MyLogitBoostClassifier(model, 20);
				break;
			case RandomForest:
				classifier = new MyRandomForestClassifier(model, 150);
				break;
			case NaiveBayes:
				classifier = new MyNaiveBayesClassifier(model);
				break;
			case DecisionTable:
				classifier = new MyDecisionTableClassifier(model);
				break;
			default:
				classifier = new MyDecisionTableClassifier(model);
				break;
		}

		// train the classifier
		success = classifier.train(replace);
		
		Form<Adult> adultForm = formFactory.form(Adult.class);
		// We pass the form and model details in order to build the select boxes

		return ok(classifierPage.render(success, classifier.getName(), adultForm, 
				DataModelDetails.WORKCLASS_ATTR,
				DataModelDetails.EDUCATION_ATTR,
				DataModelDetails.MARITAL_STATUS_ATTR,
				DataModelDetails.OCCUPATION_ATTR,
				DataModelDetails.RELATIONSHIP_ATTR,
				DataModelDetails.RACE_ATTR,
				DataModelDetails.SEX_ATTR,
				DataModelDetails.NATIVE_COUNTRY_ATTR
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
				DataModelDetails.WORKCLASS_ATTR,
				DataModelDetails.EDUCATION_ATTR,
				DataModelDetails.MARITAL_STATUS_ATTR,
				DataModelDetails.OCCUPATION_ATTR,
				DataModelDetails.RELATIONSHIP_ATTR,
				DataModelDetails.RACE_ATTR,
				DataModelDetails.SEX_ATTR,
				DataModelDetails.NATIVE_COUNTRY_ATTR
				));
	}
	
}
