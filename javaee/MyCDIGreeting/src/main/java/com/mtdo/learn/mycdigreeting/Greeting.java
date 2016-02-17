/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.mycdigreeting;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;

/**
 *
 * @author thangdo
 */
@Default
@Dependent
public class Greeting {
    public String greet(String name){
        return ("Hello " + name + ".");
    }
}
