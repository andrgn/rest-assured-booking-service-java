package com.herokuapp.restfulbooker.v1.createBooking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import extensions.RestAssuredExtension;
import entities.Booking;

import static endpoints.RestfulBookerEndpoint.BOOKING;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Steps.createBookingDataWithCheckinStep;
import static utils.Steps.createBookingDataWithCheckoutStep;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("createBooking: негативные кейсы")
class CreateBookingNegativeTests {

    @Test
    @DisplayName("createBooking возвращает 500 если передали null для firstname")
    void createBookingReturns500IfFirstnameIsNull() {
        var booking = Booking.Builder()
                .setFirstname(null)
                .build();

        var actualStatusCode = step("Создание бронирования с firstname = null", () ->
                given().
                        contentType(JSON).
                        body(booking).
                when().
                        post(BOOKING).
                then().
                        extract().statusCode()
        );

        step("statusCode ответа эквивалентен 500", () ->
                assertThat(actualStatusCode).as("createBooking вернул неверный statusCode").isEqualTo(500)
        );
    }

    @Test
    @DisplayName("createBooking возвращает 500 если передали null для lastname")
    void createBookingReturns500IfLastnameIsNull() {
        var booking = Booking.Builder()
                .setLastname(null)
                .build();

        var actualStatusCode = step("Создание бронирования с lastname = 500", () ->
                given().
                        contentType(JSON).
                        body(booking).
                when().
                        post(BOOKING).
                then().
                        extract().statusCode()
        );

        step("statusCode ответа эквивалентен 500", () ->
                assertThat(actualStatusCode).as("createBooking вернул неверный statusCode").isEqualTo(500)
        );
    }

    @Test
    @DisplayName("createBooking возвращает 500 если передали null для depositpaid")
    void createBookingReturns500IfDepositPaidIsNull() {
        var booking = Booking.Builder()
                .setDepositPaid(null)
                .build();

        var actualStatusCode = step("Создание бронирования с depositPaid = null", () ->
                given().
                        contentType(JSON).
                        body(booking).
                when().
                        post(BOOKING).
                then().
                        extract().statusCode()
        );

        step("statusCode ответа эквивалентен 500", () ->
                assertThat(actualStatusCode).as("createBooking вернул неверный statusCode").isEqualTo(500)
        );
    }

    @Test
    @DisplayName("createBooking возвращает 500 если передали null для bookingdates")
    void createBookingReturns500IfBookingDatesIsNull() {
        var booking = Booking.Builder()
                .setBookingDates(null)
                .build();

        var actualStatusCode = step("Создание бронирования с bookingDates = null", () ->
                given().
                        contentType(JSON).
                        body(booking).
                when().
                        post(BOOKING).
                then().
                        extract().statusCode()
        );

        step("statusCode ответа эквивалентен 500", () ->
                assertThat(actualStatusCode).as("createBooking вернул неверный statusCode").isEqualTo(500)
        );
    }

    @Test
    @DisplayName("createBooking возвращает 500 если передали null для bookingdates.checkin")
    void createBookingReturns500IfBookingDatesCheckinIsNull() {
        var booking = createBookingDataWithCheckinStep(null);

        var actualStatusCode = step("Создание бронирования с bookingdates.checkin = null", () ->
                given().
                        contentType(JSON).
                        body(booking).
                when().
                        post(BOOKING).
                then().
                        extract().statusCode()
        );

        step("statusCode ответа эквивалентен 500", () ->
                assertThat(actualStatusCode).as("createBooking вернул неверный statusCode").isEqualTo(500)
        );
    }

    @Test
    @DisplayName("createBooking возвращает 500 если передали null для bookingdates.checkout")
    void createBookingReturns500IfBookingDatesCheckoutIsNull() {
        var booking = createBookingDataWithCheckoutStep(null);

        var actualStatusCode = step("Создание бронирования с bookingdates.checkout = null", ()->
                given().
                        contentType(JSON).
                        body(booking).
                when().
                        post(BOOKING).
                then().
                        extract().statusCode()
        );

        step("statusCode ответа эквивалентен 500", () ->
                assertThat(actualStatusCode).as("createBooking вернул неверный statusCode").isEqualTo(500)
        );
    }
}
