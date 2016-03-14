import play.data.FormFactory;

import com.google.inject.AbstractModule;

import core.WekaModel;
import dao.MongoDao;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

    @Override
    public void configure() {
        
    	// declare beans as singletons to be loaded eagerly (at server startup)
        bind(MongoDao.class).asEagerSingleton();
        bind(WekaModel.class).asEagerSingleton();
        bind(FormFactory.class).asEagerSingleton();
        
    }

}
