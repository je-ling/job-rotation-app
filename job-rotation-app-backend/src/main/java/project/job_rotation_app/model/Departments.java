package project.job_rotation_app.model;

public enum Departments {
  PLATFORM_ENGINEERING("Platform Engineering"),
  AGILE_DELIVERY("Agile Delivery"),
  ENGAGEMENT_MANAGEMENT("Engagement Management"),
  I_AND_D("Insights and Data"),
  ARCHITECTURE("Architecture"),
  DEVELOPMENT("Development"),
  DIGITAL_EXPERIENCE("Digital Experience"),
  BUSINESS_OPERATIONS("Business Operations");

  private final String displayName;

  Departments(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}