/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.mycdipayment.event;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author thangdo
 */
public class PaymentEvent implements Serializable{
    public String       paymentType;
    public BigDecimal   amount;
    public Date         datetime;

    public PaymentEvent() {
    }

    @Override
    public String toString() {
        return "PaymentEvent{" + "paymentType=" + paymentType + ", amount=" + amount + ", datetime=" + datetime + '}';
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
    
    
}
