package jeenius.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ToDoTest {
    @Test
    public void testConstructor() {
        ToDo todo = new ToDo("test");
        assertEquals("test", todo.getDescription());
    }
}
