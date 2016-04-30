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
import javax.faces.application.FacesMessage;
//import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import mtdo.learn.jee.addressbook.ejb.ContactFacade;
import mtdo.learn.jee.addressbook.entity.Contact;

/**
 *
 * @author thangdo
 */
@Named
@SessionScoped
public class ContactController implements Serializable{

    @EJB
    private ContactFacade contactFacade;
    private DataModel items = null;
    private Contact currentContact;
    // which contact was selected, -1: new contact
    private int selectedIndex; 

    public DataModel getItems() {
        if (items == null){
            return new ListDataModel(contactFacade.findRange(new int[]{0,10}));
        }
        return items;
    }

    public Contact getSelectedContact() {
        if (currentContact == null){
            currentContact = new Contact();
            selectedIndex = -1;
        }
        return currentContact;
    }

    public void setCurrentContact(Contact currentContact) {
        this.currentContact = currentContact;
    }
    
    /**
     * Creates a new instance of ContactController
     */
    public ContactController() {
    }
    
    public String prepareAdd(){
//        addContact();
        currentContact = new Contact();
        selectedIndex = -1;
        return "/contact/Add";
    }

    public String prepareView(){
//    public void prepareView(){
//        addContact();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Before get data"));
        currentContact = (Contact)getItems().getRowData();
        context.addMessage(null, new FacesMessage("First name: " + currentContact.getFirstName()));        
//        
//        context.addMessage(null, new FacesMessage("After get data"));
//        
//        selectedIndex = items.getRowIndex();
        return "/contact/View";
    }

    
    public String addContact(){
        contactFacade.create(currentContact);
        return prepareAdd();
    }
    
}
