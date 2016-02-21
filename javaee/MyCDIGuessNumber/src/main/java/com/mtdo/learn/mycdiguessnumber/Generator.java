/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.mycdiguessnumber;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 *
 * @author thangdo
 */
@ApplicationScoped
public class Generator {
    
    // for notation
    private int maxNum = 100;    
    private final java.util.Random random = new java.util.Random( System.currentTimeMillis() );

//    public java.util.Random getRandom(){
//        return random;
//    }

    @Produces @Random int getRanNum(){
        return random.nextInt(maxNum + 1);
//        return getRandom().nextInt(maxNum + 1);
    }
    
    @Produces @MaxNumber int getMaxNum(){
        return maxNum;
    }
}
