package com.herokuapp.restfulbooker.v1.createBooking;

import entities.Booking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import extensions.RestAssuredExtension;
import entities.BookingResponse;

import java.time.LocalDate;
import java.util.stream.Stream;

import static endpoints.RestfulBookerEndpoint.BOOKING;
import static io.qameta.allure.Allure.parameter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Steps.createBookingDataWithCheckinStep;
import static utils.Steps.createBookingDataWithCheckoutStep;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("createBooking: позитивные кейсы")
class CreateBookingPositiveTests {

    @Test
    @DisplayName("createBooking возвращает statusCode 200")
    void createBookingReturns200() {
        var booking = Booking.Builder().build();

        var actualStatusCode = step("Создание бронирования с валидными параметрами", () ->
                given().
                        contentType(JSON).
                        body(booking).
                when().
                        post(BOOKING).
                then().
                        extract().statusCode()
        );

        step("statusCode ответа эквивалентен 200", () ->
                assertThat(actualStatusCode).as("createBooking вернул неверный statusCode").isEqualTo(200)
        );
    }

    @ParameterizedTest
    @DisplayName("createBooking возвращает верный firstname")
    @ValueSource(strings = { "Bill", "Дмитрий" })
    void createBookingReturnsCorrectFirstname(String expectedFirstname) {
        parameter("firstname", expectedFirstname);
        var booking = Booking.Builder()
                .setFirstname(expectedFirstname)
                .build();

        var createBookingResponse = step(
                String.format("Создание бронирования с firstname = '%s'", expectedFirstname),
                () ->
                        given().
                                contentType(JSON).
                                body(booking).
                        when().
                                post(BOOKING).
                        then().
                                extract().as(BookingResponse.class)
        );
        var actualFirstName = createBookingResponse.getBooking().getFirstname();

        step(String.format("В ответе firstname = '%s'", expectedFirstname), () ->
                assertThat(actualFirstName).as("createBooking вернул неверный firstname").isEqualTo(expectedFirstname)
        );
    }

    @ParameterizedTest
    @DisplayName("createBooking возвращает верный lastname")
    @ValueSource(strings = { "Jackson", "Петров" })
    void createBookingReturnsCorrectLastname(String expectedLastname) {
        parameter("lastname", expectedLastname);
        var booking = Booking.Builder()
                .setLastname(expectedLastname)
                .build();

        var createBookingResponse = step(
                String.format("Создание бронирования c lastname = '%s'", expectedLastname),
                () ->
                        given().
                                contentType(JSON).
                                body(booking).
                        when().
                                post(BOOKING).
                        then().
                                extract().as(BookingResponse.class)
        );
        var actualLastname = createBookingResponse.getBooking().getLastname();

        step(String.format("В ответе lastname = '%s'", expectedLastname), () ->
                assertThat(actualLastname).as("createBooking вернул неверный lastname").isEqualTo(expectedLastname)
        );
    }

    @ParameterizedTest
    @DisplayName("createBooking возвращает верный additionalneeds")
    @ValueSource(strings = {
            "Breakfast",
            "Breakfast, lunch, dinner",
            "Завтрак",
            "Завтрак, обед, ужин"
    })
    void createBookingReturnsCorrectAdditionalNeeds(String expectedAdditionalNeeds) {
        parameter("additionalneeds", expectedAdditionalNeeds);
        var booking = Booking.Builder()
                .setAdditionalNeeds(expectedAdditionalNeeds)
                .build();

        var createBookingResponse = step(
                String.format("Создание бронирования c additionalneeds = '%s'", expectedAdditionalNeeds),
                () ->
                        given().
                                contentType(JSON).
                                body(booking).
                        when().
                                post(BOOKING).
                        then().
                                extract().as(BookingResponse.class)
        );
        var actualAdditionalNeeds = createBookingResponse.getBooking().getAdditionalNeeds();

        step(String.format("В ответе additionalneeds = '%s'", expectedAdditionalNeeds), () ->
                assertThat(actualAdditionalNeeds).as("createBooking вернул неверный additionalneeds").isEqualTo(expectedAdditionalNeeds)
        );
    }

    @ParameterizedTest
    @DisplayName("createBooking возвращает верный totalprice")
    @ValueSource(ints = { 0, 5, 100, 5_000_000 })
    void createBookingReturnsCorrectTotalPrice(Integer expectedTotalPrice) {
        parameter("totalprice", expectedTotalPrice);
        var booking = Booking.Builder()
                .setTotalPrice(expectedTotalPrice)
                .build();

        var createBookingResponse = step(
                String.format("Создание бронирования с totalprice = %d", expectedTotalPrice),
                () ->
                        given().
                                contentType(JSON).
                                body(booking).
                        when().
                                post(BOOKING).
                        then().
                                extract().as(BookingResponse.class)
        );
        var actualTotalPrice = createBookingResponse.getBooking().getTotalPrice();

        step(String.format("В ответе totalprice = %d", expectedTotalPrice), () ->
                assertThat(actualTotalPrice).as("createBooking вернул неверный totalprice").isEqualTo(expectedTotalPrice)
        );
    }

    @ParameterizedTest
    @DisplayName("createBooking возвращает верный depositpaid")
    @ValueSource(booleans = { true, false })
    void createBookingReturnsCorrectDepositPaid(Boolean expectedDepositPaid) {
        parameter("depositpaid", expectedDepositPaid);
        var booking = Booking.Builder()
                .setDepositPaid(expectedDepositPaid)
                .build();

        var createBookingResponse = step(
                String.format("Создание бронирования с depositpaid = %s", expectedDepositPaid),
                () ->
                        given().
                                contentType(JSON).
                                body(booking).
                        when().
                                post(BOOKING).
                        then().
                                extract().as(BookingResponse.class)
        );
        var actualDepositPaid = createBookingResponse.getBooking().getDepositPaid();

        step(String.format("В ответе depositpaid = %s", expectedDepositPaid), () ->
                assertThat(actualDepositPaid).as("createBooking вернул неверный depositpaid").isEqualTo(expectedDepositPaid)
        );
    }

    @ParameterizedTest
    @DisplayName("createBooking возвращает верный bookingdates.checkin")
    @MethodSource("checkinProvider")
    void createBookingReturnsCorrectCheckin(LocalDate expectedCheckin) {
        parameter("checkin", expectedCheckin);
        var booking = createBookingDataWithCheckinStep(expectedCheckin);

        var createBookingResponse = step(
                String.format("Создание бронирования с bookingdates.checkin = '%s'", expectedCheckin),
                () ->
                        given().
                                contentType(JSON).
                                body(booking).
                        when().
                                post(BOOKING).
                        then().
                                extract().as(BookingResponse.class)
        );
        var actualCheckin = createBookingResponse.getBooking().getBookingDates().getCheckin();

        step(String.format("В ответе bookingdates.checkin = '%s'", expectedCheckin), () ->
                assertThat(actualCheckin).as("createBooking вернул неверный bookingdates.checkin").isEqualTo(expectedCheckin)
        );
    }

    static Stream<LocalDate> checkinProvider() {
        return Stream.of(
                LocalDate.now(),
                LocalDate.now().minusDays(1),
                LocalDate.now().minusMonths(1),
                LocalDate.now().minusYears(1),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusMonths(1),
                LocalDate.now().plusYears(1)
        );
    }

    @ParameterizedTest
    @DisplayName("createBooking возвращает верный bookingdates.checkout")
    @MethodSource("checkoutProvider")
    void createBookingReturnsCorrectCheckout(LocalDate expectedCheckout) {
        parameter("checkout", expectedCheckout);
        var booking = createBookingDataWithCheckoutStep(expectedCheckout);

        var createBookingResponse = step(
                String.format("Создание бронирования с bookingdates.checkout = '%s'", expectedCheckout),
                () ->
                        given().
                                contentType(JSON).
                                body(booking).
                        when().
                                post(BOOKING).
                        then().
                                extract().as(BookingResponse.class)
        );
        var actualCheckout = createBookingResponse.getBooking().getBookingDates().getCheckout();

        step(String.format("В ответе bookingdates.checkout = '%s'", expectedCheckout), () ->
                assertThat(actualCheckout).as("createBooking вернул неверный checkout").isEqualTo(expectedCheckout)
        );
    }

    static Stream<LocalDate> checkoutProvider() {
        return Stream.of(
                LocalDate.now(),
                LocalDate.now().minusDays(1),
                LocalDate.now().minusMonths(1),
                LocalDate.now().minusYears(1),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusMonths(1),
                LocalDate.now().plusYears(1)
        );
    }
}
