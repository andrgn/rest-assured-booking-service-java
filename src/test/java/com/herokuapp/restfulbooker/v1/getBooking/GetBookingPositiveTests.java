package com.herokuapp.restfulbooker.v1.getBooking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import extensions.RestAssuredExtension;
import entities.Booking;

import java.time.LocalDate;
import java.util.stream.Stream;

import static endpoints.RestfulBookerEndpoint.BOOKING_BY_ID;
import static io.qameta.allure.Allure.parameter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Steps.*;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("getBooking: позитивные кейсы")
class GetBookingPositiveTests {

    @Test
    @DisplayName("getBooking возвращает statusCode 200")
    void getBookingReturns200() {
        var booking = Booking.Builder().build();
        var createBookingResponse = step("Создание нового бронирования", () -> createBookingStep(booking));
        var bookingId = createBookingResponse.getBookingId();

        var actualStatusCode = step("Получение созданного бронирования", () ->
                when().
                        get(BOOKING_BY_ID, bookingId).
                then().
                        extract().statusCode()
        );

        step("statusCode ответа эквивалентен 200", () ->
                assertThat(actualStatusCode).as("getBooking вернул неверный statusCode").isEqualTo(200)
        );
    }

    @ParameterizedTest
    @DisplayName("getBooking возвращает верный firstname")
    @ValueSource(strings = { "Bill", "Дмитрий" })
    void getBookingReturnsCorrectFirstname(String expectedFirstName) {
        parameter("firstname", expectedFirstName);
        var booking = Booking.Builder()
                .setFirstname(expectedFirstName)
                .build();
        var createBookingResponse = step(
                String.format("Создание нового бронирования с firstname = '%s'", expectedFirstName),
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();

        var getBookingResponse = step("Получение созданного бронирования", () ->
                when().
                        get(BOOKING_BY_ID, bookingId).
                then().
                        extract().as(Booking.class)
        );
        var actualFirstname = getBookingResponse.getFirstname();

        step(String.format("В ответе firstname = '%s'", expectedFirstName), () ->
                assertThat(actualFirstname).as("getBooking вернул неверный firstname").isEqualTo(expectedFirstName)
        );
    }

    @ParameterizedTest
    @DisplayName("getBooking возвращает верный lastname")
    @ValueSource(strings = { "Jackson", "Петров" })
    void getBookingReturnsCorrectLastname(String expectedLastname) {
        parameter("lastname", expectedLastname);
        var booking = Booking.Builder()
                .setLastname(expectedLastname)
                .build();
        var createBookingResponse = step(
                String.format("Создание нового бронирования с lastname = '%s'", expectedLastname),
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();

        var getBookingResponse = step("Получение созданного бронирования", () ->
                when().
                        get(BOOKING_BY_ID, bookingId).
                then().
                        extract().as(Booking.class)
        );
        var actualLastname = getBookingResponse.getLastname();

        step(String.format("В ответе lastname = '%s'", expectedLastname), () ->
                assertThat(actualLastname).as("getBooking вернул неверный lastname").isEqualTo(expectedLastname)
        );
    }

    @ParameterizedTest
    @DisplayName("getBooking возвращает верный additionalneeds")
    @ValueSource(strings = {
            "Breakfast",
            "Breakfast, lunch, dinner",
            "Завтрак",
            "Завтрак, обед, ужин"
    })
    void getBookingReturnsCorrectAdditionalNeeds(String expectedAdditionalNeeds) {
        parameter("additionalNeeds", expectedAdditionalNeeds);
        var booking = Booking.Builder()
                .setAdditionalNeeds(expectedAdditionalNeeds)
                .build();
        var createBookingResponse = step(
                String.format("Создание нового бронирования с additionalneeds = '%s'", expectedAdditionalNeeds),
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();

        var getBookingResponse = step("Получение созданного бронирования", () ->
                when().
                        get(BOOKING_BY_ID, bookingId).
                then().
                        extract().as(Booking.class)
        );
        var actualAdditionalNeeds = getBookingResponse.getAdditionalNeeds();

        step(String.format("В ответе additionalneeds = '%s'", expectedAdditionalNeeds), () ->
                assertThat(actualAdditionalNeeds).as("getBooking вернул неверный additionalneeds").isEqualTo(expectedAdditionalNeeds)
        );
    }

    @ParameterizedTest
    @DisplayName("getBooking возвращает верный totalprice")
    @ValueSource(ints = { 0, 5, 100, 5_000_000 })
    void getBookingReturnsCorrectTotalPrice(Integer expectedTotalPrice) {
        parameter("totalPrice", expectedTotalPrice);
        var booking = Booking.Builder()
                .setTotalPrice(expectedTotalPrice)
                .build();
        var createBookingResponse = step(
                String.format("Создание нового бронирования с totalprice = %d", expectedTotalPrice),
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();

        var getBookingResponse = step("Получение созданного бронирования", () ->
                when().
                        get(BOOKING_BY_ID, bookingId).
                then().
                        extract().as(Booking.class)
        );
        var actualTotalPrice = getBookingResponse.getTotalPrice();

        step(String.format("В ответе totalprice = %s", expectedTotalPrice), () ->
                assertThat(actualTotalPrice).as("getBooking вернул неверный totalprice").isEqualTo(expectedTotalPrice)
        );
    }

    @ParameterizedTest
    @DisplayName("getBooking возвращает верный depositpaid")
    @ValueSource(booleans = { true, false })
    void getBookingReturnsCorrectDepositPaid(Boolean expectedDepositPaid) {
        parameter("depositPaid", expectedDepositPaid);
        var booking = Booking.Builder()
                .setDepositPaid(expectedDepositPaid)
                .build();
        var createBookingResponse = step(
                String.format("Создание нового бронирования с depositpaid = %s", expectedDepositPaid),
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();

        var getBookingResponse = step("Получение созданного бронирования", () ->
                when().
                        get(BOOKING_BY_ID, bookingId).
                then().
                        extract().as(Booking.class)
        );
        var actualDepositPaid = getBookingResponse.getDepositPaid();

        step(String.format("В ответе depositpaid = %s", expectedDepositPaid), () ->
                assertThat(actualDepositPaid).as("getBooking вернул неверный dapositpaid").isEqualTo(expectedDepositPaid)
        );
    }

    @ParameterizedTest
    @DisplayName("getBooking возвращает верный bookingdates.checkin")
    @MethodSource("checkinProvider")
    void getBookingReturnsCorrectCheckin(LocalDate expectedCheckin) {
        parameter("bookingdates.checkin", expectedCheckin);
        var booking = createBookingDataWithCheckinStep(expectedCheckin);
        var createBookingResponse = step(
                String.format("Создание нового бронирования с bookingdates.checkin = '%s'", expectedCheckin),
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();

        var getBookingResponse = step("Получение созданного бронирования", () ->
                when().
                        get(BOOKING_BY_ID, bookingId).
                then().
                        extract().as(Booking.class)
        );
        var actualCheckin = getBookingResponse.getBookingDates().getCheckin();

        step(String.format("В ответе bookingdates.checkin = '%s'", expectedCheckin), () ->
                assertThat(actualCheckin).as("getBooking вернул неверный bookingdates.checkin").isEqualTo(expectedCheckin)
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
    @DisplayName("getBooking возвращает верный bookingdates.checkout")
    @MethodSource("checkoutProvider")
    void getBookingReturnsCorrectCheckout(LocalDate expectedCheckout) {
        parameter("bookingdates.checkout", expectedCheckout);
        var booking = createBookingDataWithCheckoutStep(expectedCheckout);
        var createBookingResponse = step(
                String.format("Создание нового бронирования с bookingdates.checkout = '%s'", expectedCheckout),
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();

        var getBookingResponse = step("Получение созданного бронирования", () ->
                when().
                        get(BOOKING_BY_ID, bookingId).
                then().
                        extract().as(Booking.class)
        );
        var actualCheckout = getBookingResponse.getBookingDates().getCheckout();

        step(String.format("В ответе bookingdates.checkout = '%s'", expectedCheckout), () ->
                assertThat(actualCheckout).as("getBooking вернул неверный bookingdates.checkout").isEqualTo(expectedCheckout)
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
