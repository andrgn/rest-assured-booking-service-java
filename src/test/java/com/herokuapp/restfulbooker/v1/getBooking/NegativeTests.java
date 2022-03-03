package com.herokuapp.restfulbooker.v1.getBooking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import extensions.RestAssuredExtension;

import static endpoints.RestfulBookerEndpoint.BOOKING_BY_ID;
import static error_messages.ResponseErrorMessage.NOT_FOUND;
import static io.qameta.allure.Allure.parameter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("getBooking: негативные кейсы")
class NegativeTests {

    @ParameterizedTest
    @DisplayName("getBooking возвращает верный statusCode, если передали невалидный bookingId")
    @ValueSource(ints = { 0, -1 })
    void getBookingReturns404IfBookingIdIsInvalid(Integer invalidBookingId) {
        parameter("невалидный bookingId", invalidBookingId);

        var actualStatusCode = step(
                String.format("Получение бронирования по невалидному bookingId = %d", invalidBookingId),
                () ->
                        when().
                                get(BOOKING_BY_ID, invalidBookingId).
                        then().
                                extract().statusCode()
        );

        step("statusCode ответа эквивалентен 404", () ->
                assertThat(actualStatusCode).as("getBooking вернул неверный statusCode").isEqualTo(404)
        );
    }

    @ParameterizedTest
    @DisplayName("getBooking возвращает верное сообщение об ошибке, если передали невалидный bookingId")
    @ValueSource(ints = { 0, -1 })
    void getBookingReturnsErrorMessageIfBookingIdIsInvalid(Integer invalidBookingId) {
        parameter("невалидный bookingId", invalidBookingId);

        var actualErrorMessage = step(
                String.format("Получение бронирования по невалидному bookingId = %d", invalidBookingId),
                () ->
                        when().
                                get(BOOKING_BY_ID, invalidBookingId).
                        then().
                                extract().htmlPath().getString("html.body")
        );

        step(String.format("В ответе сообщение об ошибке = '%s'", NOT_FOUND), () ->
                assertThat(actualErrorMessage).as("getBooking вернул неверное сообщение об ошибке").isEqualTo(NOT_FOUND)
        );
    }

    @Test
    @DisplayName("getBooking возвращает верный statusCode, если передали несуществующий bookingId")
    void getBookingReturns404IfBookingIdsIsNonexistent() {
        var nonExistentId = Integer.MAX_VALUE;

        var actualStatusCode = step(
                String.format("Получение бронирования по несуществующему bookingId = %d", nonExistentId),
                () ->
                        when().
                                get(BOOKING_BY_ID, nonExistentId).
                        then().
                                extract().statusCode()
        );

        step("statusCode ответа эквивалентен 404", () ->
                assertThat(actualStatusCode).as("getBooking вернул неверный statusCode").isEqualTo(404)
        );
    }

    @Test
    @DisplayName("getBooking возвращает верное сообщение об ошибке, если передали несуществующий bookingId")
    void getBookingReturnsErrorMessageIfBookingIdsIsNonexistent() {
        var nonExistentId = Integer.MAX_VALUE;

        var actualErrorMessage = step(
                String.format("Получение бронирования по несуществующему bookingId = %d", nonExistentId),
                () ->
                        when().
                                get(BOOKING_BY_ID, nonExistentId).
                        then().
                                extract().htmlPath().getString("html.body")
        );

        step(String.format("В ответе сообщение об ошибке = '%s'", NOT_FOUND), () ->
                assertThat(actualErrorMessage).as("getBooking вернул неверное сообщение об ошибке").isEqualTo(NOT_FOUND)
        );
    }
}
