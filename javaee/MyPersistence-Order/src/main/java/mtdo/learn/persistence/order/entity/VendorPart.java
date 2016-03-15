package mtdo.learn.persistence.order.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="PERSISTENCE_ORDER_VENDOR_PART")
public class VendorPart implements Serializable {

    private Long vendorPartNumber;
    private String description;
    private double price;
    private Part part;
    private Vendor vendor;

    public VendorPart() {}
    
    public VendorPart(String description, double price, Part part) {
        this.description = description;
        this.price = price;
        this.part = part;
        part.setVendorPart(this);
    }
    
    @TableGenerator(
            name = "vendorPartGen",
            table = "PERSISTENCE_ORDER_SEQUENCE_GENERATOR",
            pkColumnName = "GEN_KEY",
            valueColumnName = "GEN_VALUE",
            pkColumnValue = "VENDOR_PART_ID",
            allocationSize = 10
    )
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "vendorPartGen")
    @Id
    public Long getVendorPartNumber() {
        return vendorPartNumber;
    }

    public void setVendorPartNumber(Long vendorPartNumber) {
        this.vendorPartNumber = vendorPartNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @OneToOne
    @JoinColumns({
        @JoinColumn(name="PARTNUMBER", referencedColumnName = "PARTNUMBER"),
        @JoinColumn(name="PARTREVISION", referencedColumnName = "REVISION"),
    })
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    @ManyToOne
    @JoinColumn(name = "VENDORID") //, referencedColumnName = "VENDORID")
    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    
}
