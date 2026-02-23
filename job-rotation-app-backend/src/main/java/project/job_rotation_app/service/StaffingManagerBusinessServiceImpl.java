package project.job_rotation_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.job_rotation_app.exception.BadRequestException;
import project.job_rotation_app.model.Roles;
import project.job_rotation_app.repository.RolesRepository;

@Service
public class StaffingManagerBusinessServiceImpl {

    @Autowired
    RolesRepository rolesRepository;

    public Roles createRole(Roles role) {
        if(role.getRoleName().isEmpty() ||
            role.getDepartment() == null ||
            role.getJobDescription().isEmpty() ||
            role.getDuration().isEmpty() ||
            role.getGradeRequired() == null ||
            role.getStaffingManagerEmailAddress().isEmpty()) {
            throw new BadRequestException("Required fields of create role request cannot be empty");
        }
        return rolesRepository.save(role);
    }
}
