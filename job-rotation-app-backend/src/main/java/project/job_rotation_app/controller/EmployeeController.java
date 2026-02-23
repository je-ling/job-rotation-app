package project.job_rotation_app.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
