package project.job_rotation_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqBody {

  private String email;
  private String password;

  public LoginReqBody(String email, String password) {
    this.email = email;
    this.password = password;
  }
}