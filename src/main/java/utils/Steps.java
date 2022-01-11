package utils;

import entities.Booking;
import entities.BookingDates;
import entities.BookingResponse;

import java.time.LocalDate;

import static endpoints.RestfulBookerEndpoint.BOOKING;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class Steps {

    public static BookingResponse createBookingStep(Booking booking) {
        return given().
                    contentType(JSON).
                    body(booking).
               when().
                    post(BOOKING).
               then().
                    extract().as(BookingResponse.class);
    }

    public static Booking createBookingDataWithCheckinStep(LocalDate checkin) {
        var bookingDates = BookingDates.Builder()
                .setCheckin(checkin)
                .build();

        return Booking.Builder()
                .setBookingDates(bookingDates)
                .build();
    }

    public static Booking createBookingDataWithCheckoutStep(LocalDate checkout) {
        var bookingDates = BookingDates.Builder()
                .setCheckout(checkout)
                .build();

        return Booking.Builder()
                .setBookingDates(bookingDates)
                .build();
    }
}
