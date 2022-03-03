package com.herokuapp.restfulbooker.v1.deleteBooking;

import entities.Booking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import extensions.RestAssuredExtension;

import static credentials.RestfulBookerCredentials.ADMIN;
import static endpoints.RestfulBookerEndpoint.BOOKING_BY_ID;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static specifications.RequestSpecifications.CONTENT_TYPE_AND_AUTH_SPEC;
import static utils.Steps.createBookingStep;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("deleteBooking: позитивные кейсы")
class PositiveTests {

    @Test
    @DisplayName("deleteBooking возвращает верный statusCode, если удалить существующее бронирование")
    void deleteBookingReturns201() {
        var booking = Booking.Builder().build();
        var createBookingResponse = step("Создание нового бронирования", () -> createBookingStep(booking));
        var bookingId = createBookingResponse.getBookingId();

        var actualStatusCode = step(
                "Удаление созданного бронирования",
                () ->
                        given().
                                spec(CONTENT_TYPE_AND_AUTH_SPEC).
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
