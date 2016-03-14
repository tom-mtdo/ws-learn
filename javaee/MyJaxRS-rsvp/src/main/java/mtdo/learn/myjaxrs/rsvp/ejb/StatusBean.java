package mtdo.learn.myjaxrs.rsvp.ejb;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Named
@Path("/status")
public class StatusBean {
    
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @Path("{eventId}/")
    public String getEvent(@PathParam("eventId") Long eventId){
        return "Hello, test status for event with id = " + eventId;
    }
    
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @Path("all")
    public String getAllEvent(){
        return "Hello, test status for all events";
    }
    
}
