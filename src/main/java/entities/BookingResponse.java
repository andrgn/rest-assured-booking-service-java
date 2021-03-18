package entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BookingResponse {
    @JsonProperty("bookingid")
    Integer bookingId;
    Booking booking;
}
