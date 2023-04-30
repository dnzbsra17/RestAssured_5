package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoRestUsersTests {

    //ce227f68fe9820c032012cae8b760cf3f5efda202e0f46a3ed6bba04a1e93931
    //{"name":"{{$randomFullName}}", "gender":"male", "email":"{{$randomEmail}}", "status":"active"}
    //POST https://gorest.co.in/public/v2/users

    Faker randomUreteci = new Faker();
    int userID;

    RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {
        baseURI = "https://gorest.co.in/public/v2/users";
        requestSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer de91bd182c9a485a7e6bb99b0a45c31781ec08ba739c844e1e035ca5361b6a69")
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test(enabled = false)
    public void createUserJSON() {
        String rndFullName = randomUreteci.name().fullName();
        String rndEmail = randomUreteci.internet().emailAddress();

        userID =

                given()
                        .spec(requestSpec)
                        .body("{\"name\":\"" + rndFullName + "\", \"gender\":\"male\", \"email\":\"" + rndEmail + "\", \"status\":\"active\"}")
                        //.log().uri()
                        //.log().body()

                        .when()
                        .post("https://gorest.co.in/public/v2/users")


                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id");

    }

    @Test
    public void createUserMap() {
        String rndFullName = randomUreteci.name().fullName();
        String rndEmail = randomUreteci.internet().emailAddress();


        Map<String, String> newUser = new HashMap<>();
        newUser.put("name", rndFullName);
        newUser.put("gender", "male");
        newUser.put("email", rndEmail);
        newUser.put("status", "active");

        userID =

                given()
                        .spec(requestSpec)
                        .body(newUser)


                        .when()
                        .post("")


                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id");

    }

    @Test(enabled = false)
    public void createUserClass() {
        String rndFullName = randomUreteci.name().fullName();
        String rndEmail = randomUreteci.internet().emailAddress();


        User newUser = new User();
        newUser.name = rndFullName;
        newUser.gender = "male";
        newUser.email = rndEmail;
        newUser.status = "active";

        userID =

                given()
                        .spec(requestSpec)
                        .body(newUser)
                        //.log().uri()
                        //.log().body()

                        .when()
                        .post("https://gorest.co.in/public/v2/users")


                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id");
    }

    @Test(dependsOnMethods = "createUserMap")
    public void getUserByID() {
        String rndFullName = randomUreteci.name().fullName();
        String rndEmail = randomUreteci.internet().emailAddress();

        given()
                .spec(requestSpec)
                .when()
                .get("" + userID)

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(userID))
        ;
    }

    @Test(dependsOnMethods = "getUserByID")
    public void updateUser() {
        Map<String, String> updateUser = new HashMap<>();
        updateUser.put("name", "mehmet");
        given()
                .spec(requestSpec)
                .body(updateUser)

                .when()
                .put("" + userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(userID))
                .body("name", equalTo("mehmet"))


        ;
    }
    @Test(dependsOnMethods ="updateUser" )
    public void deleteUser(){

        given()
                .spec(requestSpec)
                .when()
                .delete("" + userID)

                .then()
                .log().body()
                .statusCode(204);




    }

    @Test(dependsOnMethods = "deleteUser")
    public void deleteUserNegative(){

        given()
                .spec(requestSpec)

                .when()
                .delete("" + userID)

                .then()
                .log().body()
                .statusCode(404);


    }
}