import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;



public class rest {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    public void login(){

                given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                        .body( "token", notNullValue());
    }
    @Test
    public void getSingleUserTest(){
        given()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users/5")
                .then()
                .statusCode(200)
                .body( "data.id",equalTo(5))//
                .body("data.email",equalTo("charles.morris@reqres.in"));
    }
    @Test
    public void getSingleUserTestError(){
        given()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users/5")
                .then()
                .statusCode(400)
                .body( "data.id",equalTo(5))//
                .body("data.email",equalTo("charles.morris@reqres.in"));
    }
}

