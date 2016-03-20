package mtdo.learn.persistence.order.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mtdo.learn.persistence.order.entity.CustomerOrder;
import mtdo.learn.persistence.order.entity.LineItem;
import mtdo.learn.persistence.order.entity.Part;
import mtdo.learn.persistence.order.entity.PartKey;
import mtdo.learn.persistence.order.entity.Vendor;
import mtdo.learn.persistence.order.entity.VendorPart;

@Stateful
public class RequestBean {

    @PersistenceContext
    private EntityManager em;
    
    private static final Logger logger = Logger.getLogger("requestBean");
    
    // create parts
    public void createPart(String partNumber,
            int revision,
            String description,
            java.util.Date revisionDate,
            String specification,
            Serializable drawing) {
        try {
            Part part = new Part(partNumber,
                    revision,
                    description,
                    revisionDate,
                    specification,
                    drawing);
            logger.log(Level.INFO, "Created part {0}-{1}", new Object[]{partNumber, revision});
            em.persist(part);
            logger.log(Level.INFO, "Persisted part {0}-{1}", new Object[]{partNumber, revision});
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
    
    public void createVendorPart(String partNumber,
            int revision,
            String description,
            double price,
            int vendorId) {
        try {
            PartKey pkey = new PartKey();
            pkey.setPartNumber(partNumber);
            pkey.setRevision(revision);
            
            Part part = em.find(Part.class, pkey);
            
            VendorPart vendorPart = new VendorPart(description, price, part);
            em.persist(vendorPart);
            
            Vendor vendor = em.find(Vendor.class, vendorId);
            vendor.addVendorPart(vendorPart);
            vendorPart.setVendor(vendor);
            logger.log(Level.INFO, "Created vendor part {0}-{1}", new Object[]{vendorId, partNumber});
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<String> findVendorsByPartialName(String name){
        List<String> names = new ArrayList<>();
        try{
            List vendors = em.createNamedQuery("findVendorsByPartialName")
                    .setParameter("name", name)
                    .getResultList();
            for(Iterator iterator = vendors.iterator(); iterator.hasNext();){
                Vendor vendor = (Vendor)iterator.next();
                names.add(vendor.getName());
            }
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
        return names;
    }
    
    public void createVendor(int vendorId, 
            String name, 
            String address, 
            String contact, 
            String phone){
        try{
            Vendor vendor = new Vendor(vendorId, name, address, contact, phone);
            logger.log(Level.INFO, "Created vendor {0}-{1}", new Object[]{name, address});
            em.persist(vendor);
            logger.log(Level.INFO, "persisted vendor {0}-{1}", new Object[]{name, address});
        } catch (Exception e){
            throw new EJBException(e);
        }
    }    
    
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
            logger.log(Level.INFO, "persisted order {0}-{1}", new Object[]{orderId, shipmentInfo});
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

    public List<LineItem> getLineItems(int orderId){
        try{
            return em.createNamedQuery("findLineItemsByOrderId")
                    .setParameter("orderId", orderId)
                    .getResultList();
        }catch (Exception e){
            throw new EJBException(e.getMessage());
        }
    }

    public List<LineItem> getAllLineItems(){
        try{
            return em.createNamedQuery("findAllLineItems")
                    .getResultList();
        }catch (Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    public void addLineItem(Integer orderId, String partNumber, int revision, int quantity) {
        try{
            CustomerOrder order = em.find(CustomerOrder.class, orderId);
            logger.log(Level.INFO, "Found order ID {0}", orderId);

            PartKey pKey = new PartKey();
            pKey.setPartNumber(partNumber);
            pKey.setRevision(revision);

            Part part = em.find(Part.class, pKey);

            VendorPart vendorPart = part.getVendorPart();

            LineItem line = new LineItem(order, quantity, vendorPart);
            order.addLineItem(line);
            logger.log(Level.INFO, "Created line item {0}-{1}", new Object[]{orderId, partNumber});            
        }catch (Exception e){
            logger.log(Level.WARNING, "Couldn''t add {0} to order ID {1}.", new Object[]{partNumber, orderId});
            throw new EJBException(e.getMessage());
        }
    }

    public void addPartToBillOfMaterial(String bomPartNumber,
            int bomRevision,
            String partNumber,
            int revision) {
        logger.log(Level.INFO, "BOM part number: {0}", bomPartNumber);
        logger.log(Level.INFO, "BOM revision: {0}", bomRevision);
        logger.log(Level.INFO, "Part number: {0}", partNumber);
        logger.log(Level.INFO, "Part revision: {0}", revision);
        try {
            PartKey bomKey = new PartKey();
            bomKey.setPartNumber(bomPartNumber);
            bomKey.setRevision(bomRevision);
            
            Part bom = em.find(Part.class, bomKey);
            logger.log(Level.INFO, "BOM Part found: {0}", bom.getPartNumber());
            
            PartKey partKey = new PartKey();
            partKey.setPartNumber(partNumber);
            partKey.setRevision(revision);
            
            Part part = em.find(Part.class, partKey);
            logger.log(Level.INFO, "Part found: {0}", part.getPartNumber());
            bom.getParts().add(part);
            part.setBomPart(bom);
        } catch (EJBException e) {
            logger.warning(e.getMessage());
        }
    }
    
}
