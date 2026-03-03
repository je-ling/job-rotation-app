package project.job_rotation_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "staffing_managers")
public class StaffingManagerUser {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long userId;

  private String emailAddress;
  private String password;
}
