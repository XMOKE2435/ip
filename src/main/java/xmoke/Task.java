package xmoke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a task in the chatbot, including its type, description, completion status,
 * and optional date/time fields (deadline or event timing).
 */
public class Task {
    /** Task type: T = todo, D = deadline, E = event. */
    public enum TaskType {
        T, D, E
    }

    private String description;
    private boolean isDone;
    private TaskType type;
    private LocalDateTime dateTime; // For deadlines and events

    /** Creates a todo/deadline/event with no date/time. */
    public Task(String description, TaskType type) {
        assert description != null : "task description must not be null";
        this.description = description;
        this.type = type;
        this.isDone = false;
        this.dateTime = null;
    }

    /** Creates a task with given description, type and done status; no date/time. */
    public Task(String description, TaskType type, boolean isDone) {
        this.description = description;
        this.type = type;
        this.isDone = isDone;
        this.dateTime = null;
    }

    /** Creates a task with date/time (deadline or event). */
    public Task(String description, TaskType type, LocalDateTime dateTime) {
        this.description = description;
        this.type = type;
        this.isDone = false;
        this.dateTime = dateTime;
    }

    /** Creates a task with all fields. */
    public Task(String description, TaskType type, boolean isDone, LocalDateTime dateTime) {
        this.description = description;
        this.type = type;
        this.isDone = isDone;
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public TaskType getType() {
        return type;
    }

    public boolean isDone() {
        return isDone;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(type).append("]").append(getStatusIcon()).append(" ");
        sb.append(description);

        if (dateTime != null) {
            // Use English locale to avoid encoding issues
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a", Locale.ENGLISH);
            if (type == TaskType.D) {
                sb.append(" (by: ").append(dateTime.format(formatter)).append(")");
            } else if (type == TaskType.E) {
                sb.append(" (at: ").append(dateTime.format(formatter)).append(")");
            }
        }

        return sb.toString();
    }

    /** Returns a single-line string for saving to file. */
    public String toFileFormat() {
        String doneFlag = isDone ? "1" : "0";
        StringBuilder sb = new StringBuilder();
        sb.append(type).append("|").append(doneFlag).append("|").append(description);

        if (dateTime != null) {
            sb.append("|").append(dateTime.toString());
        }

        return sb.toString();
    }
}
