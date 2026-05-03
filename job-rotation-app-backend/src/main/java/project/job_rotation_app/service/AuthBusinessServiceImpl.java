package project.job_rotation_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.job_rotation_app.dto.LoginReqBody;
import project.job_rotation_app.model.StaffingManagerUser;
import project.job_rotation_app.repository.StaffingManagerRepository;

@Service
public class AuthBusinessServiceImpl {

  @Autowired
  StaffingManagerRepository staffingManagerRepository;

  public boolean validateLogin(LoginReqBody userDetails) {
    if (userDetails == null || userDetails.getEmailAddress() == null || userDetails.getPassword() == null) {
      return false;
    }

    return staffingManagerRepository.findByEmailAddress(userDetails.getEmailAddress())
        .map(user -> userDetails.getPassword().equals(user.getPassword()))
        .orElse(false);
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
