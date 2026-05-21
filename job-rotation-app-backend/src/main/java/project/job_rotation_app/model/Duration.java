package project.job_rotation_app.model;

public enum Duration {
  THREE_MONTHS("Three Months"),
  SIX_MONTHS("Six Months"),
  NINE_MONTHS("Nine Months"),
  TWELVE_MONTHS("Twelve Months");

  private final String displayName;

  Duration(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}