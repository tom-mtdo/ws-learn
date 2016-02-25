/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.mycdiproducermethod;

import javax.enterprise.inject.Alternative;

/**
 *
 * @author thangdo
 */
public class TestCoderImpl implements Coder{
    
    @Override
    public String codeString(String s, int tval){
        return ("Test implementation: input string is " + s + ", shift value is " + tval);
    }
}
