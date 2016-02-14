/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.html5;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author thangdo
 */
@Named(value = "reservationBean")
@SessionScoped
public class reservationBean implements Serializable{
    private String  name="";
    private String  email="";
    private String  emailAgain = "";
    private String  performanceDate = "";
    private String  noTicket = "1";
    private String  ticketPrice = "90";
    private String  totalValue = "90";
    /**
     * Creates a new instance of reservationBean
     */
    public reservationBean() {
    }

    public void calculateTotal(AjaxBehaviorEvent event)
        throws AbortProcessingException{
        int intNoTicket = 1;
        int intTicketPrice = 0;
        int total = 0;
        
        if( noTicket.trim().length() > 0 ){
            intNoTicket = Integer.parseInt(noTicket);
        }
        if ( ticketPrice.trim().length() > 0 ) {
            intTicketPrice = Integer.parseInt(ticketPrice);
        }
        total = intNoTicket * intTicketPrice;
        totalValue = String.valueOf(total) + ".00";
    }
    
    public void clear(AjaxBehaviorEvent event)
            throws AbortProcessingException{
        name="";
        email="";
        emailAgain = "";
        performanceDate = "";
        noTicket = "1";
        ticketPrice = "90";
        totalValue = "90.00";
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailAgain() {
        return emailAgain;
    }

    public void setEmailAgain(String emailAgain) {
        this.emailAgain = emailAgain;
    }

    public String getPerformanceDate() {
        return performanceDate;
    }

    public void setPerformanceDate(String performanceDate) {
        this.performanceDate = performanceDate;
    }

    public String getNoTicket() {
        return noTicket;
    }

    public void setNoTicket(String noTicket) {
        this.noTicket = noTicket;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(String totalValue) {
        this.totalValue = totalValue;
    }

   
}
