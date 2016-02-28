/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.mycdipayment.interceptor;

import java.io.Serializable;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author thangdo
 */
@Logged
@Interceptor
public class LoggedInterceptor implements Serializable{

    public LoggedInterceptor() {
    }
    
    @AroundInvoke
    public Object logMethodEnty(InvocationContext invocationContext) throws Exception{
        System.out.println("Entering method: "
        + invocationContext.getMethod().getName() + " in class "
        + invocationContext.getMethod().getDeclaringClass().getName());
        
        return invocationContext.proceed();
    }
}
