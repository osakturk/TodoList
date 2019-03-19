package api.controller.auth;

import api.controller.BaseController;
import api.controller.filter.Authorised;
import api.handler.HttpResponseStatusException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import model.UserInfo;
import org.apache.http.HttpStatus;
import org.glassfish.jersey.server.ContainerRequest;
import repository.UserRepository;

import javax.annotation.Priority;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.security.Key;

/**
 * Authorisation filter class.
 */
@Provider
@Authorised //Annotation binding
@Priority(Priorities.AUTHORIZATION) //Priority
public class AuthorisationFilterController extends BaseController implements ContainerRequestFilter {

    @Context
    HttpServletRequest request;

    /**
     * Authorisation filter for reports and definition end-points.
     *
     * @param containerRequestContext request context
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String username;
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String token;
        try {
            token               = authorizationHeader.substring("Bearer".length()).trim();
        }catch (Exception e){
            throw new HttpResponseStatusException(HttpStatus.SC_UNAUTHORIZED, "Sayfaya Giriş Yetkiniz Bulunmamaktadır", Response.Status.Family.CLIENT_ERROR);
        }


        UserRepository repository = new UserRepository();
        int            userId;
        UserInfo       userInfo;
        try {

            HttpSession session = request.getSession(true);

            byte[] decodedKey = this.getApiKeyBinary();
            Key    key        = new SecretKeySpec(decodedKey, 0, decodedKey.length, this.encryptionAlgorithm);

            Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);

            username = jws.getBody().getSubject();

            userId = repository.getUserIdByUserName(username);
            userInfo = repository.getUserInfo(userId);
            userInfo.setAuthenticated(true);
            userInfo.setUsername(username);


            System.out.println("### Valid token for user: " + username + "\t");
            System.out.println("### Client IP : "+getClientIp(request));
            session.setAttribute("userInfo", userInfo);
            session.setAttribute("CurrentLocale", userInfo.getCurrentLocale());

        } catch (Exception e) {
            System.err.println("### Invalid token ");
            e.printStackTrace();
            throw new HttpResponseStatusException(HttpStatus.SC_UNAUTHORIZED, "Sayfaya Giriş Yetkiniz Bulunmamaktadır", Response.Status.Family.CLIENT_ERROR);
        }
        ContainerRequest containerRequest = (ContainerRequest) containerRequestContext;
        containerRequest.bufferEntity();
        if (!userInfo.isAuthenticated()) {
            throw new HttpResponseStatusException(HttpStatus.SC_UNAUTHORIZED, "Sayfaya Giriş Yetkiniz Bulunmamaktadır", Response.Status.Family.CLIENT_ERROR);
        }
    }

    private static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-PROTO");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }

}
