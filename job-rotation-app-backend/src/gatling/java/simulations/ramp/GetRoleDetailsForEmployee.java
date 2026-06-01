package simulations.ramp;

import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class GetRoleDetailsForEmployee extends Simulation {

  HttpProtocolBuilder httpProtocol = http
      .baseUrl("http://localhost:8080")
      .acceptHeader("application/json")
      .contentTypeHeader("application/json");

  ScenarioBuilder scn = scenario("Get Role Details For Employee Stress Test")
      .exec(
          http("Get Role Details For Employee")
              .get("/employee/available-roles/202")
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
