package project.job_rotation_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.job_rotation_app.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long> {

}