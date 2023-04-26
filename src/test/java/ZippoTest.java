import Model.Location;
import Model.Place;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    @Test
    public void Test() {
        given()
                // Hazırlık işlemleri : (token,send body ,parametreler)

                .when()
                // endpoint(url), metodu(Get ,Post ,Put , Delete )

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
                .log().body() // dönen body json datası , bütün her şeyi göstermesi için log.all yazılabilinir
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

    @Test
    public void checkCountryInResponseBody() {

        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body()
                .statusCode(200)
                .body("country", equalTo("United States"))

        // body nin country değişkeni "United States" eşit mi

        ;
    }


    //
//    PM                            RestAssured
//    body.country                  body("country")
//    body.'post code'              body("post code")
//    body.places[0].'place name'   body("places[0].'place name'")
//    body.places.'place name'      body("places.'place name'")
//    bütün place nameleri bir arraylist olarak verir
//    https://jsonpathfinder.com/

    @Test
    public void checkStateInResponseBody() {
        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body() // dönen body json datası , btün her şeyi göstermesi için log.all yazılabilinir
                .statusCode(200) // dönüş kodu 200 mü demek
                .body("places[0].state", equalTo("California"))
        // body nin country değişkeni "United States" eşit mi

        ;
    }

    @Test
    public void checkHasItemy() {
        given()


                .when()
                .get("http://api.zippopotam.us/tr/01000")


                .then()
                //.log().body()
                .statusCode(200)
                .body("places.'place name'", hasItem("Dörtağaç Köyü"))
                // bütün place name lerde dört ağaç köyü var mı

        ;
    }

    @Test
    public void bodyArrayHasSizeTest() {
        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                //.log().body()
                .statusCode(200)
                .body("places", hasSize(1))


        ;
    }

    @Test
    public void combiningTest() {
        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                //.log().body()
                .statusCode(200)
                .body("places", hasSize(1)) // size ı 1 mi
                .body("places.state", hasItem("California")) // verilen paht deki list bu item e sahip mi
                .body("places[0].'place name'", equalTo("Beverly Hills")) // verilen paht deki değer buna eşit mi


        ;
    }

    @Test
    public void pahtParamTest() {
        given()
                .pathParam("ulke", "us")
                .pathParam("postaKod", 90210)
                .log().uri() // request Link


                .when()
                .get("http://api.zippopotam.us/{ulke}/{postaKod}")


                .then()
                .statusCode(200)
        //.log().body()


        ;
    }

    @Test
    public void queryParamTest() {

        //https://gorest.co.in/public/v1/users?page=2
        given()
                .param("page", 1) // ?page=2 şeklinde linke ekleniyor
                .log().uri() // request Link


                .when()
                .get("https://gorest.co.in/public/v1/users")// ?page=1


                .then()
                .statusCode(200)
        //.log().body()


        ;
    }

    @Test
    public void queryParamTest2() {
        // https://gorest.co.in/public/v1/users?page=3
        // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
        // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.

        for (int i = 1; i < 10; i++) {

            given()
                    .param("page", i) // ?page=2 şeklinde linke ekleniyor
                    .log().uri() // request Link


                    .when()
                    .get("https://gorest.co.in/public/v1/users")// ?page=1


                    .then()
                    .statusCode(200)
                    //.log().body()
                    .body("meta.pagination.page", equalTo(i))


            ;

        }


    }

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void Setup() {
        baseURI = "https://gorest.co.in/public/v1";
        requestSpec = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setContentType(ContentType.JSON)
                .build();
        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .log(LogDetail.BODY)
                .build();
    }


    @Test
    public void test1() {

        //https://gorest.co.in/public/v1/users?page=2
        given()
                .param("page", 1) // ?page=2 şeklinde linke ekleniyor
                .spec(requestSpec)


                .when()
                .get("/users")// ?page=1


                .then()
                .spec(responseSpec)
        //.log().body()


        ;
    }

    @Test
    public void extractingJsonPath() {
        String countryName =
                given()


                        .when()
                        .get("http://api.zippopotam.us/us/90210")


                        .then()
                        .extract().path("country");
        System.out.println("countryName = " + countryName);
        Assert.assertEquals(countryName, "United States");
    }


    @Test
    public void extractingJsonPath2() {
        String placesName =
                given()


                        .when()
                        .get("http://api.zippopotam.us/us/90210")


                        .then()
                        .log().body()
                        .extract().path("places[0].'place name'");
        System.out.println("countryName = " + placesName);
        Assert.assertEquals(placesName, "Beverly Hills");
    }

    @Test
    public void extractingJsonPath3() {
        // http://gorest.co.in/public/v1/users dönen değerdeki limit bilgisini yazdırınız

        int limit =
                given()


                        .when()
                        .get("http://gorest.co.in/public/v1/users")


                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().path("meta.pagination.limit");
        System.out.println("limit = " + limit);

    }


    @Test
    public void extractingJsonPath4() {
        // http://gorest.co.in/public/v1/users dönen değerdeki bütün idlerin bilgisini yazdırınız


        List<Integer> idler =
                given()


                        .when()
                        .get("http://gorest.co.in/public/v1/users")


                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().path("data.id");
        System.out.println("idler = " + idler);
    }

    @Test
    public void extractingJsonPath5() {
        // http://gorest.co.in/public/v1/users dönen değerdeki bütün namelerin bilgisini yazdırınız


        List<String> names =
                given()


                        .when()
                        .get("http://gorest.co.in/public/v1/users")


                        .then()
                        .statusCode(200)
                        .extract().path("data.name");
        System.out.println("names = " + names);
    }


    @Test
    public void extractingJsonResponseAll() {
        // http://gorest.co.in/public/v1/users dönen değerdeki bütün namelerin bilgisini yazdırınız


        Response donenData =
                given()


                        .when()
                        .get("http://gorest.co.in/public/v1/users")


                        .then()
                        .statusCode(200)
                        .extract().response();// dönen tüm datayı verir
        List<Integer> idler=donenData.path("data.id");
        List<String> names=donenData.path("data.name");
        int limit =donenData.path("meta.pagination.limit");

        System.out.println("idler = " + idler);
        System.out.println("name = " + names);
        System.out.println("limit = " + limit);

        Assert.assertTrue(names.contains("Gouranga Panicker"));
        Assert.assertTrue(idler.contains(1203771));
        Assert.assertEquals(limit,10,"test sonucu hatalı");


    }
    @Test
    public void extractJsonAll(){

        Location locationNesne=
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")




                .then()
                //.log().body()
                .statusCode(200)
                .extract().body().as(Location.class)
                 // Location şablonuna
        ;
        System.out.println("locationNesnesi.getCountry = " +locationNesne.getCountry() );
        System.out.println("locationNesne.getCountryabbreviation() = " + locationNesne.getCountryabbreviation());
        for (Place p:locationNesne.getPlaces()){
            System.out.println("p = " + p);
            System.out.println(locationNesne.getPlaces().get(0).getPlacename());

        }


    }

    @Test
    public void extractPOJO_Soru(){
        //aşağıdaki endpointte(link) Dörtağaç Köyüne ait d,ğer bilgileri yazdırınız
Location adana=
                 given()
                .when()
                .get("http://api.zippopotam.us/tr/01000")


                .then()
                //.log().body()
                .statusCode(200)
                .extract().body().as(Location.class)
        ;

        for (Place p:adana.getPlaces()){
            if (p.getPlacename().equalsIgnoreCase("Dörtağaç Köyü")){
                System.out.println("p = " + p);
            }

        }

    }


}




