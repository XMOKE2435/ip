package xmoke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void markAndFileFormat_doneTask_success() {
        Task task = new Task("read book", Task.TaskType.T);
        task.markAsDone();

        assertTrue(task.isDone());
        assertEquals("[X]", task.getStatusIcon());
        assertEquals("T|1|read book", task.toFileFormat());
    }

    @Test
    public void toString_deadlineWithDateTime_formatsCorrectly() {
        LocalDateTime dt = LocalDateTime.of(2019, 12, 2, 18, 0);
        Task task = new Task("return book", Task.TaskType.D, dt);

        assertEquals("[D][ ] return book (by: Dec 2 2019, 6:00 PM)", task.toString());
        assertEquals("D|0|return book|2019-12-02T18:00", task.toFileFormat());
    }
}
