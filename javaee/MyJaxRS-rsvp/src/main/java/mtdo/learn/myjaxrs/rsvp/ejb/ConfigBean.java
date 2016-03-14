package mtdo.learn.myjaxrs.rsvp.ejb;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mtdo.learn.myjaxrs.rsvp.entity.Person;

@Singleton
@Startup
public class ConfigBean {
    @PersistenceContext
    private EntityManager em;
    private static final Logger logger = Logger.getLogger(ConfigBean.class.getName());
    
    @PostConstruct
    public void init(){
        Person dad = new Person();
    }
}
