package project.job_rotation_app.model;

public enum Grades {
  GRADE_1("Grade 1"),
  GRADE_2("Grade 2"),
  GRADE_3("Grade 3"),
  GRADE_4("Grade 4"),
  GRADE_5("Grade 5"),
  GRADE_6("Grade 6"),
  GRADE_7("Grade 7"),
  GRADE_8("Grade 8"),
  GRADE_9("Grade 9"),
  GRADE_10("Grade 10");

  private final String displayName;

  Grades(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}