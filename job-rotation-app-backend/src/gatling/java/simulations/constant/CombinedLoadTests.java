package simulations.constant;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.nothingFor;
import static io.gatling.javaapi.http.HttpDsl.http;

import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class CombinedLoadTests extends Simulation {

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
            constantUsersPerSec(1).during(10)
        ),
        getAvailableRolesByGradeForEmployee.scn.injectOpen(
            nothingFor(5),
            constantUsersPerSec(
                1)
                .during(10)
        ),
        getAvailableRolesByDepartmentForEmployee.scn.injectOpen(
            nothingFor(5),
            constantUsersPerSec(
                1)
                .during(10)
        ),
        getAvailableRolesByDurationForEmployee.scn.injectOpen(
            nothingFor(5),
            constantUsersPerSec(
                1)
                .during(10)
        ),
        getAvailableRolesByFiltersForEmployee.scn.injectOpen(
            nothingFor(5),
            constantUsersPerSec(
                1)
                .during(10)
        ),
        getEnumValuesForEmployee.scn.injectOpen(
            nothingFor(5),
            constantUsersPerSec(
                1)
                .during(10)
        ),
        getRoleDetailsForEmployee.scn.injectOpen(
            nothingFor(5),
            constantUsersPerSec(
                1)
                .during(10)
        ),

        // Staffing Manager APIs
        loginStaffingManager.scn.injectOpen(
            nothingFor(5),
            constantUsersPerSec(
                1)
                .during(10)
        ),
        getAvailableRolesForStaffingManager.scn.injectOpen(
            nothingFor(5),
            constantUsersPerSec(
                1)
                .during(10)
        ),
        getAvailableRolesByFiltersForStaffingManager.scn.injectOpen(
            nothingFor(5),
            constantUsersPerSec(
                1)
                .during(10)
        ),
        getEnumValuesForStaffingManager.scn.injectOpen(
            nothingFor(5),
            constantUsersPerSec(
                1)
                .during(10)
        ),
        getRoleDetailsForStaffingManager.scn.injectOpen(
            nothingFor(5),
            constantUsersPerSec(
                1)
                .during(10)
        ),

        createRole.scn.injectOpen(
            nothingFor(5),
            constantUsersPerSec(
                1)
                .during(10)
        ),
        updateRole.scn.injectOpen(
            nothingFor(5),
            constantUsersPerSec(
                1)
                .during(10)
        ),
        deleteRole.scn.injectOpen(
            nothingFor(5),
            constantUsersPerSec(
                1)
                .during(10)
        )
    ).protocols(httpProtocol);
  }
}