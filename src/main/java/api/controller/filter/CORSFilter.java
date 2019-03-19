package api.controller.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * CORS filter controller.
 */
@Provider
public class CORSFilter implements ContainerResponseFilter {

    /**
     * Adds cors response headers for end-point requests.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    public void filter(ContainerRequestContext request,
                       ContainerResponseContext response) {
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization");
        response.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Server", "To-do Project Web Server");
        response.getHeaders().add("X-XSS-Protection", "1; mode=block");
        response.getHeaders().add("X-Frame-Options", "DENY");
    }
}