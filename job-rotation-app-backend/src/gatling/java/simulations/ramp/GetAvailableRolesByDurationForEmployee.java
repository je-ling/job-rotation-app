package simulations.ramp;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class GetAvailableRolesByDurationForEmployee extends Simulation {

  HttpProtocolBuilder httpProtocol = http
      .baseUrl("http://localhost:8080")
      .acceptHeader("application/json")
      .contentTypeHeader("application/json");

  ScenarioBuilder scn = scenario("Get Available Roles By Duration For Employee Stress Test")
      .exec(
          http("Login")
              .post("/staffing-manager/login")
              .body(StringBody("""
                  {
                    "emailAddress": "charlie.brown@example.com",
                    "password": "pass"
                  }
                  """))
              .check(status().is(200))
              .check(jsonPath("$.token").saveAs("jwtToken"))
      )

      .exec(
          http("Get Available Roles By Duration For Employee")
              .get(
                  "/employee/list-available-roles?duration=THREE_MONTHS")
              .header("Content-Type", "application/json")
              .check(status().is(200))
      );

  {
    setUp(
        scn.injectOpen(
            rampUsersPerSec(
                1)
                .to(10)
                .during(10)
        )
    ).protocols(httpProtocol);
  }
}

