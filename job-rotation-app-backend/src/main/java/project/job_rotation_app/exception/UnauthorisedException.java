package project.job_rotation_app.exception;

public class UnauthorisedException extends RuntimeException {
  public UnauthorisedException(String message) {
    super(message);
  }
}