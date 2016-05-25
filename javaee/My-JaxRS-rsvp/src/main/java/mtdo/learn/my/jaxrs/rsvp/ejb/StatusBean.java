/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdo.learn.my.jaxrs.rsvp.ejb;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import mtdo.learn.my.jaxrs.rsvp.entity.Event;

/**
 *
 * @author thangdo
 */
@Stateless
@Named
@Path("/status")
public class StatusBean {
    private List<Event> allEvents;
    private static Logger logger = Logger.getLogger("mtdo.learn.my.jaxrs.rsvp.ejb.StatusBean");
    
    @PersistenceContext
    private EntityManager em;
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<Event> getAllEvents(){
        this.allEvents = (List<Event>) em.createNamedQuery("rsvp.entity.Event.getAllUpcomingEvents").getResultList();
        return this.allEvents;
    }
}
