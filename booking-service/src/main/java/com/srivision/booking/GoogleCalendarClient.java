package com.srivision.booking;

import org.result4j.Result;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class GoogleCalendarClient {

    public Result<Boolean, BookingError> checkSlot(LocalDateTime time) {
        // Stub: Functional check against Calendar API bounds
        return time != null ? Result.success(true) : Result.failure(BookingError.INVALID_REQUEST);
    }

    public Result<String, BookingError> createEvent(LocalDateTime time, String details) {
        // Stub: Functional event creation returning an event ID
        return Result.success("evt-stub-12345");
    }
}
