package simulations.constant;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
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

  ScenarioBuilder scn = scenario("Get Available Roles By Duration For Employee Load Test")
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
            constantUsersPerSec(
                1)
                .during(10)
        )
    ).protocols(httpProtocol);
  }
}
