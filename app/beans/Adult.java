package beans;

/**
 * Adult object to be used as DTO between database, model and controller
 * @author René
 *
 */
public class Adult {
	
	private Integer age; 
	private String workclass; 
	private Integer fnlwgt; 
	private String education; 
	private Integer educationNum; 
	private String maritalStatus; 
	private String occupation; 
	private String relationship; 
	private String race; 
	private String sex; 
	private Integer capitalGain;
	private Integer capitalLoss;
	private Integer hoursPerWeek;
	private String nativeCountry;
	private String incomeClass;
	
	public Adult() {
		
	}
	
	public Adult(Integer age, String workclass, Integer fnlwgt,
			String education, Integer educationNum, String maritalStatus,
			String occupation, String relationship, String race, String sex,
			Integer capitalGain, Integer capitalLoss, Integer hoursPerWeek,
			String nativeCountry, String incomeClass) {
		this.age = age;
		this.workclass = workclass;
		this.fnlwgt = fnlwgt;
		this.education = education;
		this.educationNum = educationNum;
		this.maritalStatus = maritalStatus;
		this.occupation = occupation;
		this.relationship = relationship;
		this.race = race;
		this.sex = sex;
		this.capitalGain = capitalGain;
		this.capitalLoss = capitalLoss;
		this.hoursPerWeek = hoursPerWeek;
		this.nativeCountry = nativeCountry;
		this.incomeClass = incomeClass;
	}

	public Integer getAge() {
		return age;
	}

	public String getWorkclass() {
		return workclass;
	}

	public Integer getFnlwgt() {
		return fnlwgt;
	}

	public String getEducation() {
		return education;
	}

	public Integer getEducationNum() {
		return educationNum;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getRelationship() {
		return relationship;
	}

	public String getRace() {
		return race;
	}

	public String getSex() {
		return sex;
	}

	public Integer getCapitalGain() {
		return capitalGain;
	}

	public Integer getCapitalLoss() {
		return capitalLoss;
	}

	public Integer getHoursPerWeek() {
		return hoursPerWeek;
	}

	public String getNativeCountry() {
		return nativeCountry;
	}

	public String getIncomeClass() {
		return incomeClass;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}

	public void setWorkclass(String workclass) {
		this.workclass = workclass;
	}

	public void setFnlwgt(Integer fnlwgt) {
		this.fnlwgt = fnlwgt;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public void setEducationNum(Integer educationNum) {
		this.educationNum = educationNum;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setCapitalGain(Integer capitalGain) {
		this.capitalGain = capitalGain;
	}

	public void setCapitalLoss(Integer capitalLoss) {
		this.capitalLoss = capitalLoss;
	}

	public void setHoursPerWeek(Integer hoursPerWeek) {
		this.hoursPerWeek = hoursPerWeek;
	}

	public void setNativeCountry(String nativeCountry) {
		this.nativeCountry = nativeCountry;
	}

	public void setIncomeClass(String incomeClass) {
		this.incomeClass = incomeClass;
	}

	@Override
	public String toString() {
		return "Adult [age: " + age + ", workclassString: " + workclass
				+ ", fnlwgt: " + fnlwgt + ", education: " + education
				+ ", educationNum: " + educationNum + ", maritalStatus: "
				+ maritalStatus + ", occupation: " + occupation
				+ ", relationship: " + relationship + ", race: " + race
				+ ", sex: " + sex + ", capitalGain: " + capitalGain
				+ ", capitalLoss: " + capitalLoss + ", hoursPerWeek: "
				+ hoursPerWeek + ", nativeCountry: " + nativeCountry
				+ ", incomeClass: " + incomeClass + "]";
	}

	
	
}
