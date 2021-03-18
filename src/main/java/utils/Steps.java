package utils;

import entities.Booking;
import entities.BookingResponse;

import java.time.LocalDate;

import static endpoints.RestfulBookerEndpoint.BOOKING;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class Steps {

    public static Booking getDefaultBooking() {
        return new Booking()
                .setFirstname("Bill")
                .setLastname("Jackson")
                .setAdditionalNeeds("Breakfast")
                .setTotalPrice(123)
                .setDepositPaid(true)
                .setBookingDates(new Booking.BookingDates()
                        .setCheckin(LocalDate.now())
                        .setCheckout(LocalDate.now()));
    }

    public static BookingResponse postBooking(Booking booking) {
        return given().
                contentType(JSON).
                body(booking).
               when().
                post(BOOKING).
               then().
                extract().as(BookingResponse.class);
    }
}
