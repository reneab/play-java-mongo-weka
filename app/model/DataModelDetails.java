package model;

/** 
 * Utility class to declare model attributes and their possible values
 * 
 * */
public class DataModelDetails {

	// Declare the String attributes and their values
	public static final String[] WORKCLASS_ATTR = new String[] {"Private", "Self-emp-not-inc", "Self-emp-inc", "Federal-gov", "Local-gov", "State-gov", "Without-pay", "Never-worked"};
	public static final String[] EDUCATION_ATTR = new String[] {"Bachelors", "Some-college", "11th", "HS-grad", "Prof-school", "Assoc-acdm", "Assoc-voc", "9th", "7th-8th", "12th", "Masters", "1st-4th", "10th", "Doctorate", "5th-6th", "Preschool"};
	public static final String[] MARITAL_STATUS_ATTR = new String[] {"Married-civ-spouse", "Divorced", "Never-married", "Separated", "Widowed", "Married-spouse-absent", "Married-AF-spouse"};
	public static final String[] OCCUPATION_ATTR = new String[] {"Tech-support", "Craft-repair", "Other-service", "Sales", "Exec-managerial", "Prof-specialty", "Handlers-cleaners", "Machine-op-inspct", "Adm-clerical", "Farming-fishing", "Transport-moving", "Priv-house-serv", "Protective-serv", "Armed-Forces"};
	public static final String[] RELATIONSHIP_ATTR = new String[] {"Wife", "Own-child", "Husband", "Not-in-family", "Other-relative", "Unmarried"};
	public static final String[] RACE_ATTR = new String[] {"White", "Asian-Pac-Islander", "Amer-Indian-Eskimo", "Other", "Black"};
	public static final String[] SEX_ATTR = new String[] {"Female", "Male"};
	public static final String[] NATIVE_COUNTRY_ATTR = new String[] {"United-States", "Cambodia", "England", "Puerto-Rico", "Canada", "Germany", "Outlying-US(Guam-USVI-etc)", "India", "Japan", "Greece", "South", "China", "Cuba", "Iran", "Honduras", "Philippines", "Italy", "Poland", "Jamaica", "Vietnam", "Mexico", "Portugal", "Ireland", "France", "Dominican-Republic", "Laos", "Ecuador", "Taiwan", "Haiti", "Columbia", "Hungary", "Guatemala", "Nicaragua", "Scotland", "Thailand", "Yugoslavia", "El-Salvador", "Trinadad&Tobago", "Peru", "Hong", "Holand-Netherlands"};
	
	// Declare the class attribute along with its values
	public static final String[] CLASS_ATTR = new String[] {"<=50K", ">50K"};
	
}
