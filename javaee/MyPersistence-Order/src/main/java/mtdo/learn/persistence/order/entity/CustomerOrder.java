package mtdo.learn.persistence.order.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="PERSISTENCE_ORDER_CUSTOMERORDER")
@NamedQuery(
        name = "findAllOrders",
        query = "SELECT co FROM CustomerOrder co " + 
                "ORDER BY co.orderId"
)
public class CustomerOrder implements Serializable {

    private Integer orderId;
    private char    status;
    private Date    lastUpdate;
    private int     discount;
    private String shipmentInfo;
//    private Collection<LineItem> lineItems;

    public CustomerOrder() {
        this.lastUpdate = new Date();
//        this.lineItems = new ArrayList<>();
    }
    
    public CustomerOrder(Integer orderId, char status, int discount, 
            String shipmentInfo) {
        this.orderId = orderId;
        this.status = status;
        this.lastUpdate = new Date();
        this.discount = discount;
        this.shipmentInfo = shipmentInfo;
//        this.lineItems = new ArrayList<>();
    }

    @Id
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getShipmentInfo() {
        return shipmentInfo;
    }

    public void setShipmentInfo(String shipmentInfo) {
        this.shipmentInfo = shipmentInfo;
    }

//    public Collection<LineItem> getLineItems() {
//        return lineItems;
//    }
//
//    public void setLineItems(Collection<LineItem> lineItems) {
//        this.lineItems = lineItems;
//    }
    
}
