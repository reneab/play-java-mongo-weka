package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.bson.Document;

import play.Logger;
import play.Logger.ALogger;
import play.inject.ApplicationLifecycle;
import beans.Adult;
import beans.AdultDeserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * A {@code Singleton} class used to query the Mongo database
 * @author René
 *
 */
@Singleton
public class MongoDao {
	private static final ALogger LOG = Logger.of(MongoDao.class);

	private static final String PROPERTIES = "database.properties";
    private static final String TRAINING_COLLECTION = "training";
    private static final String TEST_COLLECTION = "test";
    
    private static Gson gson;
    
    private String dbName;
    private MongoClient mongoClient;
    
    /**
     * Constructor that initializes the {@link MongoClient} with a property file, 
     * and closes it when application stops
     * @param lifeCycle
     */
    @Inject
    private MongoDao(ApplicationLifecycle lifeCycle) {
    	try {
    		gson = new GsonBuilder().registerTypeAdapter(Adult.class, new AdultDeserializer()).create();
    		
    		LOG.info("Going to instanciate Mongo DB client...");
    		Properties props = new Properties();
			props.load(new FileInputStream(new File(PROPERTIES)));
			
			dbName = props.getProperty("dbname");
			mongoClient = new MongoClient(props.getProperty("host", "localhost"), Integer.valueOf(props.getProperty("port", "27017")));
			
			LOG.info("Successfully instanciated Mongo DB client to databse : " + dbName);
			
			lifeCycle.addStopHook(() -> {
				mongoClient.close();
				return CompletableFuture.completedFuture(null);
			});
		} catch (IOException e) {
			LOG.error("Could not instanciate connection from properties : " + PROPERTIES);
		}
    }
    
    /**
     * @return the {@link MongoDatabase} object of the database specified in the property file
     */
    private MongoDatabase getDb() {
    	return mongoClient.getDatabase(dbName);
    }
    
    /**
     * @return the training {@link MongoCollection} as a list of {@link Adult} objects
     */
    public List<Adult> getTrainingSet() {
    	LOG.info("Getting collection : Training");
    	MongoCollection<Document> collection = getDb().getCollection(TRAINING_COLLECTION);
    	LOG.info("Training set: " + collection.count());
    	
    	return returnAsAdultList(collection);
    }
    
    /**
     * @return the test {@link MongoCollection} as a list of {@link Adult} objects
     */
    public List<Adult> getTestSet() {
    	LOG.info("Getting collection : Test");
    	MongoCollection<Document> collection = getDb().getCollection(TEST_COLLECTION);
    	LOG.info("Test set: " + collection.count());

    	return returnAsAdultList(collection);
    }

    /**
     * Parses a {@link MongoCollection} to a list of {@link Adult} objects using {@link Gson}
     * @param collection the {@link MongoCollection} to use
     * @return a list of {@link Adult}
     */
	private List<Adult> returnAsAdultList(MongoCollection<Document> collection) {
		List<Adult> list = new LinkedList<>();
		FindIterable<Document> iterable = collection.find();
    	for (Document doc : iterable) {
    		try {
    			// TODO fix - this will not insert rows where data is missing or dirty
    			list.add(gson.fromJson(doc.toJson(), Adult.class));
    		} catch (JsonSyntaxException e) {
    			LOG.error("Unable to parse JSON : " + e.getMessage());
    		} catch (NumberFormatException e) {
    			LOG.error("Unable to parse number : " + e.getMessage());
    		} catch (NullPointerException e){
    		}
    	}
    	LOG.info("Parsing returned " + list.size() + " Adult objects");
    	return list;
	}
    
}
