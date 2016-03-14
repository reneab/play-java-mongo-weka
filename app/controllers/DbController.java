package controllers;

import java.util.List;

import javax.inject.Inject;

import beans.Adult;
import dao.MongoDao;
import play.mvc.Controller;
import play.mvc.Result;

/** 
 * A sample {@link Controller} to retrieve all data from the training set in MongoDB and display in a web page
 * @author René
 *
 */
public class DbController extends Controller {

	@Inject 
	private MongoDao dao;
	
	public Result getAll() {
		StringBuilder sb = new StringBuilder();
		List<Adult> testSet = dao.getTrainingSet();
		for (Adult adult : testSet) {
			sb.append(adult.toString() + "\n");
		}
        return ok(sb.toString());
    }
}
