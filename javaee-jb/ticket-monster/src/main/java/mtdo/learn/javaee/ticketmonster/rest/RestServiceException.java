package mtdo.learn.javaee.ticketmonster.rest;

import javax.ejb.ApplicationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@ApplicationException(inherited = true, rollback = true)
public class RestServiceException extends WebApplicationException {

    public RestServiceException() {
    }

    public RestServiceException(Response response) {
        super(response);
    }

    public RestServiceException(int status) {
        super(status);
    }

    public RestServiceException(Response.Status status) {
        super(status);
    }

    public RestServiceException(Throwable cause) {
        super(cause);
    }

    public RestServiceException(Throwable cause, Response response) {
        super(cause, response);
    }

    public RestServiceException(Throwable cause, int status) {
        super(cause, status);
    }

    public RestServiceException(Throwable cause, Response.Status status) {
        super(cause, status);
    }
}
