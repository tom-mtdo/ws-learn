package mtdo.learn.persistence.order.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import mtdo.learn.persistence.order.ejb.RequestBean;
import mtdo.learn.persistence.order.entity.CustomerOrder;

@ManagedBean
@SessionScoped
public class OrderManager implements Serializable{
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    @EJB
    RequestBean requestBean;
    
    private List<CustomerOrder> orders;
    
    private Integer newOrderID;
    private String  newShipmentInfo;
    private char    newOrderStatus;
    private int     newOrderDiscount;

    public List<CustomerOrder> getOrders(){
        try {
            this.orders = requestBean.getOrders();
        } catch (Exception e){
            logger.warning("Could not get orders.");
        }
        return this.orders;
    }
    
    public void submitOrder(){
        try{
            requestBean.createOrder(newOrderID, newOrderStatus, newOrderDiscount, newShipmentInfo);
            logger.log(Level.INFO, "Create new order: order ID={0}, status={1}, " 
                    + "discount={2}, and shipping info={3}.",
                    new Object[]{newOrderID, newOrderStatus, newOrderDiscount, newShipmentInfo});
            this.newOrderID = null;
            this.newOrderDiscount = 0;
//            this.newOrderStatus = null;
            this.newShipmentInfo = null;
        } catch (Exception e){
            logger.warning("Problem creating order in submitOrder");
        }
        
    }
    
    public Integer getNewOrderID() {
        return newOrderID;
    }

    public void setNewOrderID(Integer newOrderID) {
        this.newOrderID = newOrderID;
    }

    public String getNewShipmentInfo() {
        return newShipmentInfo;
    }

    public void setNewShipmentInfo(String newShipmentInfo) {
        this.newShipmentInfo = newShipmentInfo;
    }

    public char getNewOrderStatus() {
        return newOrderStatus;
    }

    public void setNewOrderStatus(char newOrderStatus) {
        this.newOrderStatus = newOrderStatus;
    }

    public int getNewOrderDiscount() {
        return newOrderDiscount;
    }

    public void setNewOrderDiscount(int newOrderDiscount) {
        this.newOrderDiscount = newOrderDiscount;
    }
    
}
