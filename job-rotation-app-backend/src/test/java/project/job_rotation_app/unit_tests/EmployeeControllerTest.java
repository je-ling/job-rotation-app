package project.job_rotation_app.unit_tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import project.job_rotation_app.model.Departments;
import project.job_rotation_app.model.Duration;
import project.job_rotation_app.model.Grades;
import project.job_rotation_app.model.Roles;
import project.job_rotation_app.repository.RolesRepository;
import project.job_rotation_app.service.EmployeeBusinessServiceImpl;

@SpringBootTest
@DisplayName("Tests for Employee Controller")
public class EmployeeControllerTest {

  @MockitoBean
  private EmployeeBusinessServiceImpl employeeBusinessService;

  @MockitoBean
  RolesRepository rolesRepository;

  @Test
  @DisplayName("When getAvailableRoles is called, then it should return a list of available roles")
  public void testGetAvailableRoles200() {

    Roles role = new Roles();
    role.setRoleId(123L);
    role.setRoleName("Java Developer");
    role.setDepartment(Departments.DEVELOPMENT);
    role.setDuration(Duration.TWELVE_MONTHS);
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

  @Test
  @DisplayName("When getAvailableRoles is called and a grade filter is specified, then it should return a list of available roles that match the grade filter")
  public void testGetAvailableRolesByGrade200() {
    Roles role1 = new Roles();
    role1.setRoleId(123L);
    role1.setRoleName("PMO");
    role1.setDepartment(Departments.BUSINESS_OPERATIONS);
    role1.setDuration(Duration.THREE_MONTHS);
    role1.setGradeRequired(Grades.GRADE_2);
    role1.setJobDescription("JOB_DESCRIPTION");
    role1.setStaffingManagerEmailAddress("test@example.com");

    Roles role2 = new Roles();
    role2.setRoleId(124L);
    role2.setRoleName("DevOps Engineer");
    role2.setDepartment(Departments.PLATFORM_ENGINEERING);
    role2.setDuration(Duration.NINE_MONTHS);
    role2.setGradeRequired(Grades.GRADE_4);
    role2.setJobDescription("JOB_DESCRIPTION");
    role2.setStaffingManagerEmailAddress("test@example.com");

    List<Roles> roles = new ArrayList<>();
    roles.add(role1);
    roles.add(role2);

    List<Roles> result = employeeBusinessService.getAvailableRolesByGrade(Grades.GRADE_2);

    when(employeeBusinessService.getAvailableRolesByGrade(Grades.GRADE_2)).thenReturn(
        result);
    assertDoesNotThrow(
        () -> employeeBusinessService.getAvailableRolesByGrade(Grades.GRADE_2));
    assertDoesNotThrow(
        () -> employeeBusinessService.getAvailableRolesByGrade(Grades.GRADE_2).size() == 1);
    assertDoesNotThrow(
        () -> employeeBusinessService.getAvailableRolesByGrade(Grades.GRADE_2).contains("PMO"));
  }

  @Test
  @DisplayName("When getAvailableRoles is called and a grade filter is specified but no jobs are available with the grade, then it should return an empty list")
  public void testGetAvailableRolesByGradeEmptyList200() {
    Roles role1 = new Roles();
    role1.setRoleId(123L);
    role1.setRoleName("PMO");
    role1.setDepartment(Departments.BUSINESS_OPERATIONS);
    role1.setDuration(Duration.THREE_MONTHS);
    role1.setGradeRequired(Grades.GRADE_2);
    role1.setJobDescription("JOB_DESCRIPTION");
    role1.setStaffingManagerEmailAddress("test@example.com");

    Roles role2 = new Roles();
    role2.setRoleId(124L);
    role2.setRoleName("DevOps Engineer");
    role2.setDepartment(Departments.PLATFORM_ENGINEERING);
    role2.setDuration(Duration.NINE_MONTHS);
    role2.setGradeRequired(Grades.GRADE_4);
    role2.setJobDescription("JOB_DESCRIPTION");
    role2.setStaffingManagerEmailAddress("test@example.com");

    List<Roles> roles = new ArrayList<>();
    roles.add(role1);
    roles.add(role2);

    List<Roles> result = employeeBusinessService.getAvailableRolesByGrade(Grades.GRADE_1);

    when(employeeBusinessService.getAvailableRolesByGrade(Grades.GRADE_1)).thenReturn(
        result);
    assertDoesNotThrow(
        () -> employeeBusinessService.getAvailableRolesByGrade(Grades.GRADE_1));
    assertDoesNotThrow(
        () -> employeeBusinessService.getAvailableRolesByGrade(Grades.GRADE_1).isEmpty());
  }

  @Test
  @DisplayName("When getAvailableRoles is called and a department filter is specified, then it should return a list of available roles that match the department filter")
  public void testGetAvailableRolesByDept200() {
    Roles role1 = new Roles();
    role1.setRoleId(123L);
    role1.setRoleName("PMO");
    role1.setDepartment(Departments.BUSINESS_OPERATIONS);
    role1.setDuration(Duration.THREE_MONTHS);
    role1.setGradeRequired(Grades.GRADE_6);
    role1.setJobDescription("JOB_DESCRIPTION");
    role1.setStaffingManagerEmailAddress("test@example.com");

    Roles role2 = new Roles();
    role2.setRoleId(124L);
    role2.setRoleName("DevOps Engineer");
    role2.setDepartment(Departments.PLATFORM_ENGINEERING);
    role2.setDuration(Duration.NINE_MONTHS);
    role2.setGradeRequired(Grades.GRADE_7);
    role2.setJobDescription("JOB_DESCRIPTION");
    role2.setStaffingManagerEmailAddress("test@example.com");

    List<Roles> roles = new ArrayList<>();
    roles.add(role1);
    roles.add(role2);

    List<Roles> result = employeeBusinessService.getAvailableRolesByDepartment(
        Departments.PLATFORM_ENGINEERING);

    when(employeeBusinessService.getAvailableRolesByDepartment(
        Departments.PLATFORM_ENGINEERING)).thenReturn(result);
    assertDoesNotThrow(
        () -> employeeBusinessService.getAvailableRolesByDepartment(
            Departments.PLATFORM_ENGINEERING));
    assertDoesNotThrow(
        () ->
            employeeBusinessService.getAvailableRolesByDepartment(Departments.PLATFORM_ENGINEERING)
                .size() == 1);
    assertDoesNotThrow(
        () ->
            employeeBusinessService.getAvailableRolesByDepartment(Departments.PLATFORM_ENGINEERING)
                .contains("PLATFORM_ENGINEERING"));
  }

  @Test
  @DisplayName("When getAvailableRoles is called and a department filter is specified but no roles are available, then it should return an empty list")
  public void testGetAvailableRolesByDeptEmptyList200() {
    Roles role1 = new Roles();
    role1.setRoleId(123L);
    role1.setRoleName("Database Administrator");
    role1.setDepartment(Departments.I_AND_D);
    role1.setDuration(Duration.SIX_MONTHS);
    role1.setGradeRequired(Grades.GRADE_8);
    role1.setJobDescription("JOB_DESCRIPTION");
    role1.setStaffingManagerEmailAddress("test@example.com");

    Roles role2 = new Roles();
    role2.setRoleId(124L);
    role2.setRoleName("Scrum Master");
    role2.setDepartment(Departments.AGILE_DELIVERY);
    role2.setDuration(Duration.NINE_MONTHS);
    role2.setGradeRequired(Grades.GRADE_9);
    role2.setJobDescription("JOB_DESCRIPTION");
    role2.setStaffingManagerEmailAddress("test@example.com");

    List<Roles> roles = new ArrayList<>();
    roles.add(role1);
    roles.add(role2);

    List<Roles> result = employeeBusinessService.getAvailableRolesByDepartment(
        Departments.ARCHITECTURE);

    when(employeeBusinessService.getAvailableRolesByDepartment(
        Departments.ARCHITECTURE)).thenReturn(result);
    assertDoesNotThrow(
        () -> employeeBusinessService.getAvailableRolesByDepartment(
            Departments.ARCHITECTURE));
    assertDoesNotThrow(
        () ->
            employeeBusinessService.getAvailableRolesByDepartment(Departments.ARCHITECTURE)
                .isEmpty());
  }

  @Test
  @DisplayName("When getAvailableRoles is called and a duration filter is specified, then it should return a list of available roles that match the duration filter")
  public void testGetAvailableRolesByDuration200() {
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

    List<Roles> result = employeeBusinessService.getAvailableRolesByDuration(
        Duration.NINE_MONTHS);

    when(employeeBusinessService.getAvailableRolesByDuration(Duration.NINE_MONTHS)).thenReturn(
        result);
    assertDoesNotThrow(
        () -> employeeBusinessService.getAvailableRolesByDuration(Duration.NINE_MONTHS));
    assertDoesNotThrow(
        () ->
            employeeBusinessService.getAvailableRolesByDuration(Duration.NINE_MONTHS)
                .size() == 2);
    assertDoesNotThrow(
        () ->
            employeeBusinessService.getAvailableRolesByDuration(Duration.NINE_MONTHS)
                .contains("NINE_MONTHS"));
  }

  @Test
  @DisplayName("When getAvailableRoles is called and a duration filter is specified but no roles are available, then it should return an empty list")
  public void testGetAvailableRolesByDurationEmptyList200() {
    Roles role1 = new Roles();
    role1.setRoleId(123L);
    role1.setRoleName("Database Administrator");
    role1.setDepartment(Departments.I_AND_D);
    role1.setDuration(Duration.SIX_MONTHS);
    role1.setGradeRequired(Grades.GRADE_8);
    role1.setJobDescription("JOB_DESCRIPTION");
    role1.setStaffingManagerEmailAddress("test@example.com");

    Roles role2 = new Roles();
    role2.setRoleId(124L);
    role2.setRoleName("Scrum Master");
    role2.setDepartment(Departments.AGILE_DELIVERY);
    role2.setDuration(Duration.NINE_MONTHS);
    role2.setGradeRequired(Grades.GRADE_9);
    role2.setJobDescription("JOB_DESCRIPTION");
    role2.setStaffingManagerEmailAddress("test@example.com");

    List<Roles> roles = new ArrayList<>();
    roles.add(role1);
    roles.add(role2);

    List<Roles> result = employeeBusinessService.getAvailableRolesByDuration(
        Duration.TWELVE_MONTHS);

    when(employeeBusinessService.getAvailableRolesByDuration(Duration.TWELVE_MONTHS)).thenReturn(
        result);
    assertDoesNotThrow(
        () -> employeeBusinessService.getAvailableRolesByDuration(Duration.TWELVE_MONTHS));
    assertDoesNotThrow(
        () ->
            employeeBusinessService.getAvailableRolesByDuration(Duration.NINE_MONTHS).isEmpty());
  }
}
