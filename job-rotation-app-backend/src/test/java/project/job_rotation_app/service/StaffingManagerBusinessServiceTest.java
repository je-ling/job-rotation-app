package project.job_rotation_app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import project.job_rotation_app.exception.BadRequestException;
import project.job_rotation_app.model.Departments;
import project.job_rotation_app.model.Duration;
import project.job_rotation_app.model.Grades;
import project.job_rotation_app.model.Roles;
import project.job_rotation_app.repository.RolesRepository;

public class StaffingManagerBusinessServiceTest {

  @Mock
  private RolesRepository rolesRepository;

  @InjectMocks
  private StaffingManagerBusinessServiceImpl staffingManagerBusinessService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("When createRole gets called then it should save the role when all fields are valid and provided")
  void createRoleSavesRoleWhenFieldsAreValid200() {
    Roles role = new Roles();
    role.setRoleName("Developer");
    role.setDepartment(Departments.DEVELOPMENT);
    role.setJobDescription("Software Engineer");
    role.setDuration(Duration.THREE_MONTHS);
    role.setGradeRequired(Grades.GRADE_3);
    role.setStaffingManagerEmailAddress("test@example.com");
    role.setLocation("London");
    role.setSecurityClearanceRequired("Yes");
    role.setStartDate("01-01-2026");

    when(rolesRepository.save(role)).thenReturn(role);

    Roles result = staffingManagerBusinessService.createRole(role);

    assertEquals(role, result);
    verify(rolesRepository, times(1)).save(role);
  }

  @Test
  @DisplayName("When createRole gets called then it should throw BadRequestException when required fields are empty")
  void createRoleThrowsBadRequestExceptionWhenFieldsAreEmpty400() {
    Roles role = new Roles();
    role.setRoleName("");
    role.setDepartment(null);
    role.setJobDescription("");
    role.setDuration(null);
    role.setGradeRequired(null);
    role.setStaffingManagerEmailAddress("");

    assertThrows(BadRequestException.class, () -> staffingManagerBusinessService.createRole(role));
    verify(rolesRepository, never()).save(any());
  }

  @Test
  @DisplayName("When updateRole gets called then it should update the role when all fields are valid and provided")
  void updateRoleWhenAllFieldsAreProvided200() {
    Roles existingRole = new Roles();
    existingRole.setRoleId(123L);
    existingRole.setRoleName("Developer");
    existingRole.setDepartment(Departments.DEVELOPMENT);
    existingRole.setJobDescription("Software Engineer");
    existingRole.setDuration(Duration.THREE_MONTHS);
    existingRole.setGradeRequired(Grades.GRADE_3);
    existingRole.setStaffingManagerEmailAddress("test@example.com");
    existingRole.setLocation("London");
    existingRole.setSecurityClearanceRequired("Yes");
    existingRole.setStartDate("01-01-2026");
    existingRole.setVersion(0);

    Roles updatedRole = new Roles();
    updatedRole.setRoleId(123L);
    updatedRole.setRoleName("Senior Developer");
    updatedRole.setDepartment(Departments.DEVELOPMENT);
    updatedRole.setJobDescription("Lead Software Engineer");
    updatedRole.setDuration(Duration.SIX_MONTHS);
    updatedRole.setGradeRequired(Grades.GRADE_5);
    updatedRole.setStaffingManagerEmailAddress("test@example.com");
    updatedRole.setLocation("London");
    updatedRole.setSecurityClearanceRequired("Yes");
    updatedRole.setStartDate("01-01-2026");

    when(rolesRepository.findByRoleId(123L)).thenReturn(existingRole);
    when(rolesRepository.save(existingRole)).thenReturn(existingRole);

    Roles result = staffingManagerBusinessService.updateRole(123L, updatedRole);

    assertEquals(updatedRole.getRoleName(), result.getRoleName());
    assertEquals(updatedRole.getDepartment(), result.getDepartment());
    assertEquals(updatedRole.getJobDescription(), result.getJobDescription());
    assertEquals(updatedRole.getDuration(), result.getDuration());
    assertEquals(updatedRole.getGradeRequired(), result.getGradeRequired());
    assertEquals(updatedRole.getStaffingManagerEmailAddress(),
        result.getStaffingManagerEmailAddress());
    verify(rolesRepository, times(1)).findByRoleId(123L);
    verify(rolesRepository, times(1)).save(existingRole);
  }

  @Test
  @DisplayName("When updateRole gets called but not all fields are provided then it should throw BadRequestException")
  void updateRoleWhenAllFieldsAreNotProvided400() {
    Roles role = new Roles();
    role.setRoleId(123L);
    role.setRoleName("");
    role.setDepartment(null);
    role.setJobDescription("");
    role.setDuration(null);
    role.setGradeRequired(null);
    role.setStaffingManagerEmailAddress("");

    assertThrows(BadRequestException.class,
        () -> staffingManagerBusinessService.updateRole(123L, role));
    verify(rolesRepository, never()).save(any());
  }

  @Test
  @DisplayName("When updateRole gets called then it should throw BadRequestException if role with provided ID does not exist")
  void updateRoleThrowsExceptionWhenRoleIdNotFound404() {
    Roles roleUpdate = new Roles();
    roleUpdate.setRoleId(123L);
    roleUpdate.setRoleName(".NET Developer");
    roleUpdate.setDepartment(Departments.DEVELOPMENT);
    roleUpdate.setJobDescription("Software Engineer");
    roleUpdate.setDuration(Duration.THREE_MONTHS);
    roleUpdate.setGradeRequired(Grades.GRADE_3);
    roleUpdate.setStaffingManagerEmailAddress("test@example.com");

    when(rolesRepository.findByRoleId(123L)).thenReturn(null);

    assertThrows(BadRequestException.class,
        () -> staffingManagerBusinessService.updateRole(123L, roleUpdate));
    verify(rolesRepository, never()).save(any());
  }

  @Test
  @DisplayName("When getAvailableRoles is called then it should return all available roles")
  void getAvailableRolesReturnsAllAvaRoles200() {
    List<Roles> roles = new ArrayList<>();
    Roles role = new Roles();
    roles.add(role);

    when(rolesRepository.findAll()).thenReturn(roles);

    List<Roles> result = staffingManagerBusinessService.getAvailableRoles();

    assertEquals(roles, result);
    verify(rolesRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("When getAvailableRolesByGrade is called then it should return roles matching the specified grade")
  void getAvailableRolesByGrade200() {
    Grades grade = Grades.GRADE_5;
    List<Roles> roles = new ArrayList<>();
    Roles role = new Roles();
    role.setGradeRequired(grade);
    roles.add(role);

    when(rolesRepository.findByGradeRequired(grade)).thenReturn(roles);

    List<Roles> result = staffingManagerBusinessService.getAvailableRolesByGrade(grade);

    assertEquals(roles, result);
    verify(rolesRepository, times(1)).findByGradeRequired(grade);
  }

  @Test
  @DisplayName("When getAvailableRolesByDepartment gets called then it should return available roles matching the specified department")
  void getAvailableRolesByDepartmentReturnsFilteredRoles() {
    Departments department = Departments.DEVELOPMENT;
    List<Roles> roles = new ArrayList<>();
    Roles role = new Roles();
    role.setDepartment(department);
    roles.add(role);

    when(rolesRepository.findByDepartment(department)).thenReturn(roles);

    List<Roles> result = staffingManagerBusinessService.getAvailableRolesByDepartment(department);

    assertEquals(roles, result);
    verify(rolesRepository, times(1)).findByDepartment(department);
  }

  @Test
  @DisplayName("When getAvailableRolesByDuration is called then it should return roles matching the specified duration")
  void getAvailableRolesByDurationReturnsFilteredRoles() {
    Duration duration = Duration.THREE_MONTHS;
    List<Roles> roles = new ArrayList<>();
    Roles role = new Roles();
    role.setDuration(duration);
    roles.add(role);

    when(rolesRepository.findByDuration(duration)).thenReturn(roles);

    List<Roles> result = staffingManagerBusinessService.getAvailableRolesByDuration(duration);

    assertEquals(roles, result);
    verify(rolesRepository, times(1)).findByDuration(duration);
  }

  @Test
  @DisplayName("When getAvailableRolesByMultiFilters is called then it should return roles matching all specified filters")
  void getAvailableRolesByMultiFiltersReturnsFilteredRoles() {
    Departments department = Departments.DEVELOPMENT;
    Duration duration = Duration.THREE_MONTHS;
    Grades grade = Grades.GRADE_3;
    List<Roles> roles = new ArrayList<>();
    Roles role = new Roles();
    role.setDepartment(department);
    role.setDuration(duration);
    role.setGradeRequired(grade);
    roles.add(role);

    when(rolesRepository.findAll()).thenReturn(roles);

    List<Roles> result = staffingManagerBusinessService.getAvailableRolesByMultiFilters(grade,
        department,
        duration);

    assertEquals(roles, result);
    verify(rolesRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("When getRoleDetails gets called then it should throw a BadRequestException when roleId provided is null")
  void getRoleDetailsThrowsExceptionWhenRoleIdIsNull400() {
    assertThrows(BadRequestException.class,
        () -> staffingManagerBusinessService.getRoleDetails(null));
    verify(rolesRepository, never()).findByRoleId(any());
  }

  @Test
  @DisplayName("When getRoleDetails is called then it should return the role details when roleId provided is valid")
  void getRoleDetailsReturnsRoleDetailsWhenRoleIdIsValid200() {
    Roles role = new Roles();
    role.setRoleId(123L);
    role.setRoleName("Developer");
    role.setDepartment(Departments.DEVELOPMENT);
    role.setJobDescription("Software Engineer");
    role.setDuration(Duration.THREE_MONTHS);
    role.setGradeRequired(Grades.GRADE_3);
    role.setStaffingManagerEmailAddress("test@example.com");

    when(rolesRepository.findByRoleId(role.getRoleId())).thenReturn(role);

    Roles result = staffingManagerBusinessService.getRoleDetails(role.getRoleId());

    assertEquals(role, result);
    verify(rolesRepository, times(1)).findByRoleId(role.getRoleId());
  }

  @Test
  @DisplayName("When deleteRole is called with a valid roleId, it should delete the role and return true to indicate successful deletion")
  void deleteRoleDeletesRoleWhenRoleIdIsPresentInSystem() {
    Long roleId = 123L;
    Roles existingRole = new Roles();
    existingRole.setRoleId(roleId);

    when(rolesRepository.findByRoleId(roleId)).thenReturn(existingRole);
    doNothing().when(rolesRepository).delete(existingRole);

    Boolean result = staffingManagerBusinessService.deleteRole(roleId);

    assertTrue(result);
    verify(rolesRepository, times(1)).findByRoleId(roleId);
    verify(rolesRepository, times(1)).delete(existingRole);
  }

  @Test
  @DisplayName("When deleteRole is called with an not present roleId, it should return false")
  void deleteRoleReturnsFalseWhenRoleIdProvidedIsNotPresentInSystem() {
    Long roleId = 123L;

    when(rolesRepository.findByRoleId(roleId)).thenReturn(null);

    Boolean result = staffingManagerBusinessService.deleteRole(roleId);

    assertFalse(result);
    verify(rolesRepository, times(1)).findByRoleId(roleId);
    verify(rolesRepository, never()).delete(any());
  }
}
