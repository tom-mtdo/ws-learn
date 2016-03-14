package mtdo.learn.persistence.order.entity;

import java.io.Serializable;

/**
 *
 * @author thangdo
 */
public class PartKey implements Serializable{
    private String partNumber;
    private int revision;
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getPartNumber() == null ? 0 : this.getPartNumber().hashCode())
                ^
                ((int) this.getRevision());
        return hash;
    }

    @Override
    public boolean equals(Object otherOb) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (this == otherOb) {
            return true;
        }
        if (!(otherOb instanceof PartKey)) {
            return false;
        }
        
        PartKey other = (PartKey) otherOb;
        if ((this.getPartNumber() == null && other.getPartNumber() != null) || 
                (this.getPartNumber() != null && !this.getPartNumber().equals(other.getPartNumber())) ) {
            return false;
        }        
        if (this.getRevision() != other.getRevision()){
            return false;
        }
        return true;
    }    

    @Override
    public String toString() {
        return "PartKey{" + "partNumber=" + partNumber + ", revision=" + revision + '}';
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }
    
}
