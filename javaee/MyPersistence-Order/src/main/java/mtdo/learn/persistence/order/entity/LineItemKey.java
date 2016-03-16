package mtdo.learn.persistence.order.entity;

import java.io.Serializable;
import javax.persistence.Entity;

public final class LineItemKey implements Serializable{
    private int itemId;
    private Integer customerOrder;

    public LineItemKey() {
    }

    public LineItemKey(int itemId, Integer customerOrder) {
        this.itemId = itemId;
        this.customerOrder = customerOrder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getCustomerOrder() == null 
                ? 0: this.getCustomerOrder().hashCode()
                ^ (int)this.getItemId());
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (!(object instanceof LineItemKey)) {
            return false;
        }
        LineItemKey other = (LineItemKey) object;
        return ((this.getCustomerOrder() == null
                ? other.getCustomerOrder() == null : this.getCustomerOrder()
                .equals(other.getCustomerOrder()))
                && (this.getItemId() == other.getItemId()));
    }

    @Override
    public String toString() {
        return "" + this.getCustomerOrder() + "-" + this.getItemId();
    }
    
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Integer getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(Integer customerOrder) {
        this.customerOrder = customerOrder;
    }
    
}
