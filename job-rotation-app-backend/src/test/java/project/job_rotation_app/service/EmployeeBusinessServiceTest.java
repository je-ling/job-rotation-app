package project.job_rotation_app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
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

class EmployeeBusinessServiceTest {

  @Mock
  private RolesRepository rolesRepository;

  @InjectMocks
  private EmployeeBusinessServiceImpl employeeBusinessService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("When getAvailableRoles is called then it should return all available roles")
  void getAvailableRolesReturnsAllAvaRoles200() {
    List<Roles> roles = new ArrayList<>();
    Roles role = new Roles();
    roles.add(role);

    when(rolesRepository.findAll()).thenReturn(roles);

    List<Roles> result = employeeBusinessService.getAvailableRoles();

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

    List<Roles> result = employeeBusinessService.getAvailableRolesByGrade(grade);

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

    List<Roles> result = employeeBusinessService.getAvailableRolesByDepartment(department);

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

    List<Roles> result = employeeBusinessService.getAvailableRolesByDuration(duration);

    assertEquals(roles, result);
    verify(rolesRepository, times(1)).findByDuration(duration);
  }

  @Test
  @DisplayName("When getRoleDetails gets called then it should throw a BadRequestException when roleId provided is null")
  void getRoleDetailsThrowsExceptionWhenRoleIdIsNull400() {
    assertThrows(BadRequestException.class, () -> employeeBusinessService.getRoleDetails(null));
    verify(rolesRepository, never()).findByRoleId(any());
  }

  @Test
  @DisplayName("When getRoleDetails is called then it should return role details when roleId provided is valid")
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

    Roles result = employeeBusinessService.getRoleDetails(role.getRoleId());

    assertEquals(role, result);
    verify(rolesRepository, times(1)).findByRoleId(role.getRoleId());
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

    List<Roles> result = employeeBusinessService.getAvailableRolesByMultiFilters(grade, department,
        duration);

    assertEquals(roles, result);
    verify(rolesRepository, times(1)).findAll();
  }
}
