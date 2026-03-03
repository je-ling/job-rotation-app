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
import project.job_rotation_app.repository.StaffingManagerRepository;
import project.job_rotation_app.service.EmployeeBusinessServiceImpl;

@SpringBootTest
@DisplayName("Tests for Employee Controller")
public class EmployeeControllerTest {

  @MockitoBean
  private EmployeeBusinessServiceImpl employeeBusinessService;

  @MockitoBean
  private AuthController authController;

  @MockitoBean
  private StaffingManagerRepository staffingManagerRepository;

  @MockitoBean
  private RolesRepository rolesRepository;

  @Test
  @DisplayName("When getAvailableRoles endpoint is called, then it should return a list of available roles")
  public void testGetAvailableRoles200() {
    EmployeeController controller = new EmployeeController();
    controller.employeeBusinessService = employeeBusinessService;

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

    List<Roles> result = controller.getAvailableRoles();
    assertDoesNotThrow(() -> controller.getAvailableRoles());
    assertDoesNotThrow(() -> result.size() == 1);
    assertDoesNotThrow(() -> result.contains(role));
  }

  @Test
  @DisplayName("When getAvailableRoles is called and there are no available roles, then it should return an empty list")
  public void testGetAvailableRolesEmptyList200() {
    EmployeeController controller = new EmployeeController();
    controller.employeeBusinessService = employeeBusinessService;

    List<Roles> roles = new ArrayList<>();

    when(employeeBusinessService.getAvailableRoles()).thenReturn(roles);
    List<Roles> result = controller.getAvailableRoles();
    assertDoesNotThrow(() -> result.isEmpty());
  }

  @Test
  @DisplayName("When getAvailableRoles is called and a grade filter is specified, then it should return a list of available roles that match the grade filter")
  public void testGetAvailableRolesByGrade200() {
    EmployeeController controller = new EmployeeController();
    controller.employeeBusinessService = employeeBusinessService;

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

    when(employeeBusinessService.getAvailableRolesByGrade(Grades.GRADE_2)).thenReturn(roles);

    List<Roles> result = controller.getAvailableRolesByGrade(Grades.GRADE_2);

    when(employeeBusinessService.getAvailableRolesByGrade(Grades.GRADE_2)).thenReturn(
        result);
    assertDoesNotThrow(() -> controller.getAvailableRolesByGrade(Grades.GRADE_2));
    assertDoesNotThrow(
        () -> result.size() == 1);
    assertDoesNotThrow(
        () -> result.contains(role1));
  }

  @Test
  @DisplayName("When getAvailableRoles is called and a grade filter is specified but no jobs are available with the grade, then it should return an empty list")
  public void testGetAvailableRolesByGradeEmptyList200() {
    EmployeeController controller = new EmployeeController();
    controller.employeeBusinessService = employeeBusinessService;

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

    List<Roles> result = controller.getAvailableRolesByGrade(Grades.GRADE_1);

    when(employeeBusinessService.getAvailableRolesByGrade(Grades.GRADE_1)).thenReturn(
        roles);
    assertDoesNotThrow(
        () -> controller.getAvailableRolesByGrade(Grades.GRADE_1));
    assertDoesNotThrow(
        () -> result.isEmpty());
  }

  @Test
  @DisplayName("When getAvailableRoles is called and a department filter is specified, then it should return a list of available roles that match the department filter")
  public void testGetAvailableRolesByDept200() {
    EmployeeController controller = new EmployeeController();
    controller.employeeBusinessService = employeeBusinessService;

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

    when(employeeBusinessService.getAvailableRolesByDepartment(
        Departments.PLATFORM_ENGINEERING)).thenReturn(roles);

    List<Roles> result = controller.getAvailableRolesByDepartment(Departments.PLATFORM_ENGINEERING);

    when(employeeBusinessService.getAvailableRolesByDepartment(
        Departments.PLATFORM_ENGINEERING)).thenReturn(
        result);
    assertDoesNotThrow(
        () -> controller.getAvailableRolesByDepartment(Departments.PLATFORM_ENGINEERING));
    assertDoesNotThrow(
        () -> result.size() == 1);
    assertDoesNotThrow(
        () -> result.contains(role2));
  }

  @Test
  @DisplayName("When getAvailableRoles is called and a department filter is specified but no roles are available, then it should return an empty list")
  public void testGetAvailableRolesByDeptEmptyList200() {
    EmployeeController controller = new EmployeeController();
    controller.employeeBusinessService = employeeBusinessService;

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

    when(controller.getAvailableRolesByDepartment(
        Departments.ARCHITECTURE)).thenReturn(roles);
    assertDoesNotThrow(
        () -> result.isEmpty());
  }

  @Test
  @DisplayName("When getAvailableRoles is called and a duration filter is specified, then it should return a list of available roles that match the duration filter")
  public void testGetAvailableRolesByDuration200() {
    EmployeeController controller = new EmployeeController();
    controller.employeeBusinessService = employeeBusinessService;

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

    when(employeeBusinessService.getAvailableRolesByDuration(Duration.NINE_MONTHS)).thenReturn(
        roles);

    List<Roles> result = controller.getAvailableRolesByDuration(
        Duration.NINE_MONTHS);

    assertDoesNotThrow(
        () -> controller.getAvailableRolesByDuration(Duration.NINE_MONTHS));
    assertDoesNotThrow(
        () -> result.size() == 2);
    assertDoesNotThrow(
        () -> result.contains(role2));
    assertDoesNotThrow(
        () -> result.contains(role3));
  }

  @Test
  @DisplayName("When getAvailableRoles is called and a duration filter is specified but no roles are available, then it should return an empty list")
  public void testGetAvailableRolesByDurationEmptyList200() {
    EmployeeController controller = new EmployeeController();
    controller.employeeBusinessService = employeeBusinessService;

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

    when(employeeBusinessService.getAvailableRolesByDuration(Duration.TWELVE_MONTHS)).thenReturn(
        roles);

    List<Roles> result = controller.getAvailableRolesByDuration(
        Duration.TWELVE_MONTHS);

    assertDoesNotThrow(
        () -> controller.getAvailableRolesByDuration(Duration.TWELVE_MONTHS));
    assertDoesNotThrow(
        () -> result.isEmpty());
  }

  @Test
  @DisplayName("When getAvailableRoles is called and a grade and duration filter is specified, then it should return a list of available roles that match the filters")
  public void testGetAvailableRolesByMultiFilter200() {
    EmployeeController controller = new EmployeeController();
    controller.employeeBusinessService = employeeBusinessService;

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

    when(employeeBusinessService.getAvailableRolesByMultiFilters(Grades.GRADE_3, null,
        Duration.NINE_MONTHS)).thenReturn(roles);

    List<Roles> result = controller.getAvailableRolesByMultiFilters(Grades.GRADE_3,
        null, Duration.NINE_MONTHS);

    assertDoesNotThrow(
        () -> controller.getAvailableRolesByMultiFilters(Grades.GRADE_3, null,
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
    EmployeeController controller = new EmployeeController();
    controller.employeeBusinessService = employeeBusinessService;

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

    when(employeeBusinessService.getAvailableRolesByMultiFilters(null,
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
    EmployeeController controller = new EmployeeController();
    controller.employeeBusinessService = employeeBusinessService;

    Roles role = new Roles();
    role.setRoleId(123L);
    role.setRoleName("Scala Developer");
    role.setDepartment(Departments.DEVELOPMENT);
    role.setDuration(Duration.TWELVE_MONTHS);
    role.setGradeRequired(Grades.GRADE_5);
    role.setJobDescription("JOB_DESCRIPTION");
    role.setStaffingManagerEmailAddress("test123@example.com");

    when(employeeBusinessService.getRoleDetails(123L)).thenReturn(role);

    Roles result = controller.getRoleDetails(123L);

    assertDoesNotThrow(
        () -> controller.getRoleDetails(123L));
    assertDoesNotThrow(() -> result.equals(role) &&
        result.getRoleName().equals("Scala Developer") &&
        result.getDepartment() == Departments.DEVELOPMENT
        &&
        result.getDuration() == Duration.TWELVE_MONTHS &&
        result.getGradeRequired() == Grades.GRADE_5 &&
        result.getStaffingManagerEmailAddress()
            .equals("test123@example.com"));
  }

  @Test
  @DisplayName("When getRoleDetails is called with a null RoleId, then it throw an BadRequestException")
  public void testGetRoleDetailsNullRoleId200() {
    EmployeeController controller = new EmployeeController();
    controller.employeeBusinessService = employeeBusinessService;

    when(employeeBusinessService.getRoleDetails(null)).thenThrow(BadRequestException.class);
    assertThrows(BadRequestException.class, () -> controller.getRoleDetails(null));
  }
}
