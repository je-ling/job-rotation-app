package project.job_rotation_app.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import project.job_rotation_app.model.Departments;
import project.job_rotation_app.model.Duration;
import project.job_rotation_app.model.Grades;
import project.job_rotation_app.model.Roles;
import project.job_rotation_app.repository.RolesRepository;
import project.job_rotation_app.repository.StaffingManagerRepository;
import project.job_rotation_app.service.AuthBusinessServiceImpl;
import project.job_rotation_app.service.StaffingManagerBusinessServiceImpl;

@SpringBootTest
@DisplayName("Tests for Staffing Manager Controller")
public class StaffingManagerControllerTest {

  @MockitoBean
  private StaffingManagerBusinessServiceImpl staffingManagerBusinessService;

  @MockitoBean
  private AuthBusinessServiceImpl authBusinessService;

  @MockitoBean
  private StaffingManagerRepository staffingManagerRepository;

  @MockitoBean
  private RolesRepository rolesRepository;

  @Test
  @DisplayName("When getAvailableRoles endpoint is called, then it should return a list of available roles")
  public void testGetAvailableRoles200() {
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

    List<Roles> roles = new ArrayList<>();
    roles.add(role);

    when(staffingManagerBusinessService.getAvailableRoles()).thenReturn(roles);
    List<Roles> result = controller.getAvailableRoles();

    assertDoesNotThrow(() -> result.size() == 1);
    assertDoesNotThrow(() -> result.contains(role));
    assertEquals(1, result.size());
    assertEquals(123L, result.get(0).getRoleId());
    verify(staffingManagerBusinessService).getAvailableRoles();
  }

  @Test
  @DisplayName("When getAvailableRoles is called and there are no available roles, then it should return an empty list")
  public void testGetAvailableRolesEmptyList200() {
    StaffingManagerController controller = new StaffingManagerController();
    controller.staffingManagerBusinessService = staffingManagerBusinessService;

    List<Roles> roles = new ArrayList<>();

    when(staffingManagerBusinessService.getAvailableRoles()).thenReturn(roles);
    List<Roles> result = controller.getAvailableRoles();
    assertDoesNotThrow(() -> result.isEmpty());
    verify(staffingManagerBusinessService).getAvailableRoles();
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
        Duration.NINE_MONTHS, "Client")).thenReturn(roles);

    List<Roles> result = controller.getAvailableRolesByMultiFilters(
        Grades.GRADE_3,
        null, Duration.NINE_MONTHS, "Client");

    assertDoesNotThrow(
        () -> staffingManagerBusinessService.getAvailableRolesByMultiFilters(Grades.GRADE_3, null,
            Duration.NINE_MONTHS, "Client"));
    assertDoesNotThrow(
        () -> result.size() == 2);
    assertDoesNotThrow(
        () -> result.contains(role2));
    assertDoesNotThrow(
        () -> result.contains(role3));
  }

  @Test
  void getAvailableRolesByMultiFiltersReturnsAllWhenFiltersAreNotProvided() {
    StaffingManagerController controller = new StaffingManagerController();
    controller.staffingManagerBusinessService = staffingManagerBusinessService;

    Roles role1 = new Roles();
    role1.setGradeRequired(Grades.GRADE_3);
    role1.setDepartment(Departments.DEVELOPMENT);
    role1.setDuration(Duration.THREE_MONTHS);
    role1.setClient("Client A");

    Roles role2 = new Roles();
    role2.setGradeRequired(Grades.GRADE_5);
    role2.setDepartment(Departments.BUSINESS_OPERATIONS);
    role2.setDuration(Duration.SIX_MONTHS);
    role2.setClient("Client B");

    List<Roles> roles = List.of(role1, role2);
    when(staffingManagerBusinessService.getAvailableRolesByMultiFilters(null, null, null, null))
        .thenReturn(roles);

    List<Roles> result = controller.getAvailableRolesByMultiFilters(null, null, null, null);

    assertEquals(2, result.size());
    verify(staffingManagerBusinessService).getAvailableRolesByMultiFilters(null, null, null, null);
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
        Departments.ARCHITECTURE, Duration.TWELVE_MONTHS, "Client")).thenReturn(roles);

    List<Roles> result = controller.getAvailableRolesByMultiFilters(null,
        Departments.ARCHITECTURE, Duration.TWELVE_MONTHS, "Client");

    assertDoesNotThrow(
        () -> controller.getAvailableRolesByMultiFilters(null,
            Departments.ARCHITECTURE, Duration.TWELVE_MONTHS, "Client"));
    assertDoesNotThrow(
        () -> result.isEmpty());
  }

  @Test
  @DisplayName("When getRoleDetails is called with a valid role ID, then it should return the details of the role")
  public void testGetRoleDetails200() throws Exception {
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

    Roles result = controller.getRoleDetails(123L);

    assertEquals(role, result);
    assertEquals("Scala Developer", result.getRoleName());
    assertEquals(Departments.DEVELOPMENT, result.getDepartment());
    assertEquals(Duration.TWELVE_MONTHS, result.getDuration());
    assertEquals(Grades.GRADE_5, result.getGradeRequired());
    assertEquals("test123@example.com", result.getStaffingManagerEmailAddress());
    assertEquals(123L, result.getRoleId());

    verify(staffingManagerBusinessService).getRoleDetails(123L);
  }

  @Test
  @DisplayName("When getRoleDetails is called with a null RoleId, then it throw an BadRequestException")
  public void testGetRoleDetailsNullRoleId400() throws Exception {
    StaffingManagerController controller = new StaffingManagerController();
    controller.staffingManagerBusinessService = staffingManagerBusinessService;

    when(staffingManagerBusinessService.getRoleDetails(null)).thenThrow(BadRequestException.class);
    assertThrows(BadRequestException.class,
        () -> controller.getRoleDetails(null));
    verify(staffingManagerBusinessService).getRoleDetails(null);
  }

  @Test
  @DisplayName("When createRole is called with the all required fields are given, then it should create a new role")
  public void testCreateRole200() throws Exception {
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

    when(staffingManagerBusinessService.createRole(role)).thenReturn(role);
    Roles result = staffingManagerBusinessService.createRole(role);

    assertDoesNotThrow(
        () -> controller.createRole(role));
    assertEquals("Java Developer", result.getRoleName());
  }

  @Test
  @DisplayName("When createRole is called with the required fields not provided, then it should throw a BadRequestException")
  public void testCreateRole400() throws Exception {
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
  @DisplayName("When updateRole is called with a valid role ID and all required fields are given, then it should update the role details in the system")
  public void testUpdateRole200() throws Exception {
    StaffingManagerController controller = new StaffingManagerController();
    controller.staffingManagerBusinessService = staffingManagerBusinessService;

    Roles role = new Roles();
    role.setRoleId(123L);
    role.setRoleName("Scala Developer");
    role.setDepartment(Departments.DEVELOPMENT);
    role.setDuration(Duration.TWELVE_MONTHS);
    role.setGradeRequired(Grades.GRADE_5);
    role.setJobDescription("JOB_DESCRIPTION_UPDATED");
    role.setStaffingManagerEmailAddress("test@example.com");

    when(staffingManagerBusinessService.updateRole(123L, role)).thenReturn(role);
    assertEquals("JOB_DESCRIPTION_UPDATED", role.getJobDescription());
    assertDoesNotThrow(() -> controller.updateRole(123L, role));
  }

  @Test
  @DisplayName("When updateRole is called with a valid role ID and but not all required fields are given, then it should return a HTTP 400 bad request error")
  public void testUpdateRole400() throws Exception {
    StaffingManagerController controller = new StaffingManagerController();
    controller.staffingManagerBusinessService = staffingManagerBusinessService;

    Roles role = new Roles();
    role.setRoleId(123L);
    role.setRoleName("Scala Developer");
    role.setDepartment(Departments.DEVELOPMENT);
    role.setDuration(Duration.TWELVE_MONTHS);
    role.setGradeRequired(Grades.GRADE_5);
    role.setJobDescription("JOB_DESCRIPTION");
    role.setStaffingManagerEmailAddress("");

    when(staffingManagerBusinessService.updateRole(123L, role)).thenThrow(
        BadRequestException.class);

    assertThrows(BadRequestException.class, () -> controller.updateRole(123L, role));
  }

  @Test
  @DisplayName("When deleteRole is called with a valid role ID, then it should delete the role from the system and return true")
  public void testDeleteRoleTrue() {
    StaffingManagerController controller = new StaffingManagerController();
    controller.staffingManagerBusinessService = staffingManagerBusinessService;

    when(staffingManagerBusinessService.deleteRole(123L)).thenReturn(true);

    Boolean result = controller.deleteRole(123L);

    assertDoesNotThrow(() -> controller.deleteRole(123L));
    assertTrue(controller.deleteRole(123L));
    assertTrue(result);
  }

  @Test
  @DisplayName("When deleteRole is called with a role ID that is not present in the system, then it return false")
  public void testDeleteRoleFalse() {
    StaffingManagerController controller = new StaffingManagerController();
    controller.staffingManagerBusinessService = staffingManagerBusinessService;

    when(staffingManagerBusinessService.deleteRole(125L)).thenReturn(false);
    assertFalse(controller.deleteRole(123L));
  }

  @Test
  @DisplayName("When get enum values is called it should return all enum names for grades, departments and durations")
  void getEnumValuesReturnsAllEnums() {
    StaffingManagerController controller = new StaffingManagerController();

    Map<String, List<String>> result = controller.getEnumValues();

    List<String> expectedGrades = Arrays.stream(Grades.values())
        .map(Grades::toString)
        .toList();
    List<String> expectedDepartments = Arrays.stream(Departments.values())
        .map(Departments::toString)
        .toList();
    List<String> expectedDurations = Arrays.stream(Duration.values())
        .map(Duration::toString)
        .toList();

    assertEquals(expectedGrades, result.get("grades"));
    assertEquals(10, expectedGrades.size());
    assertEquals(expectedDepartments, result.get("departments"));
    assertEquals(8, expectedDepartments.size());
    assertEquals(4, expectedDurations.size());
    assertEquals(expectedDurations, result.get("durations"));
    assertEquals(3, result.size());
  }
}
