package com.herokuapp.restfulbooker.v1.updateBooking;

import entities.Booking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import extensions.RestAssuredExtension;

import static credentials.AdminCredentials.*;
import static endpoints.RestfulBookerEndpoint.BOOKING_BY_ID;
import static error_messages.ResponseErrorMessage.FORBIDDEN;
import static error_messages.ResponseErrorMessage.METHOD_NOT_ALLOWED;
import static io.qameta.allure.Allure.parameter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Steps.*;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("updateBooking: негативные кейсы")
class UpdateBookingNegativeTests {

    @ParameterizedTest
    @DisplayName("updateBooking возвращает statusCode 405 если передали невалидный bookingId")
    @ValueSource(ints = { 0, -1 })
    void updateBookingReturns405IfBookingIdIsInvalid(Integer invalidBookingId) {
        parameter("невалидный bookingId", invalidBookingId);
        var updatedBooking = Booking.Builder().build();

        var actualStatusCode = step(
                String.format("Обновление бронирования по невалидному bookingId = %d", invalidBookingId),
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                                body(updatedBooking).
                        when().
                                put(BOOKING_BY_ID, invalidBookingId).
                        then().
                                extract().statusCode()
        );

        step("statusCode ответа эквивалентен 405", () ->
                assertThat(actualStatusCode).as("updateBooking вернул неверный statusCode").isEqualTo(405)
        );
    }

    @ParameterizedTest
    @DisplayName("updateBooking возвращает сообщение об ошибке если передали невалидный bookingId")
    @ValueSource(ints = { 0, -1 })
    void updateBookingReturnsErrorMessageIfBookingIdIsInvalid(Integer invalidBookingId) {
        parameter("невалидный bookingId", invalidBookingId);
        var updatedBooking = Booking.Builder().build();

        var actualErrorMessage = step(
                String.format("Обновление бронирования по невалидному bookingId = %d", invalidBookingId),
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                                body(updatedBooking).
                        when().
                                put(BOOKING_BY_ID, invalidBookingId).
                        then().
                                extract().htmlPath().getString("html.body")
        );

        step(String.format("В ответе сообщение об ошибке = '%s'", METHOD_NOT_ALLOWED), () ->
                assertThat(actualErrorMessage).as("updateBooking вернул неверное сообщение об ошибке").isEqualTo(METHOD_NOT_ALLOWED)
        );
    }

    @Test
    @DisplayName("updateBooking возвращает statusCode 405 если передали несуществующий bookingId")
    void updateBookingReturns405IfBookingIsNonexistent() {
        var nonExistentId = Integer.MAX_VALUE;
        var updateBooking = Booking.Builder().build();

        var actualStatusCode = step(
                String.format("Обновление бронирования по несуществующему bookingId = %d", nonExistentId),
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                                body(updateBooking).
                        when().
                                put(BOOKING_BY_ID, nonExistentId).
                        then().
                                extract().statusCode()
        );

        step("statusCode ответа эквивалентен 405", () ->
                assertThat(actualStatusCode).as("updateBooking вернул неверный statusCode").isEqualTo(405)
        );
    }

    @Test
    @DisplayName("updateBooking возвращает сообщение об ошибке если передали несуществующий bookingId")
    void updatedBookingReturnsErrorMessageIfBookingIdIsNonexistent() {
        var nonExistentId = Integer.MAX_VALUE;
        var updatedBooking = Booking.Builder().build();

        var actualErrorMessage = step(
                String.format("Обновление бронирования по несуществующему bookingId = %d", nonExistentId),
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                                body(updatedBooking).
                        when().
                                put(BOOKING_BY_ID, nonExistentId).
                        then().
                                extract().htmlPath().getString("html.body")
        );

        step(String.format("В ответе сообщение об ошибке = '%s'", METHOD_NOT_ALLOWED), () ->
                assertThat(actualErrorMessage).as("updateBooking вернул неверное сообщение об ошибке").isEqualTo(METHOD_NOT_ALLOWED)
        );
    }

    @Test
    @DisplayName("updateBooking возвращает statusCode 403 если пользователь не авторизован")
    void updateBookingReturn403IfUserIsNotAuthorized() {
        var booking = Booking.Builder()
                .setFirstname("Bill")
                .build();
        var createBookingResponse = step(
                "Создание нового бронирования с firstname = 'Bill'",
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();
        var updatedBooking = Booking.Builder()
                .setFirstname("John")
                .build();

        var actualStatusCode = step(
                "Обновление созданного бронирования с новым firstname = 'John' и без авторизации",
                () ->
                        given().
                                contentType(JSON).
                                body(updatedBooking).
                        when().
                                put(BOOKING_BY_ID, bookingId).
                        then().
                                extract().statusCode()
        );

        step("statusCode ответа эквивалентен 403", () ->
                assertThat(actualStatusCode).as("updateBooking вернул неверный statusCode").isEqualTo(403)
        );
    }

    @Test
    @DisplayName("updateBooking возвращает сообщение об ошибке если пользователь не авторизован")
    void updateBookingReturnsErrorMessageIfUserNonAuthorized() {
        var booking = Booking.Builder()
                .setFirstname("Bill")
                .build();
        var createBookingResponse = step(
                "Создание нового бронирования с firstname = 'Bill'",
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();
        var updatedBooking = Booking.Builder()
                .setFirstname("John")
                .build();

        var actualErrorMessage = step(
                "Обновление созданного бронирования с новым firstname = 'John' и без авторизации",
                () ->
                        given().
                                contentType(JSON).
                                body(updatedBooking).
                        when().
                                put(BOOKING_BY_ID, bookingId).
                        then().
                                extract().htmlPath().getString("html.body")
        );

        step(String.format("В ответе сообщение об ошибке = '%s'", FORBIDDEN), () ->
                assertThat(actualErrorMessage).as("updateBooking вернул неверное сообщение об ошибке").isEqualTo(FORBIDDEN)
        );
    }
}
