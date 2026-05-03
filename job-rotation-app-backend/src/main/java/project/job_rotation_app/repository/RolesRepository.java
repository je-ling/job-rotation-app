package project.job_rotation_app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.job_rotation_app.model.Departments;
import project.job_rotation_app.model.Duration;
import project.job_rotation_app.model.Grades;
import project.job_rotation_app.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {

  List<Roles> findByGradeRequired(Grades gradeRequired);

  List<Roles> findByDepartment(Departments department);

  List<Roles> findByDuration(Duration duration);

  Roles findByRoleId(Long roleId);
}