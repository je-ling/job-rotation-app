package project.job_rotation_app.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.job_rotation_app.dto.RoleInformationDto;
import project.job_rotation_app.exception.BadRequestException;
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
    return rolesRepository.findByDepartment(department);
  }

  public List<Roles> getAvailableRolesByDuration(Duration duration) {
    return rolesRepository.findByDuration(duration);
  }

  public List<Roles> getAvailableRolesByMultiFilters(Grades grade, Departments department,
      Duration duration) {
    return rolesRepository.findAll().stream()
        .filter(r -> grade == null || r.getGradeRequired() == grade)
        .filter(r -> department == null || r.getDepartment() == department)
        .filter(r -> duration == null || r.getDuration() == duration)
        .collect(Collectors.toList());
  }

  public Roles getRoleDetails(Long roleId) {
    if (roleId == null) {
      throw new BadRequestException("Role ID has not been provided");
    }

    Roles role = rolesRepository.findByRoleId(roleId);

    RoleInformationDto roleInformationDto = new RoleInformationDto();
    roleInformationDto.setRoleId(roleId);
    roleInformationDto.setRoleName(role.getRoleName());
    roleInformationDto.setDepartment(role.getDepartment().toString());
    roleInformationDto.setDuration(role.getDuration().toString());
    roleInformationDto.setGradeRequired(role.getGradeRequired().toString());
    roleInformationDto.setJobDescription(role.getJobDescription());
    roleInformationDto.setStaffingManagerEmailAddress(role.getStaffingManagerEmailAddress());

    return role;
  }
}
