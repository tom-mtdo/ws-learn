/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.mycdigreeting;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author thangdo
 */
@Named("MyPrinter")
@RequestScoped
public class Printer {
    private 
    @Inject 
    @Informal 
    Greeting greeting;
    
    private String name;
    private String strGreeting;

    public void generateStrGreeting(){
        strGreeting = greeting.greet(name);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrGreeting() {
        return strGreeting;
    }

//    public void setStrGreeting(String strGreeting) {
//        this.strGreeting = strGreeting;
//    }
    
}
