package com.herokuapp.restfulbooker.v1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import extensions.RestAssuredExtension;

import static endpoints.RestfulBookerEndpoint.PING;
import static io.restassured.RestAssured.*;

@ExtendWith(RestAssuredExtension.class)
class HealthCheckTests {

    @Test
    @DisplayName("ping возвращает 201")
    void pingReturns201() {
        when().
                get(PING).
        then().
                statusCode(201);
    }
}
