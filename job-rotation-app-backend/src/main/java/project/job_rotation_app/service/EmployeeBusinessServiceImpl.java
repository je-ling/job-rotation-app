package project.job_rotation_app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.job_rotation_app.model.Departments;
import project.job_rotation_app.model.Duration;
import project.job_rotation_app.model.Grades;
import project.job_rotation_app.model.Roles;
import project.job_rotation_app.repository.RolesRepository;

@Service
public class EmployeeBusinessServiceImpl {

  @Autowired
  RolesRepository rolesRepository;

  public List<Roles> getAvailableRoles() {
    return rolesRepository.findAll();
  }

  public List<Roles> getAvailableRolesByGrade(Grades grade) {
    return rolesRepository.findByGradeRequired(grade);
  }

  public List<Roles> getAvailableRolesByDepartment(Departments department) {
    return rolesRepository.findByDepartmentRequired(department);
  }

  public List<Roles> getAvailableRolesByDuration(Duration duration) {
    return rolesRepository.findByDurationSpecified(duration);
  }
}
