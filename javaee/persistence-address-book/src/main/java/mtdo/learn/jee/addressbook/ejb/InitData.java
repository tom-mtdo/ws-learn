/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdo.learn.jee.addressbook.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import mtdo.learn.jee.addressbook.entity.Contact;

/**
 *
 * @author thangdo
 */
@Singleton
@Startup
public class InitData {
    
    @EJB
    private ContactFacade contactFacade;
    
    @PostConstruct
    public void generateContacts(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {            
            Contact contact = new Contact();
            contact.setFirstName("John1");
            contact.setLastName("Smith1");
            contact.setBirthday(sdf.parse("01/01/1977"));
            contact.setHomePhone("123");
            contact.setMobilePhone("456");
            contact.setEmail("john1@smith1.com");
            contactFacade.create(contact);
            
            contact = new Contact();
            contact.setFirstName("John2");
            contact.setLastName("Smith2");
            contact.setBirthday(sdf.parse("02/02/1977"));
            contact.setHomePhone("123");
            contact.setMobilePhone("456");
            contact.setEmail("john2@smith2.com");            
            contactFacade.create(contact);

            contact = new Contact();
            contact.setFirstName("John3");
            contact.setLastName("Smith3");
            contact.setBirthday(sdf.parse("03/03/1977"));
            contact.setHomePhone("123");
            contact.setMobilePhone("456");
            contact.setEmail("john3@smith3.com");            
            contactFacade.create(contact);

            contact = new Contact();
            contact.setFirstName("John4");
            contact.setLastName("Smith4");
            contact.setBirthday(sdf.parse("04/03/1977"));
            contact.setHomePhone("123");
            contact.setMobilePhone("456");
            contact.setEmail("john4@smith4.com");            
            contactFacade.create(contact);

            contact = new Contact();
            contact.setFirstName("John5");
            contact.setLastName("Smith5");
            contact.setBirthday(sdf.parse("05/03/1977"));
            contact.setHomePhone("123");
            contact.setMobilePhone("456");
            contact.setEmail("john5@smith5.com");            
            contactFacade.create(contact);

        } catch (ParseException ex) {
            Logger.getLogger(InitData.class.getName()).log(Level.SEVERE, null, ex);
        }

            
//        try {
//            contactFacade.createContact("John1", "Smith", sdf.parse("01/01/1977"),
//                    "123456", "123456", "John1@Smith.com");
//            contactFacade.createContact("John2", "Smith", sdf.parse("02/01/1977"),
//                    "123456", "123456", "John2@Smith.com");
//            contactFacade.createContact("John3", "Smith", sdf.parse("03/01/1977"),
//                    "123456", "123456", "John3@Smith.com");
//
//        } catch (ParseException ex) {
//            Logger.getLogger(InitData.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
