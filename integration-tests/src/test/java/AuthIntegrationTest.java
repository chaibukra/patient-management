import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest {

    private static final String LOGIN_ENDPOINT = "/auth/login";

    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:80";
    }

    @Test
    public void shouldReturnOKWithValidToken(){
        String loginPayload = """
                 {
                    "email":"testuser@test.com",
                    "password": "password123"
                 }
                """;

        Response response = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .statusCode(200)
                .body("token",notNullValue())
                .extract()
                .response();

        System.out.println("Generated Token: "+ response.jsonPath().getString("token"));
    }

    @Test
    public void shouldReturnUnauthorizeOnInvalidLogin(){
        String loginPayload = """
                 {
                    "email":"invalid_user@test.com",
                    "password": "wrongpassword"
                 }
                """;

        given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .statusCode(401);
    }

    @Test
    public void shouldReturnBadRequestWhenPasswordMissing(){
        String loginPayload = """
                 {
                    "email":"invalid_user@test.com",
                 }
                """;

        given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .statusCode(400);
    }


}
