/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.mycdigreeting;

import javax.enterprise.context.Dependent;

/**
 *
 * @author thangdo
 */
@Informal
@Dependent
public class InformalGreeting extends Greeting{
    
    @Override
    public String greet(String name){
        return ("Hi " + name + "!");
    }
}
