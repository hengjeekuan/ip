package jeenius.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {
    @Test
    public void testConstructor() {
        ToDo todo = new ToDo("test");
        assertEquals("test", todo.getDescription());
    }
}
