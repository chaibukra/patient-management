import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PatientIntegrationTest {
    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:80";
    }

    private static final String LOGIN_ENDPOINT = "/auth/login";
    private static final String PATIENT_ENDPOINT = "/api/patients";

    @Test
    public void shouldReturnPatientWithValidToken(){
        String loginPayload = """
                 {
                    "email":"testuser@test.com",
                    "password": "password123"
                 }
                """;

        String token = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .get("token");

        given()
                .header("Authorization", "Bearer "  + token)
                .when()
                .get(PATIENT_ENDPOINT)
                .then()
                .statusCode(200)
                .body("patients", notNullValue());


    }


}

