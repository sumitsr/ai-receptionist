package com.srivision.booking;

import org.result4j.Result;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AvailabilityService {

    private final GoogleCalendarClient calendarClient;

    public AvailabilityService(GoogleCalendarClient calendarClient) {
        this.calendarClient = calendarClient;
    }

    public Result<String, BookingError> bookIfAvailable(LocalDateTime time, String details) {
        return calendarClient.checkSlot(time)
                .flatMap(isFree -> processAvailability(isFree, time, details));
    }

    private Result<String, BookingError> processAvailability(Boolean isFree, LocalDateTime time, String details) {
        return Boolean.TRUE.equals(isFree) 
                ? calendarClient.createEvent(time, details) 
                : Result.failure(BookingError.SLOT_UNAVAILABLE);
    }
}
