package com.herokuapp.restfulbooker.v1.deleteBooking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import extensions.RestAssuredExtension;

import static credentials.AdminCredentials.*;
import static endpoints.RestfulBookerEndpoint.BOOKING_BY_ID;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static utils.Steps.getDefaultBooking;
import static utils.Steps.postBooking;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("deleteBooking: позитивные кейсы")
class DeleteBookingPositiveTests {

    @Test
    @DisplayName("deleteBooking возвращает 201")
    void deleteBookingReturns201() {
        var newBooking = getDefaultBooking();
        var createBookingResponse = postBooking(newBooking);

        given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
        when().
                delete(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then()
                .statusCode(201);
    }
}
