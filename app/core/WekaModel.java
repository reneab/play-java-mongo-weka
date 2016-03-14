package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import play.Logger;
import play.Logger.ALogger;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import beans.Adult;

/**
 * A Singleton class that holds the datamodel as Weka {@link Attribute}, as well as the training set and the test set
 * @author René
 *
 */
@Singleton
public class WekaModel {

	private static final ALogger LOG = Logger.of(WekaModel.class);

	private ArrayList<Attribute> wekaAttributes = new ArrayList<>();
	private Instances trainingSet;
	private Instances testSet;

	@Inject
	public WekaModel() {
		LOG.info("Creating weka model...");

		// Declare the feature vector
		wekaAttributes.add(new Attribute("age")); //numeric
		wekaAttributes.add(new Attribute("workclass", Arrays.asList(DataModelDetails.WORKCLASS_ATTR)));
		wekaAttributes.add(new Attribute("fnlwgt")); //numeric
		wekaAttributes.add(new Attribute("ecudation", Arrays.asList(DataModelDetails.EDUCATION_ATTR)));
		wekaAttributes.add(new Attribute("education-num")); //numeric
		wekaAttributes.add(new Attribute("marital-status", Arrays.asList(DataModelDetails.MARITAL_STATUS_ATTR)));
		wekaAttributes.add(new Attribute("occupation", Arrays.asList(DataModelDetails.OCCUPATION_ATTR)));
		wekaAttributes.add(new Attribute("relationship", Arrays.asList(DataModelDetails.RELATIONSHIP_ATTR)));
		wekaAttributes.add(new Attribute("race", Arrays.asList(DataModelDetails.RACE_ATTR)));
		wekaAttributes.add(new Attribute("sex", Arrays.asList(DataModelDetails.SEX_ATTR)));
		wekaAttributes.add(new Attribute("capital-gain")); //numeric
		wekaAttributes.add(new Attribute("capital-loss")); //numeric
		wekaAttributes.add(new Attribute("hours-per-week")); //numeric
		wekaAttributes.add(new Attribute("native-country", Arrays.asList(DataModelDetails.NATIVE_COUNTRY_ATTR)));
		wekaAttributes.add(new Attribute("@@class@@", Arrays.asList(DataModelDetails.CLASS_ATTR))); // class attribute
		
	}
	
	/**
	 * Fills the training set with a list of {@link Adult}
	 * @param list
	 */
	public void fillTrainingSet(List<Adult> list) {
		LOG.info("Filling training set with " + list.size() + " entries...");
		// Initialize instances
		trainingSet = new Instances("Relation", wekaAttributes, 10);
		// Set class index
		trainingSet.setClassIndex(14);

		for (Adult adult : list) {
			trainingSet.add(getInstanceFromAdult(adult));
		}
		LOG.info("Successfully filled training set!");
	}
	
	/**
	 * Fills the test set with a list of {@link Adult}
	 * @param list
	 */
	public void fillTestSet(List<Adult> list) {
		LOG.info("Filling test set with " + list.size() + " entries...");
		// Initialize instances
		testSet = new Instances("Relation", wekaAttributes, 10);
		// Set class index
		testSet.setClassIndex(14);
		
		for (Adult adult : list) {
			testSet.add(getInstanceFromAdult(adult));
		}
		LOG.info("Successfully filled test set!");
	}

	/**
	 * Transforms an {@link Adult} object into a {@link Instance} object
	 * @param adult
	 * @return
	 */
	public Instance getInstanceFromAdult(Adult adult) {
		Instance instance = new DenseInstance(15);
		if (adult.getAge() != null) {
			instance.setValue(wekaAttributes.get(0), adult.getAge());
		}
		fillInstanceWithValueOrMissing(wekaAttributes.get(1), instance, adult.getWorkclass());
		if (adult.getFnlwgt() != null) {
			instance.setValue(wekaAttributes.get(2), adult.getFnlwgt());
		}
		fillInstanceWithValueOrMissing(wekaAttributes.get(3), instance, adult.getEducation());
		if (adult.getEducationNum() != null) { 
			instance.setValue(wekaAttributes.get(4), adult.getEducationNum());
		}
		fillInstanceWithValueOrMissing(wekaAttributes.get(5), instance, adult.getMaritalStatus());
		fillInstanceWithValueOrMissing(wekaAttributes.get(6), instance, adult.getOccupation());
		fillInstanceWithValueOrMissing(wekaAttributes.get(7), instance, adult.getRelationship());
		fillInstanceWithValueOrMissing(wekaAttributes.get(8), instance, adult.getRace());
		fillInstanceWithValueOrMissing(wekaAttributes.get(9), instance, adult.getSex());
		if (adult.getCapitalGain() != null) {
			instance.setValue(wekaAttributes.get(10), adult.getCapitalGain());
		}
		if (adult.getCapitalLoss() != null) {
			instance.setValue(wekaAttributes.get(11), adult.getCapitalLoss());
		}
		if (adult.getHoursPerWeek() != null) {
			instance.setValue(wekaAttributes.get(12), adult.getHoursPerWeek());
		}
		fillInstanceWithValueOrMissing(wekaAttributes.get(13), instance, adult.getNativeCountry());
		fillInstanceWithValueOrMissing(wekaAttributes.get(14), instance, adult.getIncomeClass());
		return instance;
	}

	/**
	 * Utility method to fill the instance with a String value, if not {@code null} and different from "?"
	 * @param attr a {@link Attribute} object
	 * @param instance the {@link Instance} to fill
	 * @param value the String value to evaluate and insert
	 */
	private void fillInstanceWithValueOrMissing(Attribute attr, Instance instance, String value) {
		if (value != null && !value.equalsIgnoreCase("?") ) {
			instance.setValue(attr, value);
		}
	}

	public ArrayList<Attribute> getModelAttributes() {
		return wekaAttributes;
	}

	public Instances getTrainingSet() {
		return trainingSet;
	}

	public Instances getTestSet() {
		return testSet;
	}

}
