package items;

import com.jayway.restassured.response.Header;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class ItemTest {
    @Test
    public void it_returns_todo_item_list() {
        given()
                .header(new Header("Authorization", "Bearer" + " " + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYSIsImF1dGhvcmlzYXRpb24iOiJGdWxsIG5hbWU6IFNpc3RlbSBZw7ZuZXRpY2lzaSwgTG9jYXRpb246IHRyIiwiaXNzIjoic2EiLCJpYXQiOjE1NTI5ODA0NzMsImV4cCI6MTU1NTY1NTI3M30.aSlTsQBznxtHY0PiJ8Gr_yJxfgXAa1nsaiu8m_bB9odZtHZRNd1fat9c3_WYmhWA8gOK9oYLodHm22EjKZFwbA"))
                .when()
                .get("http://localhost:8080/api/items/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void it_can_create_new_todo_item() {
        given()
                .header(new Header("Authorization", "Bearer" + " " + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYSIsImF1dGhvcmlzYXRpb24iOiJGdWxsIG5hbWU6IFNpc3RlbSBZw7ZuZXRpY2lzaSwgTG9jYXRpb246IHRyIiwiaXNzIjoic2EiLCJpYXQiOjE1NTI5ODA0NzMsImV4cCI6MTU1NTY1NTI3M30.aSlTsQBznxtHY0PiJ8Gr_yJxfgXAa1nsaiu8m_bB9odZtHZRNd1fat9c3_WYmhWA8gOK9oYLodHm22EjKZFwbA"))
                .when()
                .post("http://localhost:8080/api/items/1")
                .then()
                .statusCode(200);
    }
    @Test
    public void it_can_delete_todo_item() {
        given()
                .header(new Header("Authorization", "Bearer" + " " + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYSIsImF1dGhvcmlzYXRpb24iOiJGdWxsIG5hbWU6IFNpc3RlbSBZw7ZuZXRpY2lzaSwgTG9jYXRpb246IHRyIiwiaXNzIjoic2EiLCJpYXQiOjE1NTI5ODA0NzMsImV4cCI6MTU1NTY1NTI3M30.aSlTsQBznxtHY0PiJ8Gr_yJxfgXAa1nsaiu8m_bB9odZtHZRNd1fat9c3_WYmhWA8gOK9oYLodHm22EjKZFwbA"))
                .when()
                .delete("http://localhost:8080/api/items/7")
                .then()
                .statusCode(200);
    }

    @Test
    public void it_requires_authorization_todo_item_list() {
        given()
                .when()
                .get("http://localhost:8080/api/items/1")
                .then()
                .statusCode(401);
    }
}
