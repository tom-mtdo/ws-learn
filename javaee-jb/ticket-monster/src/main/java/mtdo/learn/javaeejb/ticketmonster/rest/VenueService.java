package mtdo.learn.javaeejb.ticketmonster.rest;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

import mtdo.learn.javaeejb.ticketmonster.model.Venue;

/**
 * <p>
 *     A JAX-RS endpoint for handling {@link Venue}s. Inherits the actual
 *     methods from {@link BaseEntityService}.
 * </p>
 *
 * @author Marius Bogoevici
 */
@Path("/venues")
/**
 * <p>
 *     This is a stateless service, we declare it as an EJB for transaction demarcation
 * </p>
 */
@Stateless
public class VenueService extends BaseEntityService<Venue> {

    public VenueService() {
        super(Venue.class);
    }

}