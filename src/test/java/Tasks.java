import Model.ToDo;
import io.restassured.http.ContentType;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Tasks {
    /**
     * Task 2
     * create a request to https://httpstat.us/203
     * expect status 203
     * expect content type TEXT
     */

    @Test
    public void task2(){
        given()
                .when()
                .get("https://httpstat.us/203")
                .then()
                .log().all()
                .statusCode(203)
                .contentType(ContentType.TEXT)

        ;

    }

    /**
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * Converting Into POJO
     */


    @Test
    public void task3(){
        ToDo todo=
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .statusCode(200)
                .extract().body().as(ToDo.class)

        ;
        System.out.println("todo = " + todo);
        System.out.println("todo.getTitle() = " + todo.getTitle());
    }


    /**

     Task 3
     create a request to https://jsonplaceholder.typicode.com/todos/2
     expect status 200
     expect content type JSON
     expect title in response body to be "quis ut nam facilis et officia qui"
     */

    @Test
    public void task4(){


                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")
                        .then()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .body("title",equalTo("quis ut nam facilis et officia qui"))
                ;

    }
/**
    create a request to https://jsonplaceholder.typicode.com/todos/2
    expect status 200
    expect content type JSON
    expect response completed status to be false
    extract completed field and testNG assertion
 */

@Test
    public void task5(){
    given()


            .when()
            .get("https://jsonplaceholder.typicode.com/todos/2")
            .then()
            .log().body()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("completed",equalTo(false))
    ;


       //2.yöntem
    Boolean completed=
    given()


            .when()
            .get("https://jsonplaceholder.typicode.com/todos/2")
            .then()
            .log().body()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .extract().path("completed")

    ;
    Assert.assertFalse(completed);
}
}
