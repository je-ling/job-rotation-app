package project.job_rotation_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.job_rotation_app.model.Roles;
import project.job_rotation_app.service.StaffingManagerBusinessServiceImpl;

@RestController
@RequestMapping("/staffing-manager")
public class StaffingManagerController {

  @Autowired
  StaffingManagerBusinessServiceImpl staffingManagerBusinessService;

  @PostMapping("/create-role")
  public Roles createRole(Roles role) {
    return staffingManagerBusinessService.createRole(role);
  }
}
