package api.controller.todo;

import api.controller.BaseController;
import api.controller.filter.Authorised;
import api.provider.ItemDefinitions;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

@Authorised
@Path("/items")
public class ItemsController extends BaseController {

    @GET
    @Path("{id}")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON_UTF_8)
    public Response show(@Context HttpServletRequest request, MultivaluedMap<String,String> form, @PathParam("id") int listId){
        JSONObject data = ItemDefinitions.getTodoItemByTodoListId(form,request,listId);
        return Response.ok().entity(data.toString()).build();
    }

    @POST
    @Path("/")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON_UTF_8)
    public Response create(@Context HttpServletRequest request, MultivaluedMap<String,String> form){
        if (ItemDefinitions.createTodoItem(request,form)){
            return Response.created(URI.create("http://localhost:8080/api/items")).entity(new JSONObject().put("result","true").toString()).build();
        }
        return Response.serverError().entity(new JSONObject().put("result","false").toString()).build();

    }

    @DELETE
    @Path("{id}")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON_UTF_8)
    public Response delete(@Context HttpServletRequest request, MultivaluedMap<String ,String > form, @PathParam("id") int id){
        if (ItemDefinitions.deleteTodoItem(id)){
            return Response.accepted().entity(new JSONObject().put("result","true").toString()).build();
        }
        return Response.serverError().entity(new JSONObject().put("result","false").toString()).build();
    }
}
