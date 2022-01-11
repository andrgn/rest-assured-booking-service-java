package com.herokuapp.restfulbooker.v1.deleteBooking;

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
import static utils.Steps.createBookingStep;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("deleteBooking: негативные кейсы")
class DeleteBookingNegativeTests {

    @ParameterizedTest
    @DisplayName("deleteBooking возвращает statusCode 405 если передали невалидный bookingId")
    @ValueSource(ints = { 0, -1 })
    void deleteBookingReturns405IfBookingIdIsInvalid(Integer invalidBookingId) {
        parameter("Невалидный bookingId", invalidBookingId);

        var actualStatusCode = step(
                String.format("Удаление бронирования по невалидному bookingId = %s", invalidBookingId),
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                        when().
                                delete(BOOKING_BY_ID, invalidBookingId).
                        then().
                                extract().statusCode()
        );

        step("statusCode в ответе эквивалентен 405", () ->
                assertThat(actualStatusCode).as("deleteBooking вернул неверный statusCode").isEqualTo(405)
        );
    }

    @ParameterizedTest
    @DisplayName("deleteBooking возвращает верное сообщение об ошибке если передали невалидный bookingId")
    @ValueSource(ints = { 0, -1 })
    void deleteBookingReturnsErrorMessageIfBookingIdIsInvalid(Integer invalidBookingId) {
        parameter("Невалидный bookingId", invalidBookingId);

        var actualErrorMessage = step(
                String.format("Удаление бронирования по невалидному bookingId = '%s'", invalidBookingId),
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                        when().
                                delete(BOOKING_BY_ID, invalidBookingId).
                        then().
                                extract().htmlPath().getString("html.body")
        );

        step(String.format("В ответе сообщение об ошибке = '%s'", METHOD_NOT_ALLOWED), () ->
                assertThat(actualErrorMessage).as("deleteBooking вернул неверное сообщение об ошибке").isEqualTo(METHOD_NOT_ALLOWED)
        );
    }

    @Test
    @DisplayName("deleteBooking возвращает statusCode 405 если передали несуществующий bookingId")
    void deleteBookingReturns405IfBookingIdIsNonexistent() {
        var nonExistentId = Integer.MAX_VALUE;

        var actualStatusCode = step(
                String.format("Удаление бронирования по несуществующему bookingId = %s", nonExistentId),
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                        when().
                                delete(BOOKING_BY_ID, nonExistentId).
                        then().
                                extract().statusCode()
        );

        step("statusCode ответа эквивалентен 405", ()->
                assertThat(actualStatusCode).as("deleteBooking вернул неверный statusCode").isEqualTo(405)
        );
    }

    @Test
    @DisplayName("deleteBooking возвращает верное сообщение об ошибке если передали несуществующий bookingId")
    void deleteBookingReturnsErrorMessageIfBookingIdIsNonexistent() {
        var nonExistentId = Integer.MAX_VALUE;

        var actualErrorMessage = step(
                String.format("Удаление бронирования по несуществующему bookingId = %s", nonExistentId),
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                        when().
                                delete(BOOKING_BY_ID, nonExistentId).
                        then().
                                extract().htmlPath().getString("html.body")
        );

        step(String.format("В ответе сообщение об ошибке = '%s'", METHOD_NOT_ALLOWED), () ->
                assertThat(actualErrorMessage).as("deleteBooking вернул неверное сообщение об ошибке").isEqualTo(METHOD_NOT_ALLOWED)
        );
    }

    @Test
    @DisplayName("deleteBooking возвращает statusCode 403 если пользователь не авторизован")
    void deleteBookingReturns403IfUserIsNotAuthorized() {
        var booking = Booking.Builder().build();
        var createBookingResponse = step("Создание нового бронирования", () -> createBookingStep(booking));
        var bookingId = createBookingResponse.getBookingId();

        var actualStatusCode = step(
                "Удаление созданного бронирования без авторизации",
                () ->
                        given().
                                contentType(JSON).
                        when().
                                delete(BOOKING_BY_ID, bookingId).
                        then().
                                extract().statusCode()
        );

        step("statusCode ответа эквивалентен 403", () ->
                assertThat(actualStatusCode).as("deleteBooking вернул неверный statusCode").isEqualTo(403)
        );
    }

    @Test
    @DisplayName("deleteBooking возвращает сообщение об ошибке если пользователь не авторизован")
    void deleteBookingReturnsErrorMessageIfUserIsNotAuthorized() {
        var booking = Booking.Builder().build();
        var createBookingResponse = step("Создание нового бронирования", () -> createBookingStep(booking));
        var bookingId = createBookingResponse.getBookingId();

        var actualErrorMessage = step(
                "Удаление созданного бронирования без авторизации",
                () ->
                        given().
                                contentType(JSON).
                        when().
                                delete(BOOKING_BY_ID, bookingId).
                        then().
                                extract().htmlPath().getString("html.body")
        );

        step(String.format("В ответе сообщение об ошибке = '%s'", FORBIDDEN), () ->
                assertThat(actualErrorMessage).as("deleteBooking вернул неверное сообщение об ошибке").isEqualTo(FORBIDDEN)
        );
    }
}
