package com.herokuapp.restfulbooker.v1.getBooking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import error_messages.ResponseErrorMessage;
import extensions.RestAssuredExtension;

import static endpoints.RestfulBookerEndpoint.BOOKING_BY_ID;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("getBooking: негативные кейсы")
class GetBookingNegativeTests {

    @ParameterizedTest
    @DisplayName("getBooking возвращает 404 если передали невалидный bookingId")
    @ValueSource(ints = { 0, -1 })
    void getBookingReturns404IfBookingIdIsInvalid(Integer invalidBookingId) {
        when().
                get(BOOKING_BY_ID, invalidBookingId).
        then().
                statusCode(404);
    }

    @ParameterizedTest
    @DisplayName("getBooking возвращает сообщение об ошибке если передали невалидный bookingId")
    @ValueSource(ints = { 0, -1 })
    void getBookingReturnsErrorMessageIfBookingIdIsInvalid(Integer invalidBookingId) {
        when().
                get(BOOKING_BY_ID, invalidBookingId).
        then().
                body(equalTo(ResponseErrorMessage.NOT_FOUND));
    }

    @Test
    @DisplayName("getBooking возвращает 404 если передали несуществующий bookingId")
    void getBookingReturns404IfBookingIdsIsNonexistent() {
        when().
                get(BOOKING_BY_ID, Integer.MAX_VALUE).
        then().
                statusCode(404);
    }

    @Test
    @DisplayName("getBooking возвращает сообщение об ошибке если передали несуществующий bookingId")
    void getBookingReturnsErrorMessageIfBookingIdsIsNonexistent() {
        when().
                get(BOOKING_BY_ID, Integer.MAX_VALUE).
        then().
                body(equalTo(ResponseErrorMessage.NOT_FOUND));
    }
}
