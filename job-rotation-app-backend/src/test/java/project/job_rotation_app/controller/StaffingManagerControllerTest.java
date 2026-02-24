package project.job_rotation_app.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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
public class StaffingManagerControllerTest {

  @MockitoBean
  StaffingManagerBusinessServiceImpl staffingManagerBusinessService;

  @MockitoBean
  RolesRepository rolesRepository;

  @Test
  @DisplayName("When createRole is called with the all required fields are given, then it should create a new role")
  public void testCreateRole201() {
    StaffingManagerController controller = new StaffingManagerController();
    controller.staffingManagerBusinessService = staffingManagerBusinessService;

    Roles role = new Roles();
    role.setRoleId(123L);
    role.setRoleName("Java Developer");
    role.setDepartment(Departments.DEVELOPMENT);
    role.setDuration(Duration.TWELVE_MONTHS);
    role.setGradeRequired(Grades.GRADE_5);
    role.setJobDescription("JOB_DESCRIPTION");
    role.setStaffingManagerEmailAddress("test@example.com");

    Roles result = staffingManagerBusinessService.createRole(role);
    when(staffingManagerBusinessService.createRole(role)).thenReturn(result);
    assertDoesNotThrow(
        () -> controller.createRole(role));
  }

  @Test
  @DisplayName("When createRole is called with the required fields not provided, then it should throw a BadRequestException")
  public void testCreateRole400() {
    StaffingManagerController controller = new StaffingManagerController();
    controller.staffingManagerBusinessService = staffingManagerBusinessService;

    Roles role = new Roles();
    role.setRoleId(123L);
    role.setRoleName("Java Developer");
    role.setDepartment(Departments.DEVELOPMENT);
    role.setDuration(Duration.SIX_MONTHS);
    role.setGradeRequired(Grades.GRADE_5);
    role.setJobDescription("JOB_DESCRIPTION");
    role.setStaffingManagerEmailAddress("");

    when(staffingManagerBusinessService.createRole(role)).thenThrow(BadRequestException.class);
    assertThrows(BadRequestException.class, () -> controller.createRole(role));
  }

  @Test
  @DisplayName("When getAvailableRoles is called and a grade and duration filter is specified, then it should return a list of available roles that match the filters")
  public void testGetAvailableRolesByMultiFilter200() {
    StaffingManagerController controller = new StaffingManagerController();
    controller.staffingManagerBusinessService = staffingManagerBusinessService;

    Roles role1 = new Roles();
    role1.setRoleId(123L);
    role1.setRoleName("Engagement Manager");
    role1.setDepartment(Departments.ENGAGEMENT_MANAGEMENT);
    role1.setDuration(Duration.THREE_MONTHS);
    role1.setGradeRequired(Grades.GRADE_10);
    role1.setJobDescription("JOB_DESCRIPTION");
    role1.setStaffingManagerEmailAddress("test@example.com");

    Roles role2 = new Roles();
    role2.setRoleId(124L);
    role2.setRoleName("UX Designer");
    role2.setDepartment(Departments.DIGITAL_EXPERIENCE);
    role2.setDuration(Duration.NINE_MONTHS);
    role2.setGradeRequired(Grades.GRADE_3);
    role2.setJobDescription("JOB_DESCRIPTION");
    role2.setStaffingManagerEmailAddress("test@example.com");

    Roles role3 = new Roles();
    role2.setRoleId(125L);
    role2.setRoleName("UX Researcher");
    role2.setDepartment(Departments.DIGITAL_EXPERIENCE);
    role2.setDuration(Duration.NINE_MONTHS);
    role2.setGradeRequired(Grades.GRADE_3);
    role2.setJobDescription("JOB_DESCRIPTION");
    role2.setStaffingManagerEmailAddress("test@example.com");

    List<Roles> roles = new ArrayList<>();
    roles.add(role1);
    roles.add(role2);
    roles.add(role3);

    when(staffingManagerBusinessService.getAvailableRolesByMultiFilters(Grades.GRADE_3, null,
        Duration.NINE_MONTHS)).thenReturn(roles);

    List<Roles> result = controller.getAvailableRolesByMultiFilters(
        Grades.GRADE_3,
        null, Duration.NINE_MONTHS);

    assertDoesNotThrow(
        () -> staffingManagerBusinessService.getAvailableRolesByMultiFilters(Grades.GRADE_3, null,
            Duration.NINE_MONTHS));
    assertDoesNotThrow(
        () -> result.size() == 2);
    assertDoesNotThrow(
        () -> result.contains(role2));
    assertDoesNotThrow(
        () -> result.contains(role3));
  }

  @Test
  @DisplayName("When getAvailableRoles is called and a department and duration filter is specified but no roles are available, then it should return an empty list")
  public void testGetAvailableRolesByMultiFilterEmptyList200() {
    StaffingManagerController controller = new StaffingManagerController();
    controller.staffingManagerBusinessService = staffingManagerBusinessService;

    Roles role1 = new Roles();
    role1.setRoleId(123L);
    role1.setRoleName("Engagement Manager");
    role1.setDepartment(Departments.ENGAGEMENT_MANAGEMENT);
    role1.setDuration(Duration.THREE_MONTHS);
    role1.setGradeRequired(Grades.GRADE_10);
    role1.setJobDescription("JOB_DESCRIPTION");
    role1.setStaffingManagerEmailAddress("test@example.com");

    Roles role2 = new Roles();
    role2.setRoleId(124L);
    role2.setRoleName("UX Designer");
    role2.setDepartment(Departments.DIGITAL_EXPERIENCE);
    role2.setDuration(Duration.NINE_MONTHS);
    role2.setGradeRequired(Grades.GRADE_3);
    role2.setJobDescription("JOB_DESCRIPTION");
    role2.setStaffingManagerEmailAddress("test@example.com");

    Roles role3 = new Roles();
    role2.setRoleId(125L);
    role2.setRoleName("UX Researcher");
    role2.setDepartment(Departments.DIGITAL_EXPERIENCE);
    role2.setDuration(Duration.NINE_MONTHS);
    role2.setGradeRequired(Grades.GRADE_3);
    role2.setJobDescription("JOB_DESCRIPTION");
    role2.setStaffingManagerEmailAddress("test@example.com");

    List<Roles> roles = new ArrayList<>();
    roles.add(role1);
    roles.add(role2);
    roles.add(role3);

    when(staffingManagerBusinessService.getAvailableRolesByMultiFilters(null,
        Departments.ARCHITECTURE, Duration.TWELVE_MONTHS)).thenReturn(roles);

    List<Roles> result = controller.getAvailableRolesByMultiFilters(null,
        Departments.ARCHITECTURE, Duration.TWELVE_MONTHS);

    assertDoesNotThrow(
        () -> controller.getAvailableRolesByMultiFilters(null,
            Departments.ARCHITECTURE, Duration.TWELVE_MONTHS));
    assertDoesNotThrow(
        () -> result.isEmpty());
  }

  @Test
  @DisplayName("When getRoleDetails is called with a valid role ID, then it should return the details of the role")
  public void testGetRoleDetails200() {
    StaffingManagerController controller = new StaffingManagerController();
    controller.staffingManagerBusinessService = staffingManagerBusinessService;

    Roles role = new Roles();
    role.setRoleId(123L);
    role.setRoleName("Scala Developer");
    role.setDepartment(Departments.DEVELOPMENT);
    role.setDuration(Duration.TWELVE_MONTHS);
    role.setGradeRequired(Grades.GRADE_5);
    role.setJobDescription("JOB_DESCRIPTION");
    role.setStaffingManagerEmailAddress("test123@example.com");

    when(staffingManagerBusinessService.getRoleDetails(123L)).thenReturn(role);

    Roles result = staffingManagerBusinessService.getRoleDetails(123L);

    assertDoesNotThrow(
        () -> controller.getRoleDetails(123L));
    assertDoesNotThrow(
        () -> result.equals(role) && result.getRoleName().equals("Scala Developer") &&
            result.getDepartment() == Departments.DEVELOPMENT &&
            result.getDuration() == Duration.TWELVE_MONTHS &&
            result.getGradeRequired() == Grades.GRADE_5 &&
            result.getStaffingManagerEmailAddress().equals("test123@example.com"));
  }

  @Test
  @DisplayName("When getRoleDetails is called with a null RoleId, then it throw an BadRequestException")
  public void testGetRoleDetailsNullRoleId200() {
    StaffingManagerController controller = new StaffingManagerController();
    controller.staffingManagerBusinessService = staffingManagerBusinessService;

    when(staffingManagerBusinessService.getRoleDetails(null)).thenThrow(BadRequestException.class);
    assertThrows(BadRequestException.class,
        () -> controller.getRoleDetails(null));
  }
}
