package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class CotacaoResourceTest {

  @Test
  public void testCotacaoDataFutura() {
    given()
      .body("{\"data\": \"2023-04-23T18:25:43.511Z\"}")
      .header("Content-Type", "application/json")
      .when()
      .post("/cotacao")
      .then()
      .statusCode(400)
        .body("violations[0].message", is("Não pode ser uma data futura"));
  }

  @Test
  public void testCotacaoDataNula() {
    given()
      .body("{\"data\": null}")
      .header("Content-Type", "application/json")
      .when()
      .post("/cotacao")
      .then()
      .statusCode(400)
      .body("violations[0].message", is("Data não pode ser nula"));
  }
}
