/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.mycdiguessnumber;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author thangdo
 */
@Named("guessNumBean")
@SessionScoped
public class GuessNumBean implements Serializable{
    @Inject
    @Random
    private Instance<Integer> ranNumInt;
    
    @Inject
    @MaxNumber
    private int maxNum;
    
    private int ranNum;
    private int highNum;
    private int lowNum;
    private int guessNum;

    @PostConstruct
    public void reset(){
        this.highNum = maxNum;
        this.lowNum = 0;
        this.guessNum = 0;
        this.ranNum = ranNumInt.get();
        
    }
    
    public void check(){
        
        if ( guessNum == ranNum ){
            FacesContext.getCurrentInstance().addMessage( null, new FacesMessage("Correct!") );
        }
        
        if(guessNum < ranNum){
            lowNum = guessNum + 1;
        }
        
        if(guessNum > ranNum){
            highNum = guessNum - 1;
        }
        
    }
    
    public int getRanNum() {
        return ranNum;
    }

    public void setRanNum(int ranNum) {
        this.ranNum = ranNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public int getHighNum() {
        return highNum;
    }

    public void setHighNum(int highNum) {
        this.highNum = highNum;
    }

    public int getLowNum() {
        return lowNum;
    }

    public void setLowNum(int lowNum) {
        this.lowNum = lowNum;
    }

    public int getGuessNum() {
        return guessNum;
    }

    public void setGuessNum(int guessNum) {
        this.guessNum = guessNum;
    }
    
    
}
