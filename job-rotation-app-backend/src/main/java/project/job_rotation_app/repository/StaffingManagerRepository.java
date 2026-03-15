package project.job_rotation_app.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.job_rotation_app.model.StaffingManagerUser;

public interface StaffingManagerRepository extends JpaRepository<StaffingManagerUser, String> {

  Optional<StaffingManagerUser> findByEmail(String emailAddress);
}
