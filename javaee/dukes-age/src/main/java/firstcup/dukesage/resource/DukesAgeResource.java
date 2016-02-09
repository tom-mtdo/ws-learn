/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package firstcup.dukesage.resource;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 */
@Path("dukesAge")
public class DukesAgeResource {

    /**
     * Creates a new instance of DukesAgeResource
     */
    public DukesAgeResource() {
    }

    /**
     * Retrieves representation of an instance of DukesAgeResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText() {
        // Create a new Calendar for Duke's birthday
        Calendar dukesBirtday = new GregorianCalendar(1995, Calendar.MAY, 23);
        // Create a new Calendar for today
        Calendar now = GregorianCalendar.getInstance();

        // Subtract today's year from Duke's burth year, 1995
        int dukesAge = now.get(Calendar.YEAR) - dukesBirtday.get(Calendar.YEAR);
        dukesBirtday.add(Calendar.YEAR, dukesAge);

        // If today's date is before May 23, subtract a year from Duke's age
        if (now.before(dukesBirtday)) {
            dukesAge--;
        }
        // Return a String representation of Duke's age
        return "" + dukesAge;
    }
}
