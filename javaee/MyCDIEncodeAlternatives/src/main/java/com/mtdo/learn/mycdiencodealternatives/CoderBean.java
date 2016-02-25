/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.mycdiencodealternatives;

import javax.enterprise.context.RequestScoped;
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

    @Inject
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
