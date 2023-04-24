import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ZippoTest {

    @Test
    public void Test() {
        given()
                // Hazırlık işlemleri : (token,send body ,parametreler)

                .when()
                // endpoint(url), metodu

                .then()
        // assertion , test , data işlemleri


        ;
    }

    @Test
    public void statusCodeTest() {
        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body() // dönen body json datası , btün her şeyi göstermesi için log.all yazılabilinir
                .statusCode(200) // dönüş kodu 200 mü demek

        ;
    }


    @Test
    public void contentTypeTest() {
        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body() // dönen body json datası , btün her şeyi göstermesi için log.all yazılabilinir
                .statusCode(200) // dönüş kodu 200 mü demek
                .contentType(ContentType.JSON) // dönen sonuc JSON mı

        ;
    }
}
