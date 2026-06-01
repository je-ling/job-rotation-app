package simulations.ramp;

import static io.gatling.javaapi.core.CoreDsl.nothingFor;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.http.HttpDsl.http;

import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class CombinedRampTests extends Simulation {

  HttpProtocolBuilder httpProtocol = http
      .baseUrl("http://localhost:8080")
      .acceptHeader("application/json")
      .contentTypeHeader("application/json");

  {
    CreateRole createRole = new CreateRole();
    DeleteRole deleteRole = new DeleteRole();
    GetAvailableRolesByDepartmentForEmployee getAvailableRolesByDepartmentForEmployee =
        new GetAvailableRolesByDepartmentForEmployee();
    GetAvailableRolesByDurationForEmployee getAvailableRolesByDurationForEmployee =
        new GetAvailableRolesByDurationForEmployee();
    GetAvailableRolesByFiltersForEmployee getAvailableRolesByFiltersForEmployee =
        new GetAvailableRolesByFiltersForEmployee();
    GetAvailableRolesByFiltersForStaffingManager getAvailableRolesByFiltersForStaffingManager =
        new GetAvailableRolesByFiltersForStaffingManager();
    GetAvailableRolesByGradeForEmployee getAvailableRolesByGradeForEmployee =
        new GetAvailableRolesByGradeForEmployee();
    GetAvailableRolesForEmployee getAvailableRolesForEmployee =
        new GetAvailableRolesForEmployee();
    GetAvailableRolesForStaffingManager getAvailableRolesForStaffingManager =
        new GetAvailableRolesForStaffingManager();
    GetEnumValuesForEmployee getEnumValuesForEmployee =
        new GetEnumValuesForEmployee();
    GetEnumValuesForStaffingManager getEnumValuesForStaffingManager =
        new GetEnumValuesForStaffingManager();
    GetRoleDetailsForEmployee getRoleDetailsForEmployee =
        new GetRoleDetailsForEmployee();
    GetRoleDetailsForStaffingManager getRoleDetailsForStaffingManager =
        new GetRoleDetailsForStaffingManager();
    LoginStaffingManager loginStaffingManager = new LoginStaffingManager();
    UpdateRole updateRole = new UpdateRole();

    setUp(
        // Employee APIs
        getAvailableRolesForEmployee.scn.injectOpen(
            nothingFor(0),
            rampUsersPerSec(1).to(5).during(20)
        ),
        getAvailableRolesByGradeForEmployee.scn.injectOpen(
            nothingFor(5),
            rampUsersPerSec(1).to(5).during(20)
        ),
        getAvailableRolesByDepartmentForEmployee.scn.injectOpen(
            nothingFor(10),
            rampUsersPerSec(1).to(5).during(20)
        ),
        getAvailableRolesByDurationForEmployee.scn.injectOpen(
            nothingFor(15),
            rampUsersPerSec(1).to(5).during(20)
        ),
        getAvailableRolesByFiltersForEmployee.scn.injectOpen(
            nothingFor(20),
            rampUsersPerSec(1).to(5).during(20)
        ),
        getEnumValuesForEmployee.scn.injectOpen(
            nothingFor(25),
            rampUsersPerSec(1).to(3).during(15)
        ),
        getRoleDetailsForEmployee.scn.injectOpen(
            nothingFor(30),
            rampUsersPerSec(1).to(3).during(15)
        ),

        // Staffing Manager APIs
        loginStaffingManager.scn.injectOpen(
            nothingFor(35),
            rampUsersPerSec(1).to(3).during(15)
        ),
        getAvailableRolesForStaffingManager.scn.injectOpen(
            nothingFor(40),
            rampUsersPerSec(1).to(5).during(20)
        ),
        getAvailableRolesByFiltersForStaffingManager.scn.injectOpen(
            nothingFor(60),
            rampUsersPerSec(1).to(5).during(20)
        ),
        getEnumValuesForStaffingManager.scn.injectOpen(
            nothingFor(65),
            rampUsersPerSec(1).to(3).during(15)
        ),
        getRoleDetailsForStaffingManager.scn.injectOpen(
            nothingFor(70),
            rampUsersPerSec(1).to(3).during(15)
        ),

        createRole.scn.injectOpen(
            nothingFor(80),
            rampUsersPerSec(1).to(2).during(15)
        ),
        updateRole.scn.injectOpen(
            nothingFor(100),
            rampUsersPerSec(1).to(2).during(15)
        ),
        deleteRole.scn.injectOpen(
            nothingFor(120),
            rampUsersPerSec(1).to(2).during(15)
        )
    ).protocols(httpProtocol);
  }
}