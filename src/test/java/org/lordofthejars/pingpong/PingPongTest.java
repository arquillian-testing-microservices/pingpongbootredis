package org.lordofthejars.pingpong;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.arquillian.cube.DockerUrl;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

@RunWith(Arquillian.class)
public class PingPongTest {

    @DockerUrl(containerName = "pingpongboot", exposedPort = 8080)
    @ArquillianResource
    RequestSpecBuilder requestSpecBuilder;

    @Test
    public void should_get_pongs() {

        final RequestSpecification pingpongService = requestSpecBuilder.build();

        given()
            .spec(pingpongService)
            .body("pong")
            .when()
            .post("/ping")
            .then()
            .assertThat()
            .statusCode(is(201));

        given()
            .spec(pingpongService)
            .when()
            .get("/ping")
            .then()
            .assertThat()
            .body("$", hasItems("pong"));

    }

}
