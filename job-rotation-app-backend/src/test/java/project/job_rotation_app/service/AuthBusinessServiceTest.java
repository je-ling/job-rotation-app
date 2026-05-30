package project.job_rotation_app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.job_rotation_app.dto.LoginReqBody;
import project.job_rotation_app.model.StaffingManagerUser;
import project.job_rotation_app.repository.StaffingManagerRepository;
import project.job_rotation_app.util.JwtUtil;

public class AuthBusinessServiceTest {

  @Mock
  private StaffingManagerRepository staffingManagerRepository;

  @InjectMocks
  private AuthBusinessServiceImpl authBusinessService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Mock
  private JwtUtil jwtUtil;

  @Test
  @DisplayName("When validateLogin is called with invalid null userDetails, it should return false")
  void validateLoginReturnsFalseForNullUserDetails() {
    boolean result = authBusinessService.validateLogin(null);
    assertFalse(result);
  }

  @Test
  @DisplayName("When verifyLoginCredentials is called with valid credentials, it should return HTTP 200 OK status")
  void verifyLoginCredentialsReturnsOkForValidCredentials() {
    LoginReqBody userDetails = new LoginReqBody("test@example.com", "password123");
    StaffingManagerUser staffingManagerUser = new StaffingManagerUser();
    staffingManagerUser.setUserId(123L);
    staffingManagerUser.setEmailAddress(userDetails.getEmailAddress());
    staffingManagerUser.setPassword("$2y$12$9qsXLGuN.c6Hk3F4G9vSluft9mo7zucEmLrePJbOG3wMjxO4GTmVy");
    when(staffingManagerRepository.findByEmailAddress(userDetails.getEmailAddress()))
        .thenReturn(Optional.of(staffingManagerUser));
    when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("example-token");

    ResponseEntity<?> response = authBusinessService.verifyLoginCredentials(userDetails);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  @DisplayName("When verifyLoginCredentials is called with invalid credentials provided, it should return HTTP 401 unauthorised status")
  void verifyLoginCredentialsReturnsUnauthorizedForInvalidCredentials() {
    LoginReqBody userDetails = new LoginReqBody("test@example.com", "wrongPassword");
    StaffingManagerUser staffingManagerUser = new StaffingManagerUser();
    staffingManagerUser.setEmailAddress(userDetails.getEmailAddress());
    when(staffingManagerRepository.findByEmailAddress(userDetails.getEmailAddress())).thenReturn(
        Optional.of(staffingManagerUser));

    ResponseEntity<?> response = authBusinessService.verifyLoginCredentials(userDetails);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertEquals(Map.of("Error", "Invalid email or password provided."), response.getBody());
  }
}
