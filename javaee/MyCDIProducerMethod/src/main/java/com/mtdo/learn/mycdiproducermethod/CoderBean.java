/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.mycdiproducermethod;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author thangdo
 */
@Named
@RequestScoped
public class CoderBean {

    private String  inputString = "input string";
    private String  codedString = "coded string";
    @Max(26)
    @Min(0)
    @NotNull
    private int     tval = 0;
    public static final int TEST=1;
    public static final int REAL=2;
    private int     choosenCoder = TEST;

    public int getChoosenCoder() {
        return choosenCoder;
    }

    public void setChoosenCoder(int choosenCoder) {
        this.choosenCoder = choosenCoder;
    }


    @Produces
    @ActiveCoder
    @RequestScoped
    public Coder getCoder(){
        switch (choosenCoder){
            case TEST:
                return new TestCoderImpl();
            case REAL:
                return new CoderImpl();
            default:
                return null;
            }
    }
    
    @Inject
    @ActiveCoder
    @RequestScoped
    Coder coder;
    
    public void convert(){
        codedString = coder.codeString(inputString, tval);
    }
    
    public void reset(){
        inputString = "";
        codedString = "";
        tval = 0;
    }
    
    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public String getCodedString() {
        return codedString;
    }

    public void setCodedString(String codedString) {
        this.codedString = codedString;
    }

    public int getTval() {
        return tval;
    }

    public void setTval(int tval) {
        this.tval = tval;
    }

}
