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

public class UpdateRole extends Simulation {

  HttpProtocolBuilder httpProtocol = http
      .baseUrl("http://localhost:8080")
      .acceptHeader("application/json")
      .contentTypeHeader("application/json");

  ScenarioBuilder scn = scenario("Update Role Load Test")
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
          http("Update Role")
              .put("/staffing-manager/update-role/202")
              .header("Authorization", "Bearer #{jwtToken}")
              .body(StringBody("""
                  {
                      "client": "McDonalds",
                      "department": "Digital Experience",
                      "duration": "Three Months ",
                      "gradeRequired": "Grade 3",
                      "jobDescription": "You will be responsible for creating user-friendly and intuitive digital experiences for the client. The role involves understanding user needs, designing interfaces, and improving the usability and accessibility of a system. A typical day involves research, creating wireframes and prototypes, and working closely with developers to ensure the final product meets user expectations and accessibility needs. \\\\n\\\\nOn site requirements - hybrid, monthly workshops. \\\\n\\\\nPreferable experience in frontend development or user-centric experience.",
                      "location": "Newcastle",
                      "roleId": 202,
                      "roleName": "UX Designer",
                      "securityClearanceRequired": "No",
                      "staffingManagerEmailAddress": "jane.doe@example.com",
                      "startDate": "2026-05-11",
                      "version": 3
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
