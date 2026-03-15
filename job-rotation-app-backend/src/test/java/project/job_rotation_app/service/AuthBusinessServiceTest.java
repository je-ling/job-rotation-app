package project.job_rotation_app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.job_rotation_app.dto.LoginReqBody;
import project.job_rotation_app.model.StaffingManagerUser;
import project.job_rotation_app.repository.StaffingManagerRepository;

public class AuthBusinessServiceTest {

  @Mock
  private StaffingManagerRepository staffingManagerRepository;

  @InjectMocks
  private AuthBusinessServiceImpl authBusinessService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

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
    staffingManagerUser.setEmailAddress(userDetails.getEmailAddress());
    staffingManagerUser.setPassword(new BCryptPasswordEncoder().encode(userDetails.getPassword()));
    when(staffingManagerRepository.findByEmail(userDetails.getEmailAddress())).thenReturn(
        Optional.of(staffingManagerUser));

    ResponseEntity<?> response = authBusinessService.verifyLoginCredentials(userDetails);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  @DisplayName("When verifyLoginCredentials is called with invalid credentials provided, it should return HTTP 401 unauthorised status")
  void verifyLoginCredentialsReturnsUnauthorizedForInvalidCredentials() {
    LoginReqBody userDetails = new LoginReqBody("test@example.com", "wrongPassword");
    StaffingManagerUser staffingManagerUser = new StaffingManagerUser();
    staffingManagerUser.setEmailAddress(userDetails.getEmailAddress());
    staffingManagerUser.setPassword(new BCryptPasswordEncoder().encode("password123"));
    when(staffingManagerRepository.findByEmail(userDetails.getEmailAddress())).thenReturn(
        Optional.of(staffingManagerUser));

    ResponseEntity<?> response = authBusinessService.verifyLoginCredentials(userDetails);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertEquals("Invalid email or password", response.getBody());
  }
}
