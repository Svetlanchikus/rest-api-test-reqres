package in.reqres.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class SimpleReqresTests {

    @DisplayName("Checking the existence of the user number 10")
    @Test
    void checkExistenceUser() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("https://reqres.in/api/users/10")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(10));
    }

    @DisplayName("Checking user changes")
    @Test
    void checkUserChange() {
        String authData = "{ \"name\": \"Janet\", \"email\": \"charles.morris@reqres.in\" }";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .patch("https://reqres.in/api/users/10")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("Janet"))
                .body("email", is("charles.morris@reqres.in"));
    }

    @DisplayName("Create a new user")
    @Test
    void createNewUser() {
        String authData = "{ \"name\": \"Tracey\", \"job\": \"leader\" }";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Tracey"))
                .body("job", is("leader"));
    }

    @DisplayName("Checking the absence of the user 150")
    @Test
    void checkAbsenceUser() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("https://reqres.in/api/users/150")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @DisplayName("Checking the existence of a resource 5")
    @Test
    void checkExistenceResource() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .get("https://reqres.in/api/unknown/5")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(5));
    }
}
