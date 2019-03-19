package api.controller.todo;

import api.controller.BaseController;
import api.controller.filter.Authorised;
import api.provider.ListDefinitions;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

@Authorised
@Path("/list")
public class ListController extends BaseController {
    @GET
    @Path("/")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON_UTF_8)
    public Response index(@Context HttpServletRequest request, MultivaluedMap<String,String> form){
        JSONObject data = ListDefinitions.getTodoList();
        return Response.ok().entity(data.toString()).build();
    }


    @GET
    @Path("{id}")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON_UTF_8)
    public Response show(@Context HttpServletRequest request, MultivaluedMap<String,String> form, @PathParam("id") int id){
        JSONObject data = ListDefinitions.getTodoListById(id);
        return Response.ok().entity(data.toString()).build();
    }

    @POST
    @Path("/")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON_UTF_8)
    public Response create(@Context HttpServletRequest request, MultivaluedMap<String,String> form){
        if (ListDefinitions.createTodoList(request,form)){
            return Response.created(URI.create("http://localhost:8080/api/list")).entity(new JSONObject().put("result","true").toString()).build();
        }
        return Response.serverError().entity(new JSONObject().put("result","false").toString()).build();

    }

    @PUT
    @Path("{id}")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON_UTF_8)
    public Response update(@Context HttpServletRequest request, MultivaluedMap<String,String> form, @PathParam("id") int id){
        if (ListDefinitions.updateTodoList(request,form,id)){
            return Response.accepted().entity(new JSONObject().put("result","true").toString()).build();
        }
        return Response.serverError().entity(new JSONObject().put("result","false").toString()).build();

    }

    @DELETE
    @Path("{id}")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON_UTF_8)
    public Response delete(@Context HttpServletRequest request, MultivaluedMap<String ,String > form, @PathParam("id") int id){
        if (ListDefinitions.deleteTodoList(id)){
            return Response.accepted().entity(new JSONObject().put("result","true").toString()).build();
        }
        return Response.serverError().entity(new JSONObject().put("result","false").toString()).build();
    }


}
