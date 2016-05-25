/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdo.learn.my.jaxrs.rsvp.web;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import mtdo.learn.my.jaxrs.rsvp.entity.Event;

/**
 *
 * @author thangdo
 */
@Named
@SessionScoped
public class StatusManager implements Serializable{
    private static final long serialVersionUID = 1;
    // for Rest client
    private Client client;
    private final String baseUri="http://localhost:8080/My-JaxRS-rsvp/webapi";
    private WebTarget target;
    
    // for webpage
    private List<Event> events;

    public StatusManager() {
        this.client = ClientBuilder.newClient();
    }
    
    public String updateEventName(Event event, String newName){
        client.target(baseUri)
                .path(event.getId().toString())
                .request(MediaType.APPLICATION_XML)
                .post(Entity.xml(newName));
        return "index";
    }
    
    public List<Event> getEvents() {
        List<Event> returnedEvents = null;
        returnedEvents = client.target(baseUri)
                .path("/status/all")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<Event>>(){} );
        
        return returnedEvents;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
        
}
