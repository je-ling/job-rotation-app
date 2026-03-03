package project.job_rotation_app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import project.job_rotation_app.dto.LoginReqBody;
import project.job_rotation_app.repository.RolesRepository;
import project.job_rotation_app.repository.StaffingManagerRepository;
import project.job_rotation_app.service.AuthBusinessServiceImpl;

@SpringBootTest
@DisplayName("Tests for Authentication Controller")
public class AuthControllerTest {

  @MockitoBean
  private AuthBusinessServiceImpl authBusinessService;

  @MockitoBean
  private RolesRepository rolesRepository;

  @MockitoBean
  private StaffingManagerRepository staffingManagerRepository;

  @Test
  @DisplayName("When loginStaffingManager is called with null loginDetails, it should return HTTP 400 and error message")
  void loginStaffingManagerWithValidCredentialsReturns200() {
    AuthController authController = new AuthController();
    authController.authBusinessService = authBusinessService;

    LoginReqBody loginDetails = new LoginReqBody("test@example.com", "password123");

    when(authBusinessService.verifyLoginCredentials(loginDetails)).thenReturn(
        ResponseEntity.ok().build());

    ResponseEntity<?> response = authController.loginStaffingManager(loginDetails);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  @DisplayName("When loginStaffingManager is called with null loginDetails, then it should return HTTP 400 and error message")
  void loginStaffingManagerReturnsBadRequestForNullLoginDetails() {
    AuthController authController = new AuthController();
    authController.authBusinessService = authBusinessService;

    when(authBusinessService.verifyLoginCredentials(null)).thenReturn(
        ResponseEntity.badRequest().build());

    ResponseEntity<?> response = authController.loginStaffingManager(null);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  @DisplayName("When loginStaffingManager is called with an empty email provided, then it should return HTTP 400 and error message")
  void loginStaffingManagerReturnsBadRequestForEmptyEmail() {
    AuthController authController = new AuthController();
    authController.authBusinessService = authBusinessService;

    LoginReqBody loginDetails = new LoginReqBody("", "password123");

    when(authBusinessService.verifyLoginCredentials(loginDetails)).thenReturn(
        ResponseEntity.badRequest().build());

    ResponseEntity<?> response = authController.loginStaffingManager(loginDetails);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  @DisplayName("When loginStaffingManager is called with empty password, then it should return HTTP 400 and error message")
  void loginStaffingManagerReturnsBadRequestForEmptyPassword() {
    AuthController authController = new AuthController();
    authController.authBusinessService = authBusinessService;

    LoginReqBody loginDetails = new LoginReqBody("test@example.com", "");

    when(authBusinessService.verifyLoginCredentials(loginDetails)).thenReturn(
        ResponseEntity.badRequest().build());

    ResponseEntity<?> response = authController.loginStaffingManager(loginDetails);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
}
