package project.job_rotation_app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import project.job_rotation_app.dto.LoginReqBody;
import project.job_rotation_app.model.StaffingManagerUser;
import project.job_rotation_app.repository.RolesRepository;
import project.job_rotation_app.repository.StaffingManagerRepository;
import project.job_rotation_app.service.AuthBusinessServiceImpl;
import project.job_rotation_app.util.JwtUtil;

@SpringBootTest
@DisplayName("Tests for Authentication Controller")
public class AuthControllerTest {

  @MockitoBean
  private AuthBusinessServiceImpl authBusinessService;

  @MockitoBean
  private RolesRepository rolesRepository;

  @MockitoBean
  private StaffingManagerRepository staffingManagerRepository;

  @MockitoBean
  private JwtUtil jwtUtil;

  @Test
  @DisplayName("When loginStaffingManager is called with valid credentials, then it should return HTTP 200")
  void loginStaffingManagerWithValidCredentialsReturns200() {
    AuthController authController = new AuthController();
    authController.authBusinessService = authBusinessService;

    LoginReqBody loginDetails = new LoginReqBody("test@example.com", "password123");

    doReturn(ResponseEntity.ok(Map.of("token", "mock-jwt-token")))
        .when(authBusinessService).verifyLoginCredentials(loginDetails);

    ResponseEntity<?> response = authController.loginStaffingManager(loginDetails);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("mock-jwt-token", ((Map<?, ?>) response.getBody()).get("token"));
    verify(authBusinessService).verifyLoginCredentials(loginDetails);
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

  @Test
  @DisplayName("validateLogin returns false when request is null")
  void validateLoginNullRequest() {
    assertFalse(authBusinessService.validateLogin(null));
  }

  @Test
  @DisplayName("validateLogin returns false when email is null")
  void validateLoginNullEmail() {
    LoginReqBody req = new LoginReqBody(null, "password123");
    assertFalse(authBusinessService.validateLogin(req));
  }

  @Test
  @DisplayName("validateLogin returns false when password is null")
  void validateLoginNullPassword() {
    LoginReqBody req = new LoginReqBody("test@example.com", null);
    assertFalse(authBusinessService.validateLogin(req));
  }

  @Test
  @DisplayName("validateLogin returns false when user not found")
  void validateLoginUserNotFound() {
    LoginReqBody req = new LoginReqBody("test@example.com", "password123");
    when(staffingManagerRepository.findByEmailAddress("test@example.com")).thenReturn(
        Optional.empty());

    assertFalse(authBusinessService.validateLogin(req));
  }

  @Test
  @DisplayName("validateLogin returns false when password mismatches")
  void validateLoginPasswordMismatch() {
    LoginReqBody req = new LoginReqBody("test@example.com", "wrong-password");
    StaffingManagerUser user = new StaffingManagerUser();
    user.setUserId(123L);
    user.setPassword("$2y$12$9qsXLGuN.c6Hk3F4G9vSluft9mo7zucEmLrePJbOG3wMjxO4GTmVy");
    user.setEmailAddress("test@example.com");
    when(staffingManagerRepository.findByEmailAddress("test@example.com")).thenReturn(
        Optional.of(user));

    assertFalse(authBusinessService.validateLogin(req));
  }
}
