/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.guessnumber.jsf;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author thangdo
 */
//@Named(value = "guessNumber")
@Named(value = "guessNumber")
@SessionScoped
public class GuessNumber implements Serializable {
    
    private static final long serialVersionUID = 5443351151396868724L;
    Integer randomInt = null;
    private Integer guessInt = null;
    private int maxInt = 10;
    private int minInt = 0;
    
    String response = null;
    
    /**
     * Creates a new instance of GuessNumber
     */
    public GuessNumber() {
        Random random = new Random();
        randomInt = new Integer(random.nextInt(maxInt + 1));
        System.out.println("Duke's number: " + randomInt);
    }

    public Integer getRandomInt() {
        return randomInt;
    }

    public void setRandomInt(Integer randomInt) {
        this.randomInt = randomInt;
    }

    public Integer getGuessInt() {
        return guessInt;
    }

    public void setGuessInt(Integer guessInt) {
        this.guessInt = guessInt;
    }

    public int getMaxInt() {
        return maxInt;
    }

    public void setMaxInt(int maxInt) {
        this.maxInt = maxInt;
    }

    public int getMinInt() {
        return minInt;
    }

    public void setMinInt(int minInt) {
        this.minInt = minInt;
    }

    public String getResponse() {
        if ( (guessInt == null) || (guessInt.compareTo(randomInt)!= 0) ) {
            return("Sorry, wrong number!");
        } else {
            return("Congratulate, you rock!");
        }

    }

    public void setResponse(String response) {
        this.response = response;
    }

    
}
