package com.herokuapp.restfulbooker.v1.createBooking;

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
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static utils.Steps.getDefaultBooking;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("createBooking: позитивные кейсы")
class Tests_createBookingPositiveCases {

    @Test
    @DisplayName("Ответ createBooking соответсвует схеме")
    void createBookingResponseMatchesToSchema() {
        Booking newBooking = getDefaultBooking();

        given().
                contentType(JSON).
                body(newBooking).
        when().
                post(BOOKING).
        then().
                body(matchesJsonSchemaInClasspath("schemas/CreateBookingSchema.json"));
    }

    @Test
    @DisplayName("createBooking возвращает 200")
    void createBookingReturns200() {
        Booking newBooking = getDefaultBooking();

        given().
                contentType(JSON).
                body(newBooking).
        when().
                post(BOOKING).
        then().
                statusCode(200);
    }

    @ParameterizedTest
    @DisplayName("createBooking возращает верный firstname")
    @ValueSource(strings = { "Bill", "Дмитрий" })
    void createBookingReturnsCorrectFirstname(String firstname) {
        Booking newBooking = getDefaultBooking()
                .setFirstname(firstname);

        BookingResponse response = given().
                contentType(JSON).
                body(newBooking).
        when().
                post(BOOKING).
        then().
                extract().as(BookingResponse.class);

        assertThat("createBooking вернул неверный firstname",
                response.getBooking().getFirstname(),
                equalTo(newBooking.getFirstname()));
    }

    @ParameterizedTest
    @DisplayName("createBooking возращает верный lastname")
    @ValueSource(strings = { "Jackson", "Петров" })
    void createBookingReturnsCorrectLastname(String lastname) {
        Booking newBooking = getDefaultBooking()
                .setLastname(lastname);

        BookingResponse response = given().
                contentType(JSON).
                body(newBooking).
        when().
                post(BOOKING).
        then().
                extract().as(BookingResponse.class);

        assertThat("createBooking вернул неверный lastname",
                response.getBooking().getLastname(),
                equalTo(newBooking.getLastname()));
    }

    @ParameterizedTest
    @DisplayName("createBooking возвращает верный additionalneeds")
    @ValueSource(strings = {
            "Breakfast",
            "Breakfast, brunch, lunch, dinner, tea, supper",
            "Завтрак",
            "Завтрак, обед, ужин"
    })
    void createBookingReturnsCorrectAdditionalNeeds(String additionalneeds) {
        Booking newBooking = getDefaultBooking()
                .setAdditionalNeeds(additionalneeds);

        BookingResponse response = given().
                contentType(JSON).
                body(newBooking).
        when().
                post(BOOKING).
        then().
                extract().as(BookingResponse.class);

        assertThat("createBooking вернул неверный addtionalneeds",
                response.getBooking().getAdditionalNeeds(),
                equalTo(newBooking.getAdditionalNeeds()));
    }

    @ParameterizedTest
    @DisplayName("createBooking возвращает верный totalprice")
    @ValueSource(ints = { 0, 5, 100, 5_000_000 })
    void createBookingReturnsCorrectTotalPrice(Integer totalPrice) {
        Booking newBooking = getDefaultBooking()
                .setTotalPrice(totalPrice);

        BookingResponse response = given().
                contentType(JSON).
                body(newBooking).
        when().
                post(BOOKING).
        then().
                extract().as(BookingResponse.class);

        assertThat("createBooking вернул неверный totalprice",
                response.getBooking().getTotalPrice(),
                equalTo(newBooking.getTotalPrice()));
    }

    @ParameterizedTest
    @DisplayName("createBooking возвращает верный depositpaid")
    @ValueSource(booleans = { true, false })
    void createBookingReturnsCorrectDepositPaid(Boolean depositPaid) {
        Booking newBooking = getDefaultBooking()
                .setDepositPaid(depositPaid);

        BookingResponse response = given().
                contentType(JSON).
                body(newBooking).
        when().
                post(BOOKING).
        then().
                extract().as(BookingResponse.class);

        assertThat("createBooking вернул неверный depositpaid",
                response.getBooking().getDepositPaid(),
                equalTo(newBooking.getDepositPaid()));
    }

    static Stream<LocalDate> checkinProvider() {
        return Stream.of(
                LocalDate.parse("1937-01-01"),
                LocalDate.parse("2017-12-01")
        );
    }

    @ParameterizedTest
    @DisplayName("createBooking возвращает верный bookingdates.checkin")
    @MethodSource("checkinProvider")
    void createBookingReturnsCorrectCheckin(LocalDate checkin) {
        Booking newBooking = getDefaultBooking()
                .setBookingDates(new Booking.BookingDates()
                        .setCheckin(checkin)
                        .setCheckout(LocalDate.now()));

        BookingResponse response = given().
                contentType(JSON).
                body(newBooking).
        when().
                post(BOOKING).
        then().
                extract().as(BookingResponse.class);

        assertThat("createBooking вернул неверный checkin",
                response.getBooking().getBookingDates().getCheckin(),
                equalTo(newBooking.getBookingDates().getCheckin()));
    }

    static Stream<LocalDate> checkoutProvider() {
      return Stream.of(
              LocalDate.parse("2023-01-02"),
              LocalDate.parse("3021-12-15")
      );
    }

    @ParameterizedTest
    @DisplayName("createBooking возвращает верный bookingdates.checkout")
    @MethodSource("checkoutProvider")
    void createBookingReturnsCorrectCheckout(LocalDate checkout) {
        Booking newBooking = getDefaultBooking()
                .setBookingDates(new Booking.BookingDates()
                        .setCheckin(LocalDate.now())
                        .setCheckout(checkout));

        BookingResponse response = given().
                contentType(JSON).
                body(newBooking).
        when().
                post(BOOKING).
        then().
                extract().as(BookingResponse.class);

        assertThat("createBooking вернул неверный checkout",
                response.getBooking().getBookingDates().getCheckout(),
                equalTo(newBooking.getBookingDates().getCheckout()));
    }
}
