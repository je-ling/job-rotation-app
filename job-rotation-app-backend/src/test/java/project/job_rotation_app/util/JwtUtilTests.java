package project.job_rotation_app.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.job_rotation_app.controller.AuthController;

class JwtUtilTests {

  @Autowired
  private final JwtUtil jwtUtil = new JwtUtil();

  @Test
  @DisplayName("generateToken should create a non-empty token")
  void generateTokenReturnsToken() {
    String token = jwtUtil.generateToken("test@example.com", "123L");
    assertNotNull(token);
    assertFalse(token.isBlank());
  }

  @Test
  @DisplayName("extractUsername should return subject/email from token")
  void extractUsernameReturnsEmail() {
    String email = "test@example.com";
    String token = jwtUtil.generateToken("test@example.com", "123L");

    String extracted = jwtUtil.extractUsername(token);

    assertEquals(email, extracted);
  }

  @Test
  @DisplayName("validateToken should return true for matching user")
  void validateTokenTrueForMatchingUser() {
    String token = jwtUtil.generateToken("test@example.com", "123L");

    boolean isValid = jwtUtil.isTokenValid(token);

    assertTrue(isValid);
  }

  @Test
  @DisplayName("validateToken should return false for non-matching user")
  void validateTokenFalseForWrongUser() {
    boolean isValid = jwtUtil.isTokenValid("test@example.com");

    assertFalse(isValid);
  }

  @Test
  @DisplayName("validateToken should reject malformed token")
  void validateTokenRejectsMalformedToken() {
    String malformedJwt = "not.a.real.jwt";
    assertThrows(Exception.class, () -> jwtUtil.extractUsername(malformedJwt));
  }


  @Test
  @DisplayName("When logout is called, then it should clear the JWT cookie and return HTTP 200")
  void logoutReturnsOkAndClearsJwtCookie() {
    AuthController authController = new AuthController();
    HttpServletResponse response = mock(HttpServletResponse.class);

    ResponseEntity<?> result = authController.logout(response);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals("Successfully logged out!", result.getBody());

    ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
    verify(response).addCookie(cookieCaptor.capture());

    Cookie cookie = cookieCaptor.getValue();
    assertEquals("jwt", cookie.getName());
    assertEquals(0, cookie.getMaxAge());
    assertTrue(cookie.isHttpOnly());
    assertEquals("/", cookie.getPath());
  }
}