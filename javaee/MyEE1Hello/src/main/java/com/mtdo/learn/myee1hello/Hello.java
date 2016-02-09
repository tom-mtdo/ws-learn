/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.myee1hello;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author thangdo
 */
@Named
@RequestScoped
public class Hello {
    
    private String name;

    public Hello() {
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
}
