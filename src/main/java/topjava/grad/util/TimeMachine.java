package topjava.grad.util;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Component
public class TimeMachine {
    private Clock clock;

    public TimeMachine() {
        this.clock = Clock.systemDefaultZone();
    }

    public void setFixedClockAt(String dateTime) {
        clock =  Clock.fixed(Instant.parse(dateTime), ZoneId.of("UTC"));
    }

    public void setDefaultClock() {
        clock = Clock.systemDefaultZone();
    }

    public Clock getClock() {
        return clock;
    }
}
