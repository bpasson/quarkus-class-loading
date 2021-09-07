package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.spi.Bootstrap;
import javax.money.spi.CurrencyProviderSpi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AmountResourceTest {

    @Test
    public void testSPIs() {
        Bootstrap.getServices(CurrencyProviderSpi.class).forEach( p -> {
            System.out.println("Found currency provider " + p.getClass());
        });

    }

//    @Test
//    public void testCurrencyLoaded() {
//        Assertions.assertTrue(Monetary.getCurrencies().stream()
//                .anyMatch( c -> c.getCurrencyCode().equals("XYZ")));
//
//    }
//
//    @Test
//    public void testParseValueEndpoint() {
//        given()
//          .when()
//                .queryParam("v", "0.00 UPX")
//                .get("/parse-value")
//          .then()
//             .statusCode(200)
//             .body(is("[value=0.00, cur=UPX]"));
//    }

}