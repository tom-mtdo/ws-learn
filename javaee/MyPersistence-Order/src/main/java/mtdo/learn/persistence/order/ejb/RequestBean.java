package mtdo.learn.persistence.order.ejb;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mtdo.learn.persistence.order.entity.CustomerOrder;

/**
 *
 * @author thangdo
 */
@Stateful
public class RequestBean {
    
    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    @PersistenceContext
    private EntityManager em;
    
    public void removeOrder(Integer orderId){
        try{
            CustomerOrder order = em.find(CustomerOrder.class, orderId);
            em.remove(order);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    public void createOrder(Integer orderId, char status, int discount, String shipmentInfo){
        try{
            CustomerOrder order = new CustomerOrder(orderId, status, discount, shipmentInfo);
            em.persist(order);
        } catch (Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<CustomerOrder> getOrders(){
        try{
            return (List<CustomerOrder>) em.createNamedQuery("findAllOrders").getResultList();
        } catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
}
