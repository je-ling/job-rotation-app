package project.job_rotation_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

public class ApiExceptionHandler {

  @ExceptionHandler(Unauthorized.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public void handleUnauthorizedException(Unauthorized ex) {
  }

  @ExceptionHandler(Forbidden.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public void handleForbiddenException(Forbidden ex) {
  }
}
