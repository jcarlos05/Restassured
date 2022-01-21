import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;



public class rest {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void login(){

                given()
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
                .post("register")
                .then()
                .statusCode(200)
                        .body( "token", notNullValue());
    }
    @Test
    public void getSingleUserTest(){
        given()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body( "data.id",equalTo(2))//
                .body("data.email",equalTo("charles.morris@reqres.in"));
    }
    @Test
    public void getSingleUserTestError(){
        given()
                .get("/users/2")
                .then()
                .statusCode(400)
                .body( "data.id",equalTo(5))//
                .body("data.email",equalTo("charles.morris@reqres.in"));
    }
    @Test
    public void deleteUserTest(){
        given()
                .delete("/users/2")
                .then()
                .statusCode(HttpStatus.SC_OK);
        //HttpStatus.SC_NO_CONTENT
    }
    @Test
    public void patchUserTest(){
        String nameUpdate = given()
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .patch("/users/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath().getString("name");

        assertThat(nameUpdate, equalTo("morpheus"));
    }

}


