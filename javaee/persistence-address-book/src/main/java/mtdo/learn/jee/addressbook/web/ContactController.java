/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdo.learn.jee.addressbook.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import mtdo.learn.jee.addressbook.ejb.ContactFacade;
import mtdo.learn.jee.addressbook.entity.Contact;

/**
 *
 * @author thangdo
 */
@Named(value = "contactController")
@SessionScoped
public class ContactController implements Serializable{

    @EJB
    private ContactFacade contactFacade;
    
    /**
     * Creates a new instance of ContactController
     */
    public ContactController() {
    }
    
    public String prepareAdd(){
        addContact();
        return "/contact/Add";
    }

    public void addContact(){
        Contact contact = new Contact();
        contact.setFirstName("Tom");
        contact.setLastName("Do");
        contact.setMobilePhone("123");
        contact.setHomePhone("456");
        contact.setEmail("tom.mtdo@gmail.com");
        
        contactFacade.create(contact);
    }
    
}
