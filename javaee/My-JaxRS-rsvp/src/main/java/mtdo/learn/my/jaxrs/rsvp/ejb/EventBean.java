/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdo.learn.my.jaxrs.rsvp.ejb;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import mtdo.learn.my.jaxrs.rsvp.entity.Event;

/**
 *
 * @author thangdo
 */
@Named
@Stateless
@Path("/{eventId}")
public class EventBean {
    @PersistenceContext
    private EntityManager em;

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void putEvent(@PathParam("eventId") Long eventId,
            String newName){
        Event event = em.find(Event.class, eventId);
        event.setName(newName);
        em.merge(event);
    }
}
