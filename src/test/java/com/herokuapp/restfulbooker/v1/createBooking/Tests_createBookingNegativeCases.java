package com.herokuapp.restfulbooker.v1.createBooking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import extensions.RestAssuredExtension;
import entities.Booking;

import java.time.LocalDate;

import static endpoints.RestfulBookerEndpoint.BOOKING;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static utils.Steps.getDefaultBooking;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("createBooking: негативные кейсы")
class Tests_createBookingNegativeCases {

    @Test
    @DisplayName("createBooking возвращает 500 если передали null для firstname")
    void createBookingReturns500IfFirstnameIsNull() {
        Booking newBooking = getDefaultBooking()
                .setFirstname(null);

        given().
                contentType(JSON).
                body(newBooking).
        when().
                post(BOOKING).
        then().
                statusCode(500);
    }

    @Test
    @DisplayName("createBooking возвращает 500 если передали null для lastname")
    void createBookingReturns500IfLastnameIsNull() {
        Booking newBooking = getDefaultBooking()
                .setLastname(null);

        given().
                contentType(JSON).
                body(newBooking).
        when().
                post(BOOKING).
        then().
                statusCode(500);
    }

    @Test
    @DisplayName("createBooking возвращает 500 если передали null для depositpaid")
    void createBookingReturns500IfDepositPaidIsNull() {
        Booking newBooking = getDefaultBooking()
                .setDepositPaid(null);

        given().
                contentType(JSON).
                body(newBooking).
        when().
                post(BOOKING).
        then().
                statusCode(500);
    }

    @Test
    @DisplayName("createBooking возвращает 500 если передали null для bookingdates")
    void createBookingReturns500IfBookingDatesIsNull() {
        Booking newBooking = getDefaultBooking()
                .setBookingDates(null);

        given().
                contentType(JSON).
                body(newBooking).
        when().
                post(BOOKING).
        then().
                statusCode(500);
    }

    @Test
    @DisplayName("createBooking возвращает 500 если передали null для bookingdates.checkin")
    void createBookingReturns500IfBookingDatesCheckinIsNull() {
        Booking newBooking = getDefaultBooking()
                .setBookingDates(new Booking.BookingDates()
                        .setCheckin(null)
                        .setCheckout(LocalDate.now()));

        given().
                contentType(JSON).
                body(newBooking).
        when().
                post(BOOKING).
        then().
                statusCode(500);
    }

    @Test
    @DisplayName("createBooking возвращает 500 если передали null для bookingdates.checkout")
    void createBookingReturns500IfBookingDatesCheckoutIsNull() {
        Booking newBooking = getDefaultBooking()
                .setBookingDates(new Booking.BookingDates()
                        .setCheckin(LocalDate.now())
                        .setCheckout(null));

        given().
                contentType(JSON).
                body(newBooking).
        when().
                post(BOOKING).
        then().
                statusCode(500);
    }
}
