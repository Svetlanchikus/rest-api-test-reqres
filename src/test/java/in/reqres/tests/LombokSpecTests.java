package in.reqres.tests;

import in.reqres.models.createPost.CreateRequestBodyModel;
import in.reqres.models.createPost.CreateResponseBodyModel;
import in.reqres.models.register.ErrorRegisterResponseBodyModel;
import in.reqres.models.register.RegisterRequestBodyModel;
import in.reqres.models.register.RegisterResponseBodyModel;
import in.reqres.models.updatePatch.UpdatePatchRequestBodyModel;
import in.reqres.models.updatePatch.UpdatePatchResponseBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.CreateSpec.*;
import static in.reqres.specs.RegisterSpec.*;
import static in.reqres.specs.UpdatePatchSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LombokSpecTests {
    @DisplayName("Create a new user")
    @Test
    void createNewUser() {
        CreateRequestBodyModel authData = new CreateRequestBodyModel();
        authData.setName("Tracey");
        authData.setJob("leader");


        CreateResponseBodyModel response = step("Creating a user", () ->
                given(createRequestSpec)
                        .body(authData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createResponseSpec)
                        .extract().as(CreateResponseBodyModel.class));

        step("Checking the Response", () -> {
            assertEquals("Tracey", response.getName());
            assertEquals("leader", response.getJob());
        });
    }

    @DisplayName("Checking user changes")
    @Test
    void checkUserChange() {
        UpdatePatchRequestBodyModel authData = new UpdatePatchRequestBodyModel();
        authData.setName("Janet");
        authData.setEmail("charles.morris@reqres.in");


        UpdatePatchResponseBodyModel response = step("Checking user changes", () ->
                given(UpdatePatchRequestSpec)
                        .body(authData)
                        .when()
                        .patch("/users/10")
                        .then()
                        .spec(UpdatePatchResponseSpec)
                        .extract().as(UpdatePatchResponseBodyModel.class));

        step("Checking the Response", () -> {
            assertEquals("Janet", response.getName());
            assertEquals("charles.morris@reqres.in", response.getEmail());
        });
    }

    @DisplayName("Successful user registration")
    @Test
    void successfulUserRegistration() {
        RegisterRequestBodyModel authData = new RegisterRequestBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("pistol");


        RegisterResponseBodyModel response = step("User registration", () ->
                given(registerRequestSpec)
                        .body(authData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(registerResponseSpec)
                        .extract().as(RegisterResponseBodyModel.class));

        step("Checking the Response", () -> {
            assertEquals(4, response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });
    }

    @DisplayName("Registering a user with an error")
    @Test
    void errorUserRegistration() {
        RegisterRequestBodyModel authData = new RegisterRequestBodyModel();
        authData.setEmail("eve.holt@reqres.in");

        ErrorRegisterResponseBodyModel response = step("User registration", () ->
                given(registerRequestSpec)
                        .body(authData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(errorRegisterResponseSpec)
                        .extract().as(ErrorRegisterResponseBodyModel.class));

        step("Checking the Response", () -> {
            assertEquals("Missing password", response.getError());
        });
    }
}
