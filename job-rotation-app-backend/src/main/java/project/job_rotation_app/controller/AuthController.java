package project.job_rotation_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.job_rotation_app.dto.LoginReqBody;
import project.job_rotation_app.service.AuthBusinessServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

  @Autowired
  AuthBusinessServiceImpl authBusinessService;

  @PostMapping("/staffing-manager/login")
  public ResponseEntity<?> loginStaffingManager(
      @RequestBody LoginReqBody loginDetails) {
    return authBusinessService.verifyLoginCredentials(loginDetails);
  }
}
