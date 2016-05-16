/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdo.learn.jee.addressbook.ejb;

import java.util.Date;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mtdo.learn.jee.addressbook.entity.Contact;

/**
 *
 * @author thangdo
 */
//@Stateless
@Stateful
public class ContactFacade extends AbstractFacade{

    @PersistenceContext(unitName = "my-persistence-address-bookPU")
    private EntityManager em;

    public ContactFacade() {
        super(Contact.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public Contact createContact(
            String firstName,
            String lastName,
            Date birthDay,
            String homePhone,
            String mobilePhone,
            String email){

        Contact contact = new Contact(firstName, lastName, birthDay,
            homePhone, mobilePhone, email);
        super.create(contact);
        return contact;
    }
}
