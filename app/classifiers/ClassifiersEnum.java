package classifiers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ClassifiersEnum {
	J48, 
	NaiveBayes, 
	DecisionTable, 
	Bagging, 
	LogitBoost, 
	RandomForest;

	public static List<String> names() {
	    return Stream.of(ClassifiersEnum.values()).map(ClassifiersEnum::name).collect(Collectors.toList());
	}
}
