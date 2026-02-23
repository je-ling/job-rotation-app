package project.job_rotation_app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.job_rotation_app.model.Roles;
import project.job_rotation_app.repository.RolesRepository;

@Service
public class EmployeeBusinessServiceImpl {

    @Autowired
    RolesRepository rolesRepository;

    public List<Roles> getAvailableRoles() {
        return rolesRepository.findAll();
    }
}
