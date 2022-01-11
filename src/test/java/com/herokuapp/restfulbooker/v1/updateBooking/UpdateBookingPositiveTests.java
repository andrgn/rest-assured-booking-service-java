package com.herokuapp.restfulbooker.v1.updateBooking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import extensions.RestAssuredExtension;
import entities.Booking;

import java.time.LocalDate;
import java.util.stream.Stream;

import static credentials.AdminCredentials.*;
import static endpoints.RestfulBookerEndpoint.BOOKING_BY_ID;
import static io.qameta.allure.Allure.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static utils.Steps.*;

@ExtendWith(RestAssuredExtension.class)
@DisplayName("updateBooking: позитивные кейсы")
class UpdateBookingPositiveTests {

    @Test
    @DisplayName("updateBooking возвращает statusCode 200")
    void updateBookingReturns200() {
        var booking = Booking.Builder()
                .setFirstname("Bill")
                .build();
        var createBookingResponse = step(
                "Создание нового бронирования с firstname = 'Bill'",
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();
        var updatedBooking = Booking.Builder()
                .setFirstname("John")
                .build();

        var actualStatusCode = step("Обновление созданного бронирования с новым firstname = 'John'", () ->
                given().
                        contentType(JSON).
                        auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                        body(updatedBooking).
                when().
                        put(BOOKING_BY_ID, bookingId).
                then().
                        extract().statusCode()
        );

        step("statusCode ответа эквивалентен 200", () ->
                assertThat(actualStatusCode).as("updateBooking вернул неверный statusCode").isEqualTo(200)
        );
    }

    @ParameterizedTest
    @DisplayName("updateBooking возвращает обновленный firstname")
    @CsvSource({
            "Bill, John",
            "Bill, Дмитрий"
    })
    void updateBookingReturnsUpdatedFirstname(String oldFirstname, String expectedFirstname) {
        parameter("старый firstname", oldFirstname);
        parameter("новый firstname", expectedFirstname);

        var booking = Booking.Builder()
                .setFirstname(oldFirstname)
                .build();
        var createBookingResponse = step(
                String.format("Создание нового бронирования с firstname = '%s'", oldFirstname),
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();
        var updatedBooking = Booking.Builder()
                .setFirstname(expectedFirstname)
                .build();

        var updateBookingResponse = step(
                String.format("Обновление созданного бронирования с новым firstname = '%s'", expectedFirstname),
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                                body(updatedBooking).
                        when().
                                put(BOOKING_BY_ID, bookingId).
                        then().
                                extract().as(Booking.class)
        );
        var actualFirstname = updateBookingResponse.getFirstname();

        step(String.format("В ответе firstname = '%s'", expectedFirstname), () ->
                assertThat(actualFirstname).as("updateBooking вернул неверный firstname").isEqualTo(expectedFirstname)
        );
    }

    @ParameterizedTest
    @DisplayName("updateBooking возвращает обновленный lastname")
    @CsvSource({
            "Jackson, Smith",
            "Jackson, Иванов"
    })
    void updateBookingReturnsUpdatedLastname(String oldLastname, String expectedLastname) {
        parameter("старый lastname", oldLastname);
        parameter("новый lastname", expectedLastname);

        var booking = Booking.Builder()
                .setLastname(oldLastname)
                .build();
        var createBookingResponse = step(
                String.format("Создание нового бронирования с lastname = '%s'", oldLastname),
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();
        var updatedBooking = Booking.Builder()
                .setLastname(expectedLastname)
                .build();

        var updateBookingResponse = step(
                String.format("Обновление созданного бронирования с новым lastname = '%s'", expectedLastname),
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                                body(updatedBooking).
                        when().
                                put(BOOKING_BY_ID, bookingId).
                        then().
                                extract().as(Booking.class)
        );
        var actualLastname = updateBookingResponse.getLastname();

        step(String.format("В ответе lastname = '%s'", expectedLastname), () ->
                assertThat(actualLastname).as("updateBooking вернул неверный lastname").isEqualTo(expectedLastname)
        );
    }

    @ParameterizedTest
    @DisplayName("updateBooking возвращает обновленный additionalneeds")
    @CsvSource({
            "'Old additional needs', Breakfast",
            "'Old additional needs', Завтрак",
            "'Old additional needs', 'Breakfast, lunch, dinner'",
            "'Old additional needs', 'Завтрак, обед, ужин'"
    })
    void updateBookingReturnsUpdatedAdditionalNeeds(String oldAdditionalNeeds, String expectedAdditionalNeeds) {
        parameter("старый additionalneeds", oldAdditionalNeeds);
        parameter("новый additionalneeds", expectedAdditionalNeeds);

        var booking = Booking.Builder()
                .setAdditionalNeeds(oldAdditionalNeeds)
                .build();
        var createBookingResponse = step(
                String.format("Создание нового бронирования с additionalneeds = '%s'", oldAdditionalNeeds),
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();
        var updatedBooking = Booking.Builder()
                .setAdditionalNeeds(expectedAdditionalNeeds)
                .build();

        var updateBookingResponse = step(
                String.format("Обновление созданного бронирования с новым additionalneeds = '%s'", expectedAdditionalNeeds),
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                                body(updatedBooking).
                        when().
                                put(BOOKING_BY_ID, bookingId).
                        then().
                                extract().as(Booking.class)
        );
        var actualAdditionalNeeds = updateBookingResponse.getAdditionalNeeds();

        step(String.format("В ответе additionalneeds = '%s'", expectedAdditionalNeeds), () ->
                assertThat(actualAdditionalNeeds).as("updateBooking вернул неверный additionalneeds").isEqualTo(expectedAdditionalNeeds)
        );
    }

    @ParameterizedTest
    @DisplayName("updateBooking возвращает обновленный totalprice")
    @CsvSource({
        "1_000, 0",
        "1_000, 5",
        "1_000, 100",
        "1_000, 5_000_000"
    })
    void updateBookingReturnsUpdatedTotalPrice(Integer oldTotalPrice, Integer expectedTotalPrice) {
        parameter("старый totalprice", oldTotalPrice);
        parameter("новый totalprice", expectedTotalPrice);

        var booking = Booking.Builder()
                .setTotalPrice(oldTotalPrice)
                .build();
        var createBookingResponse = step(
                String.format("Создание нового бронирования с totalprice = %d", oldTotalPrice),
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();
        var updatedBooking = Booking.Builder()
                .setTotalPrice(expectedTotalPrice)
                .build();

        var updateBookingResponse = step(
                String.format("Обновление созданного бронирования с новым totalprice = %d", expectedTotalPrice),
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                                body(updatedBooking).
                        when().
                                put(BOOKING_BY_ID, bookingId).
                        then().
                                extract().as(Booking.class)
        );
        var actualTotalPrice = updateBookingResponse.getTotalPrice();

        step(String.format("В ответе totalprice = %s", expectedTotalPrice), () ->
                assertThat(actualTotalPrice).as("updateBooking вернул неверный totalprice").isEqualTo(expectedTotalPrice)
        );
    }

    @ParameterizedTest
    @DisplayName("updateBooking возвращает обновленный depositpaid")
    @CsvSource({
            "true, false",
            "false, true"
    })
    void updateBookingReturnsUpdatedDepositPaid(Boolean oldDepositPaid, Boolean expectedDepositPaid) {
        parameter("старый depositpaid", oldDepositPaid);
        parameter("новый depositpaid", expectedDepositPaid);

        var booking = Booking.Builder()
                .setDepositPaid(oldDepositPaid)
                .build();
        var createBookingResponse = step(
                String.format("Создание нового бронирования с depositpaid = %s", oldDepositPaid),
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();
        var updatedBooking = Booking.Builder()
                .setDepositPaid(expectedDepositPaid)
                .build();

        var updateBookingResponse = step(
                String.format("Обновление созданного бронирования с новым depositpaid = %s", expectedDepositPaid),
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                                body(updatedBooking).
                        when().
                                put(BOOKING_BY_ID, bookingId).
                        then().
                                extract().as(Booking.class)
        );
        var actualDepositPaid = updateBookingResponse.getDepositPaid();

        step(String.format("В ответе depositpaid = %s", expectedDepositPaid), () ->
                assertThat(actualDepositPaid).as("updatedBooking вернул неверный depositpaid").isEqualTo(expectedDepositPaid)
        );
    }

    @ParameterizedTest
    @DisplayName("updateBooking возвращает обновленный bookingdates.checkin")
    @MethodSource("checkinProvider")
    void updateBookingReturnsUpdatedBookingDatesCheckin(LocalDate oldCheckin, LocalDate expectedCheckin) {
        parameter("старый checkin", oldCheckin);
        parameter("новый checkin", expectedCheckin);

        var booking = createBookingDataWithCheckinStep(oldCheckin);
        var createBookingResponse = step(
                String.format("Создание нового бронирования с booking.checkin = '%s'", oldCheckin),
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();
        var updatedBooking = createBookingDataWithCheckinStep(expectedCheckin);

        var updateBookingResponse = step(
                String.format("Обновление созданного бронирования с новым bookingdates.checkin = '%s'", expectedCheckin),
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                                body(updatedBooking).
                        when().
                                put(BOOKING_BY_ID, bookingId).
                        then().
                                extract().as(Booking.class)
        );
        var actualCheckin = updateBookingResponse.getBookingDates().getCheckin();

        step(String.format("В ответе bookingdates.checkin = '%s'", expectedCheckin), () ->
                assertThat(actualCheckin).as("updateBooking вернул неверный bookingdates.checkin").isEqualTo(expectedCheckin)
        );
    }

    static Stream<Arguments> checkinProvider() {
        return Stream.of(
                arguments(LocalDate.now(), LocalDate.now().plusDays(1)),
                arguments(LocalDate.now(), LocalDate.now().plusWeeks(1)),
                arguments(LocalDate.now(), LocalDate.now().plusMonths(1)),
                arguments(LocalDate.now(), LocalDate.now().plusYears(1))
        );
    }

    @ParameterizedTest
    @DisplayName("updateBooking возвращает обновленный bookingdates.checkout")
    @MethodSource("checkoutProvider")
    void updateBookingReturnsUpdatedBookingDatesCheckout(LocalDate oldCheckout, LocalDate expectedCheckout) {
        parameter("старый checkout", oldCheckout);
        parameter("новый checkout", expectedCheckout);

        var booking = createBookingDataWithCheckoutStep(oldCheckout);
        var createBookingResponse = step(
                String.format("Создание нового бронирования с bookingdates.checkout = '%s'", oldCheckout),
                () -> createBookingStep(booking)
        );
        var bookingId = createBookingResponse.getBookingId();
        var updatedBooking = createBookingDataWithCheckoutStep(expectedCheckout);

        var updateBookingResponse = step(
                String.format("Обновление созданного бронирования с новым bookingdates.checkout = '%s'", expectedCheckout),
                () ->
                        given().
                                contentType(JSON).
                                auth().preemptive().basic(ADMIN_LOGIN, ADMIN_PASSWORD).
                                body(updatedBooking).
                        when().
                                put(BOOKING_BY_ID, bookingId).
                        then().
                                extract().as(Booking.class)
        );
        var actualCheckout = updateBookingResponse.getBookingDates().getCheckout();

        step(String.format("В ответе bookingdates.checkout = '%s'", expectedCheckout), () ->
                assertThat(actualCheckout).as("updatedBooking вернул неверный bookingdates.checkout").isEqualTo(expectedCheckout)
        );
    }

    static Stream<Arguments> checkoutProvider() {
        return Stream.of(
                arguments(LocalDate.now(), LocalDate.now().plusDays(1)),
                arguments(LocalDate.now(), LocalDate.now().plusWeeks(1)),
                arguments(LocalDate.now(), LocalDate.now().plusMonths(1)),
                arguments(LocalDate.now(), LocalDate.now().plusYears(1))
        );
    }
}
