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
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LombokSpecTests {
    @DisplayName("Create a new user")
    @Test
    void createNewUserTest() {
        CreateRequestBodyModel authData = new CreateRequestBodyModel();
        authData.setName("Tracey");
        authData.setJob("leader");


        CreateResponseBodyModel response = step("Creating a user", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(responseSpecCode201)
                        .extract().as(CreateResponseBodyModel.class));

        step("Checking the Response", () -> {
            assertEquals("Tracey", response.getName());
            assertEquals("leader", response.getJob());
        });
    }

    @DisplayName("Checking user changes")
    @Test
    void checkUserChangeTest() {
        UpdatePatchRequestBodyModel authData = new UpdatePatchRequestBodyModel();
        authData.setName("Janet");
        authData.setEmail("charles.morris@reqres.in");


        UpdatePatchResponseBodyModel response = step("Checking user changes", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .patch("/users/10")
                        .then()
                        .spec(responseSpecCode200)
                        .extract().as(UpdatePatchResponseBodyModel.class));

        step("Checking the Response", () -> {
            assertEquals("Janet", response.getName());
            assertEquals("charles.morris@reqres.in", response.getEmail());
        });
    }

    @DisplayName("Successful user registration")
    @Test
    void successfulUserRegistrationTest() {
        RegisterRequestBodyModel authData = new RegisterRequestBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");

        RegisterResponseBodyModel response = step("User registration", () ->
                given(requestSpec)
                        .body(authData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responseSpecCode200)
                        .extract().as(RegisterResponseBodyModel.class));

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

        ErrorRegisterResponseBodyModel response = step("User registration", () ->
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
    void GettingListUsersTest() {
        ListUsersResponseBodyModel response = step("Getting a list of users", () ->
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
        });
    }
        @DisplayName("Deleting users")
        @Test
        void DeletingUsersTest() {
            step("Getting a list of users", () ->
            given(requestSpec)
                    .when()
                    .delete("/users/2")
                    .then()
                    .spec(responseSpecCode204));
        };
    }


