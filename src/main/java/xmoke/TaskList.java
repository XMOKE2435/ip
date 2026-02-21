package xmoke;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Stores and manages a collection of tasks.
 * Provides operations such as add, delete, list, and find.
 */

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
        assert tasks.size() < MAX_TASKS : "list must not be full when adding";
        assert task != null : "task to add must not be null";
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
        return tasks.stream()
                .filter(task -> task.getDateTime() != null && task.getDateTime().toLocalDate().equals(date))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Task> findTasks(String keyword) {
        String needle = keyword.trim().toLowerCase();
        return tasks.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(needle))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}