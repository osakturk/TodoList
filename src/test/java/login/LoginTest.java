package login;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class LoginTest {
    protected static final String user          = "ABCDEFGHIJKLMNOPRSTUVWYZ";
    protected static final String pass          = "asdfgdhfgfdsfdghjkgsa";

    @Test
    public void can_login() {


        given()
                .param("username", "sa")
                .param("sa", "1234")
                .when()
                .post("http://localhost:8080/api/login")
                .then()
                .statusCode(200)
                .body("locale", equalTo("tr"));

    }

    @Test
    public void test_failed_login() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[]        bytesOfUser   = user.getBytes("UTF-8");
        byte[]        bytesOfPass   = pass.getBytes("UTF-8");
        MessageDigest mdpass        = MessageDigest.getInstance("MD5");
        MessageDigest mduser        = MessageDigest.getInstance("MD5");
        byte[]        thedigest     = mduser.digest(bytesOfUser);
        byte[]        thedigestpass = mdpass.digest(bytesOfPass);

        given()
                .param("username", thedigest)
                .param("password", thedigestpass)
                .when()
                .post("http://localhost:8080/api/login")
                .then()
                .statusCode(422)
                .body("status", equalTo(422));

    }
}
