package api.controller.user;

import api.controller.BaseController;
import api.controller.filter.Authorised;
import api.provider.UserDefinitions;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

@Authorised
@Path("/user")
public class UserController extends BaseController {

    @POST
    @Path("/")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON_UTF_8)
    public Response create(@Context HttpServletRequest request, MultivaluedMap<String,String> form){
        if (UserDefinitions.createPersonnelDefinition(request,form)){
            return Response.created(URI.create("http://localhost:8080/api/user")).entity(new JSONObject().put("result","true").toString()).build();
        }
        return Response.serverError().entity(new JSONObject().put("result","false").toString()).build();

    }
    @GET
    @Path("/")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON_UTF_8)
    public Response index(@Context HttpServletRequest request, MultivaluedMap<String,String> form){
        JSONObject data = UserDefinitions.getUsers();
        return Response.ok().entity(data.toString()).build();

    }
}
