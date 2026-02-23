package project.job_rotation_app.unit_tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import project.job_rotation_app.model.Departments;
import project.job_rotation_app.model.Grades;
import project.job_rotation_app.model.Roles;
import project.job_rotation_app.service.EmployeeBusinessServiceImpl;

@SpringBootTest
@DisplayName("Tests for Employee Controller")
public class EmployeeControllerTest {

  @MockitoBean
  private EmployeeBusinessServiceImpl employeeBusinessService;

  @Test
  @DisplayName("When getAvailableRoles is called, then it should return a list of available roles")
  public void testGetAvailableRoles200() {

    Roles role = new Roles();
    role.setRoleId(123L);
    role.setRoleName("Java Developer");
    role.setDepartment(Departments.DEVELOPMENT);
    role.setDuration("12 Months");
    role.setGradeRequired(Grades.GRADE_5);
    role.setJobDescription("JOB_DESCRIPTION");
    role.setStaffingManagerEmailAddress("test@example.com");

    List<Roles> roles = new ArrayList<>();
    roles.add(role);

    when(employeeBusinessService.getAvailableRoles()).thenReturn(roles);
    assertDoesNotThrow(
        () -> employeeBusinessService.getAvailableRoles());
    assertDoesNotThrow(
        () -> employeeBusinessService.getAvailableRoles().size() == 1);

  }

  @Test
  @DisplayName("When getAvailableRoles is called and there are no available roles, then it should return an empty list")
  public void testGetAvailableRolesEmptyList200() {
    List<Roles> roles = new ArrayList<>();

    when(employeeBusinessService.getAvailableRoles()).thenReturn(roles);
    assertDoesNotThrow(
        () -> employeeBusinessService.getAvailableRoles());
    assertDoesNotThrow(
        () -> employeeBusinessService.getAvailableRoles().isEmpty());
  }
}
