/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.mycdipayment.payment;

import com.mtdo.learn.mycdipayment.event.PaymentEvent;
import com.mtdo.learn.mycdipayment.interceptor.Logged;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Digits;

/**
 *
 * @author thangdo
 */
@Named
@SessionScoped
public class PaymentBean implements Serializable{
    public static final int DEBIT = 1;
    public static final int CREDIT = 2;    
    private int paymentType = DEBIT;
    
    @Digits(integer = 10, fraction = 2, message = "Invalid value")
    private BigDecimal value;
    
    private Date dateTime;
    
    @Inject
    @Credit
    Event<PaymentEvent> creditEvent;
    
    @Inject
    @Debit
    Event<PaymentEvent> debitEvent;
    
    private Logger logger;
    
    @Logged
    public String pay(){
        this.setDateTime(Calendar.getInstance().getTime());
        
        switch (paymentType){
            case DEBIT:
                PaymentEvent debitPay = new PaymentEvent();
                debitPay.setAmount(value);
                debitPay.setDatetime(dateTime);
                debitPay.setPaymentType("Debit");
                debitEvent.fire(debitPay);
                break;
            case CREDIT:
                PaymentEvent creditPay = new PaymentEvent();
                creditPay.setAmount(value);
                creditPay.setDatetime(dateTime);
                creditPay.setPaymentType("Credit");
                creditEvent.fire(creditPay);
                break;
            default:
                logger.severe("Invalid payment option");
        }
        
        return "response";
    }

    @Logged
    public void reset(){
        setPaymentType(DEBIT);
        setValue(BigDecimal.ZERO);
    }
    
    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Event<PaymentEvent> getCreditEvent() {
        return creditEvent;
    }

    public void setCreditEvent(Event<PaymentEvent> creditEvent) {
        this.creditEvent = creditEvent;
    }

    public Event<PaymentEvent> getDebitEvent() {
        return debitEvent;
    }

    public void setDebitEvent(Event<PaymentEvent> debitEvent) {
        this.debitEvent = debitEvent;
    }
    
    
    
}
