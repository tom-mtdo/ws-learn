package mtdo.learn.persistence.order.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.TIMESTAMP;
import javax.persistence.Transient;

@Entity
@Table(name="MY_PERSISTENCE_ORDER_CUSTOMERORDER")
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
    private Collection<LineItem> lineItems;

    public CustomerOrder() {
        this.lastUpdate = new Date();
        this.lineItems = new ArrayList<>();
    }
    
    public CustomerOrder(Integer orderId, char status, int discount, 
            String shipmentInfo) {
        this.orderId = orderId;
        this.status = status;
        this.lastUpdate = new Date();
        this.discount = discount;
        this.shipmentInfo = shipmentInfo;
        this.lineItems = new ArrayList<>();
    }

    @Id
    public Integer getOrderId() {
        return orderId;
    }

    @OneToMany(cascade = ALL, mappedBy = "customerOrder")
    public Collection<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(Collection<LineItem> lineItems) {
        this.lineItems = lineItems;
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

    @Temporal(TIMESTAMP)
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
    
    @Transient
    public int getNextId(){
        return this.lineItems.size() + 1;
    }
    
    public double calculateAmount(){
        double ammount = 0;
        Collection<LineItem> items = getLineItems();
        for (LineItem item : items) {
            VendorPart part = item.getVendorPart();
            ammount += part.getPrice() * item.getQuantity();
        }
        return (ammount * (100 - getDiscount()))/100;
    }

    public void addLineItem(LineItem line) {
        this.lineItems.add(line);
    }
    
}
