/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.mycdidecorating;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

/**
 *
 * @author thangdo
 */
@Decorator
public abstract class CoderDecorator implements Coder{
    @Inject
    @Delegate
    @Any
    Coder coder;

    @Override
    public String codeString(String s, int tval){
        int leng = s.length();
        
        return "\"" + s + "\" becomes " + "\"" + coder.codeString(s, tval)
                + "\", " + leng + " characters in length";
    }
    
    
}
