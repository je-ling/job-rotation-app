package project.job_rotation_app.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleInformationDto {

  private String roleId;
  private String roleName;
  private String gradeRequired;
  private String staffingManagerEmailAddress;
  private String department;
  private String duration;
  private String jobDescription;
}
