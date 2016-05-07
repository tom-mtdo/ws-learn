/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdo.learn.jee.addressbook.web;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
//import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
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


    public String prepareView(Contact contact){
//        have to use getItems(), if use items then not work 
//      Why???????????? because session finish ?
//        currentContact = (Contact)getItems().getRowData();        
        currentContact = contact;
        //selectedIndex = getItems().getRowIndex();

//        FacesContext context = FacesContext.getCurrentInstance();
//        context.addMessage(null, new FacesMessage("First Name " + currentContact.getFirstName()));

        return "/contact/View";
    }

    public String prepareEdit(Contact contact){
//        currentContact = (Contact)getItems().getRowData();
//        selectedIndex = getItems().getRowIndex();
        currentContact = contact;
        return "/contact/Edit";
    }
    
    public String addContact(){
        contactFacade.create(currentContact);
        
        // inform success
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Contact created", "Contact created");
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);

        return prepareAdd();
    }
    
    public String editContact(){
        contactFacade.edit(currentContact);

        // inform success
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Contact edited", "Contact edited");
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);

        return "/contact/Edit";        
    }

    public String deleteContact(Contact contact){
        contactFacade.remove(contact);
        items = null;
        
        // inform success
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Contact deleted", "Contact deleted");
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);

        return "/contact/List";
    }

    @FacesConverter(forClass=Contact.class)
    public static class ContactControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContactController controller = (ContactController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "contactController");
            return controller.contactFacade.find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Contact) {
                Contact o = (Contact) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + 
                        object + 
                        " is of type " + 
                        object.getClass().getName() + 
                        "; expected type: " +
                        ContactController.class.getName());
            }
        }

    }

    
}
