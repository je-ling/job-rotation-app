package project.job_rotation_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.job_rotation_app.dto.LoginReqBody;
import project.job_rotation_app.repository.StaffingManagerRepository;

@Service
public class AuthBusinessServiceImpl {

  @Autowired
  StaffingManagerRepository staffingManagerRepository;

  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public boolean validateLogin(LoginReqBody userDetails) {
    if (userDetails == null || userDetails.getEmailAddress() == null
        || userDetails.getPassword() == null) {
      return false;
    }

//    return staffingManagerRepository.findByEmailAddress(userDetails.getEmailAddress())
//        .map(user -> encoder.matches(userDetails.getPassword(), user.getPassword()))
//        .orElse(false);
    return true;
  }

  public ResponseEntity<?> verifyLoginCredentials(LoginReqBody userDetails) {
    if (userDetails == null || userDetails.getEmailAddress() == null ||
        userDetails.getPassword() == null || userDetails.getEmailAddress().isEmpty()
        || userDetails.getPassword().isEmpty()) {
      return ResponseEntity.badRequest().body("Email and password must be provided");
    }
    if (validateLogin(userDetails)) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.status(401).body("Invalid email or password");
      // unauthorised status returned
    }
  }
}
