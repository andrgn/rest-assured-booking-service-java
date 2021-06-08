package com.herokuapp.restfulbooker.v1.getBooking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import extensions.RestAssuredExtension;
import entities.Booking;
import entities.BookingResponse;

import java.time.LocalDate;
import java.util.stream.Stream;

import static endpoints.RestfulBookerEndpoint.BOOKING;
import static endpoints.RestfulBookerEndpoint.BOOKING_BY_ID;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static utils.Steps.getDefaultBooking;
import static utils.Steps.postBooking;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("getBooking: позитивные кейсы")
class Tests_getBookingPositiveCases {

    @Test
    @DisplayName("getBooking соответствует схеме")
    void getBookingResponseMatchesToSchema() {
        var newBooking = getDefaultBooking();
        var createBookingResponse = postBooking(newBooking);

        when().
                get(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                body(matchesJsonSchemaInClasspath("schemas/getBookingSchema.json"));
    }

    @Test
    @DisplayName("getBooking возвращает 200")
    void getBookingReturns200() {
        var newBooking = getDefaultBooking();
        var createBookingResponse = postBooking(newBooking);

        when().
                get(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                statusCode(200);
    }

    @ParameterizedTest
    @DisplayName("getBooking возвращает верный firstname")
    @ValueSource(strings = { "Bill", "Дмитрий" })
    void getBookingReturnsCorrectFirstname(String firstname) {
        var newBooking = getDefaultBooking()
                .setFirstname(firstname);
        var createBookingResponse = postBooking(newBooking);

        Booking response = when().
                get(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                extract().as(Booking.class);

        assertThat("getBooking вернул неверный firstname",
                response.getFirstname(),
                equalTo(newBooking.getFirstname()));
    }

    @ParameterizedTest
    @DisplayName("getBooking возвращает верный lastname")
    @ValueSource(strings = { "Jackson", "Петров" })
    void getBookingReturnsCorrectLastname(String lastname) {
        var newBooking = getDefaultBooking()
                .setLastname(lastname);
        var createBookingResponse = postBooking(newBooking);

        Booking response = when().
                get(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                extract().as(Booking.class);

        assertThat("getBooking вернул неверный lastname",
                response.getLastname(),
                equalTo(newBooking.getLastname()));
    }

    @ParameterizedTest
    @DisplayName("getBooking возвращает верный additionalneeds")
    @ValueSource(strings = {
            "Breakfast",
            "Breakfast, brunch, lunch, dinner, tea, supper",
            "Завтрак",
            "Завтрак, обед, ужин"
    })
    void getBookingReturnsCorrectAdditionalNeeds(String additionalNeeds) {
        var newBooking = getDefaultBooking()
                .setAdditionalNeeds(additionalNeeds);
        var createBookingResponse = postBooking(newBooking);

        var response = when().
                get(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                extract().as(Booking.class);

        assertThat("getBooking вернул неверный additionalneeds",
                response.getAdditionalNeeds(),
                equalTo(newBooking.getAdditionalNeeds()));
    }

    @ParameterizedTest
    @DisplayName("getBooking возвращает верный totalprice")
    @ValueSource(ints = { 0, 5, 100, 5_000_000 })
    void getBookingReturnsCorrectTotalPrice(Integer totalPrice) {
        var newBooking = getDefaultBooking()
                .setTotalPrice(totalPrice);
        var createBookingResponse = postBooking(newBooking);

        var response = when().
                get(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                extract().as(Booking.class);

        assertThat("getBooking вернул неверный totalprice",
                response.getTotalPrice(),
                equalTo(newBooking.getTotalPrice()));
    }

    @ParameterizedTest
    @DisplayName("getBooking возвращает верный depositpaid")
    @ValueSource(booleans = { true, false })
    void getBookingReturnsCorrectDepositPaid(Boolean depositPaid) {
        var newBooking = getDefaultBooking()
                .setDepositPaid(depositPaid);
        var createBookingResponse = postBooking(newBooking);

        var response = when().
                get(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                extract().as(Booking.class);

        assertThat("getBooking возвращает верный depositpaid",
                response.getTotalPrice(),
                equalTo(newBooking.getTotalPrice()));
    }

    static Stream<LocalDate> checkinProvider() {
        return Stream.of(
                LocalDate.parse("1937-01-01"),
                LocalDate.parse("2017-12-01")
        );
    }

    @ParameterizedTest
    @DisplayName("getBooking возвращает верный bookingdates.checkin")
    @MethodSource("checkinProvider")
    void getBookingReturnsCorrectCheckin(LocalDate checkin) {
        var newBooking = getDefaultBooking()
                .setBookingDates(new Booking.BookingDates()
                        .setCheckin(checkin)
                        .setCheckout(LocalDate.now()));
        var createBookingResponse = postBooking(newBooking);

        var response = when().
                get(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                extract().as(Booking.class);

        assertThat("getBooking вернул неверный bookingdates.checkin",
                response.getBookingDates().getCheckin(),
                equalTo(newBooking.getBookingDates().getCheckin()));
    }

    static Stream<LocalDate> checkoutProvider() {
        return Stream.of(
                LocalDate.parse("2023-01-02"),
                LocalDate.parse("3021-12-15")
        );
    }

    @ParameterizedTest
    @DisplayName("getBooking возвращает верный bookingdates.checkout")
    @MethodSource("checkoutProvider")
    void getBookingReturnsCorrectCheckout(LocalDate checkout) {
        var newBooking = getDefaultBooking()
                .setBookingDates(new Booking.BookingDates()
                        .setCheckin(LocalDate.now())
                        .setCheckout(checkout));
        var createBookingResponse = postBooking(newBooking);

        var response = when().
                get(BOOKING_BY_ID, createBookingResponse.getBookingId()).
        then().
                extract().as(Booking.class);

        assertThat("getBooking вернул неверный bookingdates.checkout",
                response.getBookingDates().getCheckout(),
                equalTo(newBooking.getBookingDates().getCheckout()));
    }
}
