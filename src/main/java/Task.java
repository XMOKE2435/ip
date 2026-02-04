import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Task {
    public enum TaskType {
        T, D, E
    }

    private String description;
    private boolean isDone;
    private TaskType type;
    private LocalDateTime dateTime; // For deadlines and events

    public Task(String description, TaskType type) {
        this.description = description;
        this.type = type;
        this.isDone = false;
        this.dateTime = null;
    }

    public Task(String description, TaskType type, boolean isDone) {
        this.description = description;
        this.type = type;
        this.isDone = isDone;
        this.dateTime = null;
    }

    // Constructor with date/time
    public Task(String description, TaskType type, LocalDateTime dateTime) {
        this.description = description;
        this.type = type;
        this.isDone = false;
        this.dateTime = dateTime;
    }

    // Constructor with all parameters
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