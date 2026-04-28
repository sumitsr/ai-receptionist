package com.srivision.booking;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.result4j.Result;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AvailabilityServiceTest {

    @Test
    void shouldReturnEventIdWhenSlotIsAvailable() {
        GoogleCalendarClient mockClient = mock(GoogleCalendarClient.class);
        AvailabilityService service = new AvailabilityService(mockClient);
        LocalDateTime testTime = Instancio.create(LocalDateTime.class);
        String testDetails = Instancio.create(String.class);

        when(mockClient.checkSlot(testTime)).thenReturn(Result.success(true));
        when(mockClient.createEvent(testTime, testDetails)).thenReturn(Result.success("evt-123"));

        Result<String, BookingError> result = service.bookIfAvailable(testTime, testDetails);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.get()).isEqualTo("evt-123");
    }
}
