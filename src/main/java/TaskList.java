import java.time.LocalDate;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;
    private static final int MAX_TASKS = 100;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        if (tasks.size() >= MAX_TASKS) {
            throw new IllegalStateException("Task list is full!");
        }
        tasks.add(task);
    }

    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public void markTask(int index) {
        tasks.get(index).markAsDone();
    }

    public void unmarkTask(int index) {
        tasks.get(index).markAsNotDone();
    }

    public int size() {
        return tasks.size();
    }

    public boolean isFull() {
        return tasks.size() >= MAX_TASKS;
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Find tasks occurring on a specific date
     */
    public ArrayList<Task> getTasksOnDate(LocalDate date) {
        ArrayList<Task> tasksOnDate = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDateTime() != null) {
                if (task.getDateTime().toLocalDate().equals(date)) {
                    tasksOnDate.add(task);
                }
            }
        }
        return tasksOnDate;
    }
}