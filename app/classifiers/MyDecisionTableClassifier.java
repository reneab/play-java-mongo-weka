package classifiers;

import model.WekaModel;
import weka.classifiers.rules.DecisionTable;

public class MyDecisionTableClassifier extends AbstractClassifier {

	public MyDecisionTableClassifier(WekaModel model) {
		super(new DecisionTable(), model);
	}

	@Override
	public String getName() {
		return "Decision Table";
	}

}
