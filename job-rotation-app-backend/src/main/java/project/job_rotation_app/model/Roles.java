package project.job_rotation_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "roles")
public class Roles {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long roleId;

  private String roleName;

  @Enumerated(EnumType.STRING)
  private Grades gradeRequired;

  @Enumerated(EnumType.STRING)
  private Departments department;

  private String staffingManagerEmailAddress;

  @Enumerated(EnumType.STRING)
  private Duration duration;

  private String jobDescription;

  private String location;

  private String startDate;

  private String client;

  private String securityClearanceRequired;

  @Version
  private Integer version;
}
