package project.job_rotation_app.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.job_rotation_app.dto.LoginReqBody;
import project.job_rotation_app.service.AuthBusinessServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

  @Autowired
  AuthBusinessServiceImpl authBusinessService;

  @PostMapping("/staffing-manager/login")
  public ResponseEntity<?> loginStaffingManager(
      @RequestBody LoginReqBody loginDetails) {
    return authBusinessService.verifyLoginCredentials(loginDetails);
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletResponse response) {
    Cookie cookie = new Cookie("jwt", null);
    cookie.setMaxAge(0);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    response.addCookie(cookie);

    return ResponseEntity.status(HttpStatus.OK).body("Successfully logged out!");
  }
}
