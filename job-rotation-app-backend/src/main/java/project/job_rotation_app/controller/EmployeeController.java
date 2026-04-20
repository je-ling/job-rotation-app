package project.job_rotation_app.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.job_rotation_app.model.Departments;
import project.job_rotation_app.model.Duration;
import project.job_rotation_app.model.Grades;
import project.job_rotation_app.model.Roles;
import project.job_rotation_app.service.EmployeeBusinessServiceImpl;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

  @Autowired
  EmployeeBusinessServiceImpl employeeBusinessService;

  @GetMapping("/list-available-roles")
  public List<Roles> getAvailableRoles() {
    return employeeBusinessService.getAvailableRoles();
  }

  @GetMapping("/list-available-roles/{grade}")
  public List<Roles> getAvailableRolesByGrade(@PathVariable Grades grade) {
    return employeeBusinessService.getAvailableRolesByGrade(grade);
  }

  @GetMapping("/list-available-roles/{department}")
  public List<Roles> getAvailableRolesByDepartment(@PathVariable Departments department) {
    return employeeBusinessService.getAvailableRolesByDepartment(department);
  }

  @GetMapping("/list-available-roles/{duration}")
  public List<Roles> getAvailableRolesByDuration(@PathVariable Duration duration) {
    return employeeBusinessService.getAvailableRolesByDuration(duration);
  }

  @GetMapping("/list-available-roles/filter")
  public List<Roles> getAvailableRolesByMultiFilters(
      @RequestParam(required = false) Grades grade,
      @RequestParam(required = false) Departments department,
      @RequestParam(required = false) Duration duration) {
    return employeeBusinessService.getAvailableRolesByMultiFilters(grade, department, duration);
  }

  @GetMapping("/available-roles/{roleId}")
  public Roles getRoleDetails(@PathVariable Long roleId) {
    return employeeBusinessService.getRoleDetails(roleId);
  }

  @GetMapping("/enums")
  public Map<String, List<String>> getEnumValues() {
    Map<String, List<String>> enums = new HashMap<>();
    enums.put("grades", Arrays.asList(
        Arrays.stream(Grades.values()).map(Enum::name).toArray(String[]::new)));
    enums.put("departments", Arrays.asList(
        Arrays.stream(Departments.values()).map(Enum::name).toArray(String[]::new)));
    enums.put("durations", Arrays.asList(
        Arrays.stream(Duration.values()).map(Enum::name).toArray(String[]::new)));
    return enums;
  }
}
