package in.reqres.tests;

import in.reqres.models.createpost.CreateRequestBodyModel;
import in.reqres.models.createpost.CreateResponseBodyModel;
import in.reqres.models.listusersget.ListUsersResponseBodyModel;
import in.reqres.models.register.ErrorRegisterResponseBodyModel;
import in.reqres.models.register.RegisterRequestBodyModel;
import in.reqres.models.register.RegisterResponseBodyModel;
import in.reqres.models.updatepatch.UpdatePatchRequestBodyModel;
import in.reqres.models.updatepatch.UpdatePatchResponseBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.Specs.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Testing the site API https://reqres.in/")
public class ReqresInTests extends TestBase {
    @DisplayName("Create a new user")
    @Test
    void createNewUserTest() {
        CreateRequestBodyModel authData = new CreateRequestBodyModel();
        authData.setName("Tracey");
        authData.setJob("leader");

        CreateResponseBodyModel response = step("Execute a request to create a user", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(responseSpecCode201)
                        .extract()
                        .as(CreateResponseBodyModel.class));

        step("Checking the Response", () -> {
            assertEquals("Tracey", response.getName());
            assertEquals("leader", response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }

    @DisplayName("Checking user changes")
    @Test
    void checkUserChangeTest() {
        UpdatePatchRequestBodyModel authData = new UpdatePatchRequestBodyModel();
        authData.setName("Janet");
        authData.setEmail("charles.morris@reqres.in");

        UpdatePatchResponseBodyModel response = step("Make a request to update the user", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .patch("/users/10")
                        .then()
                        .spec(responseSpecCode200)
                        .extract()
                        .as(UpdatePatchResponseBodyModel.class));

        step("Checking the Response", () -> {
            assertEquals("Janet", response.getName());
            assertEquals("charles.morris@reqres.in", response.getEmail());
            assertNotNull(response.getUpdatedAt());
        });
    }

    @DisplayName("Successful user registration")
    @Test
    void successfulUserRegistrationTest() {
        RegisterRequestBodyModel authData = new RegisterRequestBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");

        RegisterResponseBodyModel response = step("Execute a user registration request", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responseSpecCode200)
                        .extract()
                        .as(RegisterResponseBodyModel.class));

        step("Checking the Response", () -> {
            assertEquals(4, response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });
    }

    @DisplayName("Registering a user with an error")
    @Test
    void errorUserRegistrationTest() {
        RegisterRequestBodyModel authData = new RegisterRequestBodyModel();
        authData.setEmail("eve.holt@reqres.in");

        ErrorRegisterResponseBodyModel response = step("Make a request to register a user without specifying a password", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(errorResponseSpecCode400)
                        .extract()
                        .as(ErrorRegisterResponseBodyModel.class));

        step("Checking the Response", () -> {
            assertEquals("Missing password", response.getError());
        });
    }

    @DisplayName("Getting a list of users")
    @Test
    void gettingListUsersTest() {
        ListUsersResponseBodyModel response = step("Make a request to get a list of users", () ->
                given(requestSpec)
                        .queryParams("page", "2")
                        .when()
                        .get("/users")
                        .then()
                        .spec(responseSpecCode200)
                        .extract()
                        .as(ListUsersResponseBodyModel.class));

        step("Checking the Response", () -> {
            assertEquals(2, response.getPage());
            assertEquals(6, response.getPerPage());
            assertEquals(12, response.getTotal());
            assertEquals(2, response.getTotalPages());
            assertEquals(7, response.getData().get(0).getId());
            assertEquals("michael.lawson@reqres.in", response.getData().get(0).getEmail());
            assertEquals("Michael", response.getData().get(0).getFirstName());
            assertEquals("Lawson", response.getData().get(0).getLastName())
            ;assertEquals("https://reqres.in/img/faces/7-image.jpg", response.getData().get(0).getAvatar());
        });
    }
    @DisplayName("Deleting user")
    @Test
    void deletingUsersTest() {
        step("Execute a request to delete a user", () ->
                given(requestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(responseSpecCode204));
    }
}


