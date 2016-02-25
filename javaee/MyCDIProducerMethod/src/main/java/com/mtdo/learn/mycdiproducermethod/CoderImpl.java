



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.mycdiproducermethod;

/**
 *
 * @author thangdo
 */
public class CoderImpl implements Coder{

    @Override
    public String codeString(String s, int tval) {
        return("Real implementation: " + s + ", " + tval + ", " + s);
    }
    
}
