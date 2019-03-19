package api.handler;

import org.json.JSONObject;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;


public class HttpResponseStatusException extends ClientErrorException{

    public HttpResponseStatusException(int status, String pageMessage, Response.Status.Family type){
        super(validate(Response.status(status).entity(pageMessage).build(),type), null);
    }

    public HttpResponseStatusException(int status, JSONObject pageMessage, Response.Status.Family type){
        super(validate(Response.status(status).entity(pageMessage.toString()).build(),type), null);
    }

    private static Response validate(Response response, Response.Status.Family expectedStatusFamily) {
        if (response.getStatusInfo().getFamily() != expectedStatusFamily) {
            throw new IllegalArgumentException(String.format("Status code of the supplied response [%d] is not from the required status code family \"%s\".", response.getStatus(), expectedStatusFamily));
        } else {
            return response;
        }
    }
}