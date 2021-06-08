package com.herokuapp.restfulbooker.v1.deleteBooking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import error_messages.ResponseErrorMessage;
import extensions.RestAssuredExtension;
import entities.Booking;
import entities.BookingResponse;

import java.time.LocalDate;

import static credentials.AdminCredentials.*;
import static endpoints.RestfulBookerEndpoint.BOOKING;
import static endpoints.RestfulBookerEndpoint.BOOKING_BY_ID;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static utils.Steps.getDefaultBooking;
import static utils.Steps.postBooking;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("deleteBooking: негативные кейсы")
class Tests_deleteBookingNegativeCases {

    @ParameterizedTest
    @DisplayName("deleteBooking возвращает 405 если передали невалидный bookingId")
    @ValueSource(ints = { 0, -1 })
    void deleteBookingReturns405IfBookingIdIsInvalid(Integer bookingId) {
        given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
        when().
                delete(BOOKING_BY_ID, bookingId).
        then().
                statusCode(405);
    }

    @ParameterizedTest
    @DisplayName("deleteBooking возвращает сообщение об ошибке если передали невалидный bookingId")
    @ValueSource(ints = { 0, -1 })
    void deleteBookingReturnsErrorMessageIfBookingIdIsInvalid(Integer bookingId) {
        given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
        when().
                delete(BOOKING_BY_ID, bookingId).
        then().
                body(equalTo(ResponseErrorMessage.METHOD_NOT_ALLOWED));
    }

    @Test
    @DisplayName("deleteBooking возвращает 405 если передали несуществующий bookingId")
    void deleteBookingReturns405IfBookingIdIsNonexistent() {
        given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
        when().
                delete(BOOKING_BY_ID, Integer.MAX_VALUE).
        then().
                statusCode(405);
    }

    @Test
    @DisplayName("deleteBooking возвращает сообщение об ошибке если передали несуществующий bookingId")
    void deleteBookingReturnsErrorMessageIfBookingIdIsNonexistent() {
        given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
        when().
                delete(BOOKING_BY_ID, Integer.MAX_VALUE).
        then().
                body(equalTo(ResponseErrorMessage.METHOD_NOT_ALLOWED));
    }

    @Test
    @DisplayName("deleteBooking возвращает 403 если пользователь не авторизован")
    void deleteBookingReturns403IfUserIsNotAuthorized() {
        var newBooking = getDefaultBooking();
        var createBookingResponse = postBooking(newBooking);

        given().
                contentType(JSON).
        when().
                delete(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                statusCode(403);
    }

    @Test
    @DisplayName("deleteBooking возвращает сообщение об ошибке если пользователь не авторизован")
    void deleteBookingReturnsErrorMessageIfUserIsNotAuthorized() {
        var newBooking = getDefaultBooking();
        var createBookingResponse = postBooking(newBooking);

        given().
                contentType(JSON).
        when().
                delete(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                body(equalTo(ResponseErrorMessage.FORBIDDEN));
    }
}
