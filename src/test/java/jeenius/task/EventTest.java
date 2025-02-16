package jeenius.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    public void testConstructor() {
        Event event = new Event("test", "11/2/2025 0000", "11/2/2025 2359");
        assertEquals("test", event.getDescription());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        assertEquals("11/2/2025 0000", event.getFrom());
        assertEquals("11/2/2025 2359", event.getTo());
    }

    @Test
    public void testGetFrom() {
        Event event = new Event("test", "11/2/2025 0000", "11/2/2025 2359");
        assertEquals("11/2/2025 0000", event.getFrom());
    }

    @Test
    public void testGetTo() {
        Event event = new Event("test", "11/2/2025 0000", "11/2/2025 2359");
        assertEquals("11/2/2025 2359", event.getTo());
    }

    @Test
    public void testToFileFormat() {
        Event event = new Event("test", "11/2/2025 0000", "11/2/2025 2359");
        event.mark();
        assertEquals("E | 1 | test | 11/2/2025 0000 | 11/2/2025 2359", event.toFileFormat());
    }
}


