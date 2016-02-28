/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.mycdipayment.listener;

import com.mtdo.learn.mycdipayment.event.PaymentEvent;
import com.mtdo.learn.mycdipayment.interceptor.Logged;
import com.mtdo.learn.mycdipayment.payment.Credit;
import com.mtdo.learn.mycdipayment.payment.Debit;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;

/**
 *
 * @author thangdo
 */
@Logged
@SessionScoped
public class PaymentHandler implements Serializable {
    private static final Logger logger =
        Logger.getLogger(PaymentHandler.class.getCanonicalName());

    
    public void creditPayment(@Observes @Credit PaymentEvent event){
        logger.log(Level.INFO, "PaymentHandler - Credit Handler: {0}", 
                event.toString());
        // call credit handler ...
    }
    
    public void debitPayment(@Observes @Debit PaymentEvent event){
        logger.log(Level.INFO,"PaymentHandler - Debit Handler: {0}",
                event.toString());
        // call debit handler ...
    }
}
