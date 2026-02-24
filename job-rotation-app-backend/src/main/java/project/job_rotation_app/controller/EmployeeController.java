package project.job_rotation_app.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
  public List<Roles> getAvailableRolesByGrade(Grades grade) {
    return employeeBusinessService.getAvailableRolesByGrade(grade);
  }

  @GetMapping("/list-available-roles/{department}")
  public List<Roles> getAvailableRolesByDepartment(Departments department) {
    return employeeBusinessService.getAvailableRolesByDepartment(department);
  }

  @GetMapping("/list-available-roles/{duration}")
  public List<Roles> getAvailableRolesByDuration(Duration duration) {
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
  public Roles getRoleDetails(@RequestParam Long roleId) {
    return employeeBusinessService.getRoleDetails(roleId);
  }
}
