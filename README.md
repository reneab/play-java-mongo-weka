## This is a Play application
It uses the Play framework to build a web application with a web UI to evaluate and use machine learning algorithms for classification purposes.

### The problem
Data used is the Census Income dataset that can be found here : https://archive.ics.uci.edu/ml/datasets/Census+Income
There is a data set and a test set.
Prediction task is to determine whether a person makes over 50K a year.

First I converted these 2 files to .arff format to experiment classification algorightms using Weka desktop app 
(http://www.cs.waikato.ac.nz/ml/weka/)
Weka output gives you detailed accuracy results. So I focused on the algorithms that were giving me good results in terms of :
 - Correctly Classified Instances %
 - Precision = true_positive / (true_positive + false_positive)
 - Recall = true_positive / (true_positive + false_negative)
 - ROC area

Weka also allows you replace missing values ("?" in the dataset), as some algorithm cannot handle these missing values.
On the other algorithms, in some cases it would improve my results (ex: J48 decision tree), and in others it would worsen them, because it replaces missing values with means of the other values in the dataset, which can be a bias.

You can also experiment with other parameters than the defaulted ones on the algorithm, in order to try and get better results (ex: on the Random Forest algorithm, which builds a "forest" of decision trees on randomly selected sub-data sets, you can set 
more trees per forest - it will make it slower but will give more accurante results)

Finally I selected the following algorithms that I added in my code, using Weka java libraries, along with the best values I could find 
for the parameters:
- J48 decision tree (up to 86.2% accuracy on this dataset)
- Decision table (85.9%)
- Random Forest (84.9% on 100 trees)
- Bagging (84.9% - it splits the data in sub-data sets, and creates a classifier to run on each sample, improving it according to the specificities of the sample to get better accuracy on it)
- LogitBoost (85%)
- Naive Bayes (83% - this one is very famous but runs rather poorly on this data, as it requires each "column" to be more uniformly distributed)

### Implementing
When using Weka libraries, you have to build a model as a list of `Attributes` object: they contain the name of each column and their possible values in case of nominal (`WekaModel.java`)

I then built a MongoDB database (as data in the real world that often does not come in a text file...), a DAO and the DTO object to hold this data (in this case, the `Adult` class)
For the purpose of this exercice, I imported data in MongoDB using the mongoimport command line executable on CSV files:
  mongoimport -d adult -c training --type csv --file adult.data.csv --headerline
  mongoimport -d adult -c test --type csv --file adult.test.csv --headerline

Note: you have to put a header in you csv file, with the names of the columns matching the attributes of the Adult object in order 
  for `GSON` serializer to work properly.
  Or you can implement you own custom `GSON` deserializer.

You then have to parse the `Adult` objects to Weka `Instance` objects. This is also done in the `WekaModel class`. For missing values (null or "?" in the original data and MongoDB) you don't have to set the value on the instance and Weka will understand that it is missing.
Missing values can be "cleaned" before training the classifier by using Weka `ReplaceMissingValues` class.
  
Then I built the interface and the main controller (`ClassifierController.java`) which instanciate the Mongo connection, the model, and one the classifier mentionned above.
It allows you to:
- select the classifier
- choose if you want to replace missing values or not
- evaluate the algorithm selected on the test set
- cross-validate that algorithm 10 folds (= split the training set in 10 sub-sets, select 1, train the algorithm on each of the 9 remaning and test it on the 1 selected - repeat until all sub-sets have been used as test set)
- simulate a classification of an individual by entering his details in a form. The result will be displayed at the bottom and be either: <=50K or >50K

This uses the class `AbstractClassifier` that contains the model and the methods to perform the above.


You can see the result in my Heroku app : https://agile-cove-61490.herokuapp.com/


### TO IMPROVE:
 - Parsing data from MongoDB to `Adult` objets : some errors occured when I tried to import the test set as Adult objects because of poor
 formatting and unmatching values (some entries that are supposed to be numbers turned out to be strings). I chose in a first step to 
 ignore these entries and not add them to my test set at all
 - display a proper error message/page when MongoDB connection cannot be established
 - Filtering the data from the GUI to avoid injections
 - Select the numbers of folds to perform cross-validation


### FEATURES
#### Controllers

- `ClassifierController.java`:

  Handles the main page and events : /, /evaluate, and /cross-validate 

- `DbController.java`:

  Shows the training data as json objects: /db

