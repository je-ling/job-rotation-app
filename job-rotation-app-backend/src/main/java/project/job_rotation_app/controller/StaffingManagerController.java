package project.job_rotation_app.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.job_rotation_app.model.Departments;
import project.job_rotation_app.model.Duration;
import project.job_rotation_app.model.Grades;
import project.job_rotation_app.model.Roles;
import project.job_rotation_app.service.StaffingManagerBusinessServiceImpl;

@RestController
@RequestMapping("/staffing-manager")
public class StaffingManagerController {

  @Autowired
  StaffingManagerBusinessServiceImpl staffingManagerBusinessService;

  @GetMapping("/get-all-roles")
  public List<Roles> getAvailableRoles() {
    return staffingManagerBusinessService.getAvailableRoles();
  }

  @GetMapping("/list-available-roles/{grade}")
  public List<Roles> getAvailableRolesByGrade(Grades grade) {
    return staffingManagerBusinessService.getAvailableRolesByGrade(grade);
  }

  @GetMapping("/list-available-roles/{department}")
  public List<Roles> getAvailableRolesByDepartment(Departments department) {
    return staffingManagerBusinessService.getAvailableRolesByDepartment(department);
  }

  @GetMapping("/list-available-roles/{duration}")
  public List<Roles> getAvailableRolesByDuration(Duration duration) {
    return staffingManagerBusinessService.getAvailableRolesByDuration(duration);
  }

  @GetMapping("/available-roles/{roleId}")
  public Roles getRoleDetails(@RequestParam Long roleId) {
    return staffingManagerBusinessService.getRoleDetails(roleId);
  }

  @GetMapping("/list-available-roles/filter")
  public List<Roles> getAvailableRolesByMultiFilters(
      @RequestParam(required = false) Grades grade,
      @RequestParam(required = false) Departments department,
      @RequestParam(required = false) Duration duration) {
    return staffingManagerBusinessService.getAvailableRolesByMultiFilters(grade, department,
        duration);
  }

  @PostMapping("/create-role")
  public Roles createRole(Roles role) {
    return staffingManagerBusinessService.createRole(role);
  }

  @PutMapping("/update-role/{roleId}")
  public Roles updateRole(@RequestParam Long roleId, Roles updatedRole) {
    return staffingManagerBusinessService.updateRole(roleId, updatedRole);
  }
}
