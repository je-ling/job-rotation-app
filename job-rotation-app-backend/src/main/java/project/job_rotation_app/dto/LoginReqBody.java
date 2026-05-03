package project.job_rotation_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqBody {

  private String emailAddress;
  private String password;

  public LoginReqBody(String emailAddress, String password) {
    this.emailAddress = emailAddress;
    this.password = password;
  }
}