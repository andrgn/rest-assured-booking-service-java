package com.herokuapp.restfulbooker.v1.updateBooking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import extensions.RestAssuredExtension;
import entities.Booking;
import entities.BookingResponse;

import java.time.LocalDate;
import java.util.stream.Stream;

import static credentials.AdminCredentials.*;
import static endpoints.RestfulBookerEndpoint.BOOKING;
import static endpoints.RestfulBookerEndpoint.BOOKING_BY_ID;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static utils.Steps.getDefaultBooking;
import static utils.Steps.postBooking;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("updateBooking: позитивные кейсы")
class Tests_updateBookingPositiveCases {

    @Test
    @DisplayName("updateBooking соотвествует схеме")
    void updateBookingMatchesToSchema() {
        var newBooking = getDefaultBooking()
                .setFirstname("Bill");
        var createBookingResponse = postBooking(newBooking);
        var updatedBooking = newBooking
                .setFirstname("John");

        given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                body(updatedBooking).
        when().
                put(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                body(matchesJsonSchemaInClasspath("schemas/UpdateBookingSchema.json"));
    }

    @Test
    @DisplayName("updateBooking возвращает 200")
    void updateBookingReturns200() {
        var newBooking = getDefaultBooking()
                .setFirstname("Bill");
        var createBookingResponse = postBooking(newBooking);
        var updatedBooking = newBooking
                .setFirstname("John");

        given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                body(updatedBooking).
        when().
                put(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                statusCode(200);
    }

    @ParameterizedTest
    @DisplayName("updateBooking возращает обновленный firstname")
    @ValueSource(strings = { "John", "Дмитрий" })
    void updateBookingReturnsUpdatedFirstname(String newFirstname) {
        var newBooking = getDefaultBooking()
                .setFirstname("Bill");
        var createBookingResponse = postBooking(newBooking);
        var updatedBooking = newBooking
                .setFirstname(newFirstname);

        var response = given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                body(updatedBooking).
        when().
                put(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                extract().as(Booking.class);

        assertThat("updateBooking вернул неверный firstname",
                response.getFirstname(), equalTo(newFirstname));
    }

    @ParameterizedTest
    @DisplayName("updateBooking возвращает обновленный lastname")
    @ValueSource(strings = { "Smith", "Иванов" })
    void updateBookingReturnsUpdatedLastname(String newLastname) {
        var newBooking = getDefaultBooking()
                .setLastname("Jackson");
        var createBookingResponse = postBooking(newBooking);
        var updatedBooking = newBooking
                .setLastname(newLastname);

        var response = given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                body(updatedBooking).
        when().
                put(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                extract().as(Booking.class);

        assertThat("updateBooking вернул неверный lastname",
                response.getLastname(), equalTo(newLastname));
    }

    @ParameterizedTest
    @DisplayName("updateBooking возвращает обновленный additionalneeds")
    @ValueSource(strings = {
            "Breakfast",
            "Breakfast, brunch, lunch, dinner, tea, supper",
            "Завтрак",
            "Завтрак, обед, ужин"
    })
    void updateBookingReturnsUpdatedAdditionalNeeds(String newAdditionalNeeds) {
        var newBooking = getDefaultBooking()
                .setAdditionalNeeds("Old additional needs");
        var createBookingResponse = postBooking(newBooking);
        var updatedBooking = newBooking
                .setAdditionalNeeds(newAdditionalNeeds);

        var response = given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                body(updatedBooking).
        when().
                put(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                extract().as(Booking.class);

        assertThat("updateBooking вернул неверный additionalneeds",
                response.getAdditionalNeeds(), equalTo(newAdditionalNeeds));
    }

    @ParameterizedTest
    @DisplayName("updateBooking возвращает обновленный totalprice")
    @ValueSource(ints = { 0, 5, 100, 5_000_000 })
    void updateBookingReturnsUpdatedTotalPrice(Integer newTotalPrice) {
        var newBooking = getDefaultBooking()
                .setTotalPrice(123);
        var createBookingResponse = postBooking(newBooking);
        var updatedBooking = newBooking
                .setTotalPrice(newTotalPrice);

        var response = given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                body(updatedBooking).
        when().
                put(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                extract().as(Booking.class);

        assertThat("updateBooking вернул неверный totalprice",
                response.getTotalPrice(), equalTo(newTotalPrice));
    }

    @ParameterizedTest
    @DisplayName("updateBooking возвращает обновленный depositpaid")
    @CsvSource({
            "true, false",
            "false, true"
    })
    void updateBookingReturnsUpdatedDepositPaid(Boolean oldDepositPaid, Boolean newDepositPaid) {
        var newBooking = getDefaultBooking()
                .setDepositPaid(oldDepositPaid);
        var createBookingResponse = postBooking(newBooking);
        var updatedBooking = newBooking
                .setDepositPaid(newDepositPaid);

        var response = given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                body(updatedBooking).
        when().
                put(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                extract().as(Booking.class);

        assertThat("updatedBooking вернул неверный depositpaid",
                response.getDepositPaid(), equalTo(newDepositPaid));
    }

    static Stream<LocalDate> checkinProvider() {
        return Stream.of(
                LocalDate.parse("2021-01-05"),
                LocalDate.parse("2021-05-01"),
                LocalDate.parse("2022-01-01")
        );
    }

    @ParameterizedTest
    @DisplayName("updateBooking возвращает обновленый bookingdates.checkin")
    @MethodSource("checkinProvider")
    void updateBookingReturnsUpdatedBookingDatesCheckin(LocalDate newCheckin) {
        var newBooking = getDefaultBooking()
                .setBookingDates(new Booking.BookingDates()
                        .setCheckin(LocalDate.parse("2021-01-01"))
                        .setCheckout(LocalDate.now()));
        var createBookingResponse = postBooking(newBooking);
        var updatedBooking = newBooking
                .setBookingDates(newBooking.getBookingDates()
                        .setCheckin(newCheckin));

        var response = given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                body(updatedBooking).
        when().
                put(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                extract().as(Booking.class);

        assertThat("updateBooking вернул неверный bookingdates.checkin",
                response.getBookingDates().getCheckin(), equalTo(newCheckin));
    }

    static Stream<LocalDate> checkoutProvider() {
        return Stream.of(
                LocalDate.parse("2021-01-05"),
                LocalDate.parse("2021-05-01"),
                LocalDate.parse("2022-01-01")
        );
    }

    @ParameterizedTest
    @DisplayName("updateBooking возвращает обновленный bookingdates.checkout")
    @MethodSource("checkoutProvider")
    void updateBookingReturnsUpdatedBookingDatesCheckout(LocalDate newCheckout) {
        var newBooking = getDefaultBooking()
                .setBookingDates(new Booking.BookingDates()
                        .setCheckin(LocalDate.now())
                        .setCheckout(LocalDate.parse("2021-01-01")));
        var createBookingResponse = postBooking(newBooking);
        var updatedBooking = newBooking
                .setBookingDates(newBooking.getBookingDates()
                        .setCheckout(newCheckout));

        var response = given().
                contentType(JSON).
                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                body(updatedBooking).
        when().
                put(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                extract().as(Booking.class);

        assertThat("updatedBooking вернул неверный bookingdates.checkout",
                response.getBookingDates().getCheckout(), equalTo(newCheckout));
    }
}
