package project.job_rotation_app.service;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.job_rotation_app.dto.RoleInformationDto;
import project.job_rotation_app.model.Departments;
import project.job_rotation_app.model.Duration;
import project.job_rotation_app.model.Grades;
import project.job_rotation_app.model.Roles;
import project.job_rotation_app.repository.RolesRepository;

@Service
public class StaffingManagerBusinessServiceImpl {

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
      Duration duration, String client) {
    return rolesRepository.findAll().stream()
        .filter(r -> grade == null || r.getGradeRequired() == grade)
        .filter(r -> department == null || r.getDepartment() == department)
        .filter(r -> duration == null || r.getDuration() == duration)
        .filter(r -> client == null || r.getClient() == client)
        .collect(Collectors.toList());
  }

  public Roles getRoleDetails(Long roleId) throws Exception {
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
    roleInformationDto.setLocation(role.getLocation());
    roleInformationDto.setStartDate(role.getStartDate());
    roleInformationDto.setSecurityClearanceRequired(role.getSecurityClearanceRequired());

    return role;
  }

  public Roles createRole(Roles role) throws Exception {
    if (role.getRoleName() == null || role.getRoleName().isEmpty() ||
        role.getDepartment() == null ||
        role.getJobDescription().isEmpty() ||
        role.getDuration() == null ||
        role.getGradeRequired() == null ||
        role.getStaffingManagerEmailAddress().isEmpty() || role.getLocation().isEmpty()
        || role.getStartDate() == null) {

      throw new BadRequestException("Required fields of create role request cannot be empty");
    }

    role.setRoleId(null);
    role.setVersion(0);

    return rolesRepository.save(role);
  }

  public Roles updateRole(Long roleId, Roles updatedRole) throws Exception {
    Roles existingRole = rolesRepository.findByRoleId(roleId);
    if (existingRole == null || !existingRole.getRoleId().equals(roleId)) {
      throw new BadRequestException("Role with the provided ID does not exist in the system");
    }

    existingRole.setRoleName(updatedRole.getRoleName());
    existingRole.setDepartment(updatedRole.getDepartment());
    existingRole.setJobDescription(updatedRole.getJobDescription());
    existingRole.setDuration(updatedRole.getDuration());
    existingRole.setGradeRequired(updatedRole.getGradeRequired());
    existingRole.setStaffingManagerEmailAddress(updatedRole.getStaffingManagerEmailAddress());
    existingRole.setVersion(existingRole.getVersion() + 1);

    return rolesRepository.save(existingRole);
  }

  public Boolean deleteRole(Long roleId) {
    Roles existingRole = rolesRepository.findByRoleId(roleId);
    if (existingRole == null || !existingRole.getRoleId().equals(roleId)) {
      return false;
    }

    rolesRepository.delete(existingRole);
    return true;
  }
}