/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdo.learn.my.jaxrs.rsvp.ejb;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mtdo.learn.my.jaxrs.rsvp.entity.Event;
import mtdo.learn.my.jaxrs.rsvp.entity.Person;
import mtdo.learn.my.jaxrs.rsvp.entity.Response;
import mtdo.learn.my.jaxrs.rsvp.util.ResponseEnum;

/**
 *
 * @author thangdo
 */
@Singleton
@Startup
public class ConfigBean {

    @PersistenceContext
    private EntityManager em;
    private static final Logger logger = Logger.getLogger("mtdo.learn.my.jaxrs.rsvp.ejb.ConfigBean");
    
    @PostConstruct
    public void init(){
        // event owner;
        Person dad = new Person();
        dad.setFirstName("Tom");
        dad.setLastName("Do");
        em.persist(dad);
        
        // event
        Event event = new Event();
        event.setName("Madelyn's birthday");
        event.setLocation("Latitude");
        Calendar cal = new GregorianCalendar(2016, Calendar.JUNE, 21, 10, 0);
        event.setEventDate(cal.getTime());
        em.persist(event);
        
         // set the relationships
        dad.getOwnedEvents().add(event);
        dad.getEvents().add(event);
        event.setOwner(dad);
        event.getInvitees().add(dad);
        Response dadsResponse = new Response(event, dad, ResponseEnum.ATTENDING);
        em.persist(dadsResponse);
        event.getResponses().add(dadsResponse);

        // create some invitees
        Person duke = new Person();
        duke.setFirstName("Duke");
        duke.setLastName("OfJava");
        em.persist(duke);
        
        Person tux = new Person();
        tux.setFirstName("Tux");
        tux.setLastName("Penguin");
        em.persist(tux);

        // set the relationships
        event.getInvitees().add(duke);
        duke.getEvents().add(event);
        Response dukesResponse = new Response(event, duke);
        em.persist(dukesResponse);
        event.getResponses().add(dukesResponse);
        duke.getResponses().add(dukesResponse);

        event.getInvitees().add(tux);
        tux.getEvents().add(event);
        Response tuxsResponse = new Response(event, tux);
        em.persist(tuxsResponse);
        event.getResponses().add(tuxsResponse);
        tux.getResponses().add(tuxsResponse);
     }
}
