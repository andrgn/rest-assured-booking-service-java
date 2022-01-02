package com.herokuapp.restfulbooker.v1.updateBooking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import error_messages.ResponseErrorMessage;
import extensions.RestAssuredExtension;

import static credentials.AdminCredentials.*;
import static endpoints.RestfulBookerEndpoint.BOOKING_BY_ID;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static utils.Steps.getDefaultBooking;
import static utils.Steps.postBooking;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("updateBooking: негативные кейсы")
class UpdateBookingNegativeTests {

    @ParameterizedTest
    @DisplayName("updateBooking возвращает 405 если передали невалидный bookingId")
    @ValueSource(ints = { 0, -1 })
    void updateBookingReturns405IfBookingIdIsInvalid(Integer bookingId) {
        var updatedBooking = getDefaultBooking();

        given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                body(updatedBooking).
        when().
                put(BOOKING_BY_ID, bookingId).
        then().
                statusCode(405);
    }

    @ParameterizedTest
    @DisplayName("updatedBooking возвращает сообщение об ошибке если передали невалидный bookingId")
    @ValueSource(ints = { 0, -1 })
    void updateBookingReturnsErrorMessageIfBookingIdIsInvalid(Integer bookingId) {
        var updatedBooking = getDefaultBooking();

        given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                body(updatedBooking).
        when().
                put(BOOKING_BY_ID, bookingId).
        then().
                body(equalTo(ResponseErrorMessage.METHOD_NOT_ALLOWED));
    }

    @Test
    @DisplayName("updateBooking возвращает 405 если передали несуществующий bookingId")
    void updateBookingReturns405IfBookingIsNonexistent() {
        var updateBooking = getDefaultBooking();

        given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                body(updateBooking).
        when().
                put(BOOKING_BY_ID, Integer.MAX_VALUE).
        then().
                statusCode(405);
    }

    @Test
    @DisplayName("updatedBooking возвращает сообщение об ошибке если передали несуществующий bookingId")
    void updatedBookingReturnsErrorMessageIfBookingIdIsNonexistent() {
        var updatedBooking = getDefaultBooking();

        given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                body(updatedBooking).
        when().
                put(BOOKING_BY_ID, Integer.MAX_VALUE).
        then().
                body(equalTo(ResponseErrorMessage.METHOD_NOT_ALLOWED));
    }

    @Test
    @DisplayName("updateBooking возвращает 403 если пользователь не авторизован")
    void updateBookingReturn403IfUserIsNotAuthorized() {
        var newBooking = getDefaultBooking()
                .setFirstname("Bill");
        var createBookingResponse = postBooking(newBooking);
        var updatedBooking = newBooking
                .setFirstname("John");

        given().
                contentType(JSON).
                body(updatedBooking).
        when().
                put(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                statusCode(403);
    }

    @Test
    @DisplayName("updatedBooking возвращает сообщение об ошибке если пользователь не авторизован")
    void updateBookingReturnsErrorMessageIfUserNonAuthorized() {
        var newBooking = getDefaultBooking()
                .setFirstname("Bill");
        var createBookingResponse = postBooking(newBooking);
        var updatedBooking = newBooking
                .setFirstname("John");

        given().
                contentType(JSON).
                body(updatedBooking).
        when().
                put(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                body(equalTo(ResponseErrorMessage.FORBIDDEN));
    }
}
