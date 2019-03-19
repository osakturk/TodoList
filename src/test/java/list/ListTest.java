package list;

import com.jayway.restassured.response.Header;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class ListTest {
    @Test
    public void it_returns_todo_list() {
        given()
                .header(new Header("Authorization", "Bearer" + " " + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYSIsImF1dGhvcmlzYXRpb24iOiJGdWxsIG5hbWU6IFNpc3RlbSBZw7ZuZXRpY2lzaSwgTG9jYXRpb246IHRyIiwiaXNzIjoic2EiLCJpYXQiOjE1NTI5ODA0NzMsImV4cCI6MTU1NTY1NTI3M30.aSlTsQBznxtHY0PiJ8Gr_yJxfgXAa1nsaiu8m_bB9odZtHZRNd1fat9c3_WYmhWA8gOK9oYLodHm22EjKZFwbA"))
                .when()
                .get("http://localhost:8080/api/list")
                .then()
                .statusCode(200);
    }

    @Test
    public void can_create_todo_list() {
        given()
                .header(new Header("Authorization", "Bearer" + " " + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYSIsImF1dGhvcmlzYXRpb24iOiJGdWxsIG5hbWU6IFNpc3RlbSBZw7ZuZXRpY2lzaSwgTG9jYXRpb246IHRyIiwiaXNzIjoic2EiLCJpYXQiOjE1NTI5ODA0NzMsImV4cCI6MTU1NTY1NTI3M30.aSlTsQBznxtHY0PiJ8Gr_yJxfgXAa1nsaiu8m_bB9odZtHZRNd1fat9c3_WYmhWA8gOK9oYLodHm22EjKZFwbA"))
                .param("name","Todo List From API")
                .param("user_id",1)
                .when()
                .post("http://localhost:8080/api/list")
                .then()
                .statusCode(200);
    }
    @Test
    public void can_delete_todo_list() {
        given()
                .header(new Header("Authorization", "Bearer" + " " + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYSIsImF1dGhvcmlzYXRpb24iOiJGdWxsIG5hbWU6IFNpc3RlbSBZw7ZuZXRpY2lzaSwgTG9jYXRpb246IHRyIiwiaXNzIjoic2EiLCJpYXQiOjE1NTI5ODA0NzMsImV4cCI6MTU1NTY1NTI3M30.aSlTsQBznxtHY0PiJ8Gr_yJxfgXAa1nsaiu8m_bB9odZtHZRNd1fat9c3_WYmhWA8gOK9oYLodHm22EjKZFwbA"))
                .param("name","Todo List From API")
                .param("user_id",3)
                .when()
                .post("http://localhost:8080/api/list")
                .then()
                .statusCode(200);
    }

    @Test
    public void it_requires_authorization_todo_list() {
        given()
                .when()
                .get("http://localhost:8080/api/list")
                .then()
                .statusCode(401);
    }
}
