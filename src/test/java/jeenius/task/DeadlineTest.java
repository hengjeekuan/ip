package jeenius.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void testConstructor() {
        Deadline deadline = new Deadline("test", "11/2/2025 2359");
        assertEquals("test", deadline.getDescription());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        assertEquals("11/2/2025 2359", deadline.getBy());
    }

    @Test
    public void testGetBy() {
        Deadline deadline = new Deadline("test", "11/2/2025 2359");
        assertEquals("11/2/2025 2359", deadline.getBy());
    }

    @Test
    public void testToFileFormat() {
        Deadline deadline = new Deadline("test", "11/2/2025 2359");
        deadline.mark();
        assertEquals("D | 1 | test | 11/2/2025 2359", deadline.toFileFormat());
    }
}
