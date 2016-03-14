package mtdo.learn.persistence.order.ejb;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thangdo
 * initiate
 */

@Singleton
@Startup
public class ConfigBean {
    @PersistenceContext
    private EntityManager em;

}
