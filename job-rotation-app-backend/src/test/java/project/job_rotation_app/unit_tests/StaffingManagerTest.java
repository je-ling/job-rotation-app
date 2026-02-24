package project.job_rotation_app.unit_tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import project.job_rotation_app.exception.BadRequestException;
import project.job_rotation_app.model.Departments;
import project.job_rotation_app.model.Duration;
import project.job_rotation_app.model.Grades;
import project.job_rotation_app.model.Roles;
import project.job_rotation_app.repository.RolesRepository;
import project.job_rotation_app.service.StaffingManagerBusinessServiceImpl;

@SpringBootTest
@DisplayName("Tests for Staffing Manager Controller")
public class StaffingManagerTest {

  @MockitoBean
  StaffingManagerBusinessServiceImpl staffingManagerBusinessService;

  @MockitoBean
  RolesRepository rolesRepository;

  @Test
  @DisplayName("When createRole is called with the all required fields are given, then it should create a new role")
  public void testCreateRole201() {
    Roles role = new Roles();
    role.setRoleId(123L);
    role.setRoleName("Java Developer");
    role.setDepartment(Departments.DEVELOPMENT);
    role.setDuration(Duration.TWELVE_MONTHS);
    role.setGradeRequired(Grades.GRADE_5);
    role.setJobDescription("JOB_DESCRIPTION");
    role.setStaffingManagerEmailAddress("test@example.com");

    when(staffingManagerBusinessService.createRole(role)).thenReturn(role);
    assertDoesNotThrow(
        () -> staffingManagerBusinessService.createRole(role));
  }

  @Test
  @DisplayName("When createRole is called with the required fields not provided, then it should throw a BadRequestException")
  public void testCreateRole400() {
    Roles role = new Roles();
    role.setRoleId(123L);
    role.setRoleName("Java Developer");
    role.setDepartment(Departments.DEVELOPMENT);
    role.setDuration(Duration.SIX_MONTHS);
    role.setGradeRequired(Grades.GRADE_5);
    role.setJobDescription("JOB_DESCRIPTION");
    role.setStaffingManagerEmailAddress("");

    when(staffingManagerBusinessService.createRole(role)).thenThrow(BadRequestException.class);
    assertThrows(BadRequestException.class, () -> staffingManagerBusinessService.createRole(role));
  }
}
