package api.controller.auth;

import api.controller.BaseController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.UserInfo;
import org.json.JSONObject;
import repository.LoginRepository;
import repository.UserRepository;

import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

/**
 * Manages user operations such as login and password change etc.
 */
@Path("/user")
@Transactional
public class LoginController extends BaseController {

    /**
     * Manages user login process. If success returns response OK (200) status code with header AUTHORIZATION
     * with value "Bearer + 'token'" otherwise returns UNAUTHORIZED status code with empty response.
     *
     * @param username Username
     * @param password Password
     * @return Login attempt response.
     */
    @POST
    @Path("/login")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON_UTF_8)
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        try {

            if (authenticateUser(username, password)) {
                String         token      = issueToken(username);
                UserRepository repository = new UserRepository();
                UserInfo       userInfo   = repository.getUserInfo(repository.getUserIdByUserName(username));

                JSONObject object = new JSONObject();

                object.put("token", token);
                object.put("name", userInfo.getFirstname() + " " + userInfo.getLastname());
                object.put("locale", userInfo.getCurrentLocale().toString());
                System.out.println("### Valid Authentication for username : " + username);
                return Response.ok().entity(object.toString()).header(AUTHORIZATION, "Bearer " + token).build();
            } else {
                System.out.println("### Authenticate user failed for user: " + username);
                JSONObject object = new JSONObject();
                object.put("status", 422);
                return Response.ok().entity(object.toString()).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    /**
     * Checks user credentials for user over db.
     *
     * @param username username
     * @param password password
     * @return boolean result
     */
    private boolean authenticateUser(String username, String password) {
        int             userId;
        LoginRepository repository = new LoginRepository();

        userId = repository.authenticateUser(username, password);

        return userId >= 0;
    }

    /**
     * Generates token for authenticated user.
     *
     * @param username JWT token subject name.
     * @return JWT token for user session.
     */
    private String issueToken(String username) {
        byte[]         decodedKey     = this.getApiKeyBinary();
        Key            key            = new SecretKeySpec(decodedKey, 0, decodedKey.length, this.encryptionAlgorithm);
        UserRepository userRepository = new UserRepository();

        Date     now      = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Date end = calendar.getTime();

        UserInfo userInfo = userRepository.getUserInfo(userRepository.getUserIdByUserName(username));

        return Jwts.builder()
                .setSubject(username)
                .claim("authorisation", "Full name: " + userInfo.getFirstname() + ", Location: " + userInfo.getCurrentLocale())
                .setIssuer(username)
                .setIssuedAt(now)
                .setExpiration(end)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}
