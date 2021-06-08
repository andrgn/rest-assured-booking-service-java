package entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class Booking {

    String firstname;

    String lastname;

    @JsonProperty("totalprice")
    Integer totalPrice;

    @JsonProperty("depositpaid")
    Boolean depositPaid;

    @JsonProperty("additionalneeds")
    String additionalNeeds;

    @JsonProperty("bookingdates")
    BookingDates bookingDates;

    @Data
    @Accessors(chain = true)
    public static class BookingDates {
        LocalDate checkin;
        LocalDate checkout;
    }
}
