package simulations.constant;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class CreateRole extends Simulation {

  HttpProtocolBuilder httpProtocol = http
      .baseUrl("http://localhost:8080")
      .acceptHeader("application/json")
      .contentTypeHeader("application/json");

  ScenarioBuilder scn = scenario("Create Role Load Test")
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
          http("Create Role")
              .post("/staffing-manager/create-role")
              .header("Authorization", "Bearer #{jwtToken}")
              .body(StringBody("""
                  {
                  "roleId": 0,
                  "roleName":"Java Developer",
                  "gradeRequired":"Grade 3",
                  "department":"Development",
                  "duration":"Six Months",
                  "client":"Client A",
                  "jobDescription":"test",
                  "staffingManagerEmailAddress":"charlie.brown@example.com",
                  "location":"London",
                  "startDate":"2026-07-01",
                  "securityClearanceRequired":"NO"
                  }
                  """))
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
