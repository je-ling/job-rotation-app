package project.job_rotation_app.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.job_rotation_app.dto.LoginReqBody;
import project.job_rotation_app.repository.StaffingManagerRepository;
import project.job_rotation_app.util.JwtUtil;

@Service
public class AuthBusinessServiceImpl {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  StaffingManagerRepository staffingManagerRepository;

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public boolean validateLogin(LoginReqBody userDetails) {
    if (userDetails == null || userDetails.getEmailAddress() == null
        || userDetails.getPassword() == null) {
      return false;
    }

    return staffingManagerRepository.findByEmailAddress(userDetails.getEmailAddress())
        .map(user -> encoder.matches(userDetails.getPassword(), user.getPassword()))
        .orElse(false);
  }

  public ResponseEntity<?> verifyLoginCredentials(LoginReqBody userDetails,
      HttpServletResponse response) {
    if (userDetails == null || userDetails.getEmailAddress() == null ||
        userDetails.getPassword() == null || userDetails.getEmailAddress().isEmpty()
        || userDetails.getPassword().isEmpty()) {
      return ResponseEntity.badRequest()
          .body(Map.of("Error", "A valid email and password must be provided"));
    }

    return staffingManagerRepository.findByEmailAddress(userDetails.getEmailAddress())
        .filter(user -> encoder.matches(userDetails.getPassword(), user.getPassword()))
        .map(user -> {
          String jwtToken = jwtUtil.generateToken(
              user.getEmailAddress(),
              String.valueOf(user.getUserId())
          );

          Cookie cookie = new Cookie("jwt", jwtToken);
          cookie.setHttpOnly(true);
          cookie.setPath("/");
          cookie.setMaxAge(3600); 
          response.addCookie(cookie);

          return ResponseEntity.ok().body(Map.of("token", jwtToken));
        })
        .orElse(ResponseEntity.status(401)
            .body(Map.of("Error", "Invalid email or password provided.")));
  }
}
