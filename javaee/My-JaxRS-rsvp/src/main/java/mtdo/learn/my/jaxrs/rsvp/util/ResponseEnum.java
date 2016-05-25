/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdo.learn.my.jaxrs.rsvp.util;

/**
 *
 * @author thangdo
 */
public enum ResponseEnum {
    ATTENDING("Attending"),
    NOT_ATTENDING("Not attending"),
    MAYBE_ATTENDING("Maybe"),
    NOT_RESPONDED("No response yet");
    
    private final String label;
    
    private ResponseEnum(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return this.label;
    }
}
