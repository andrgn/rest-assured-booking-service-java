package com.herokuapp.restfulbooker.v1.deleteBooking;

import entities.Booking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import extensions.RestAssuredExtension;

import static credentials.AdminCredentials.*;
import static endpoints.RestfulBookerEndpoint.BOOKING_BY_ID;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Steps.createBookingStep;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("deleteBooking: позитивные кейсы")
class DeleteBookingPositiveTests {

    @Test
    @DisplayName("deleteBooking возвращает 201")
    void deleteBookingReturns201() {
        var booking = Booking.Builder().build();
        var createBookingResponse = step("Создание нового бронирования", () -> createBookingStep(booking));
        var bookingId = createBookingResponse.getBookingId();

        var actualStatusCode = step(
                "Удаление созданного бронирования",
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                        when().
                                delete(BOOKING_BY_ID, bookingId).
                        then().
                                extract().statusCode()
        );

        step("statusCode в ответе эквивалентен 201", () ->
                assertThat(actualStatusCode).as("deleteBooking вернул неверный statusCode").isEqualTo(201)
        );
    }
}
