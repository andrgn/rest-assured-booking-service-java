package entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class BookingResponse {
    @JsonProperty("bookingid")
    @Getter
    Integer bookingId;

    @JsonProperty("booking")
    @Getter
    Booking booking;
}
