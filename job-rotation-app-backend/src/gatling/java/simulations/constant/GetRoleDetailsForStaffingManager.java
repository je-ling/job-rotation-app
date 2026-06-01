package simulations.constant;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class GetRoleDetailsForStaffingManager extends Simulation {

  HttpProtocolBuilder httpProtocol = http
      .baseUrl("http://localhost:8080")
      .acceptHeader("application/json")
      .contentTypeHeader("application/json");

  ScenarioBuilder scn = scenario("Get Role Details For Staffing Manager Load Test")

      .exec(
          http("Login Staffing Manager")
              .post("/staffing-manager/login")
              .header("Content-Type", "application/json")
              .body(StringBody("""
                    {
                      "emailAddress": "john.doe@example.com",
                      "password": "pass"
                    }
                  """))
              .check(status().is(200))
      )

      .exec(
          http("Get Role Details For Staffing Manager")
              .get("/staffing-manager/available-roles/202")
              .header("Content-Type", "application/json")
              .check(status().is(200))
      );

  {
    setUp(
        scn.injectOpen(
            constantUsersPerSec(
                1)
                .during(5)
        )
    ).protocols(httpProtocol);
  }
}
