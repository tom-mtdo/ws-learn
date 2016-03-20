package mtdo.learn.persistence.order.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import mtdo.learn.persistence.order.ejb.RequestBean;
import mtdo.learn.persistence.order.entity.CustomerOrder;
import mtdo.learn.persistence.order.entity.LineItem;

@ManagedBean
@SessionScoped
public class OrderManager implements Serializable{
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    @EJB
    RequestBean requestBean;
    
    private String vendorName;
    private List<String> vendorSearchResults;
    
    private Integer currentOrder;
    private List<CustomerOrder> orders;    
    private Integer newOrderID;
    private String  newShipmentInfo;
    private char    newOrderStatus;
    private int     newOrderDiscount;

    public List<LineItem> getLineItems(){
        try{
            return requestBean.getLineItems(this.currentOrder);
        }catch(Exception e){
            logger.warning(e.getMessage());
            return null;
        }
    }

    public List<LineItem> getAllLineItems(){
        try{
            return requestBean.getLineItems(this.currentOrder);
        }catch(Exception e){
            logger.warning(e.getMessage());
            return null;
        }
    }
    
    public void findVendors(){
        try{
            this.vendorSearchResults = requestBean.findVendorsByPartialName(vendorName);
            logger.log(Level.INFO, "Found {0} vendor(s) using search string {1}", 
                    new Object[]{vendorSearchResults.size(), vendorName});
        } catch(Exception e){
            logger.warning("Problem searching for Vendors");
        }
    }
    
    public void removeOrder(ActionEvent event){
        try{
            UIParameter para = (UIParameter) event.getComponent().findComponent("deleteOrderId");
            Integer id = Integer.parseInt(para.getValue().toString());
            requestBean.removeOrder(id);
        }catch(Exception e){
            logger.warning("Error when deleting order");
        }
    }
    
    public List<CustomerOrder> getOrders(){
        try {
            this.orders = requestBean.getOrders();
        } catch (Exception e){
            logger.warning("Could not get orders.");
            logger.warning(e.getMessage());
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
    
    public Integer getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(int currentOrder) {
        this.currentOrder = currentOrder;
    }    

    public List<String> getVendorSearchResults() {
        return vendorSearchResults;
    }

    public void setVendorSearchResults(List<String> vendorSearchResults) {
        this.vendorSearchResults = vendorSearchResults;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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
