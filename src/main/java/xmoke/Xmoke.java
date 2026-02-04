package xmoke;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Xmoke {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Xmoke() {
        ui = new Ui();
        storage = new Storage();
        tasks = storage.loadTasks();
    }

    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();

            if (input.trim().equalsIgnoreCase("bye")) {
                ui.showGoodbye();
                break;
            }

            if (input.equals("list")) {
                ui.showTaskList(tasks);
                continue;
            }

            if (input.trim().startsWith("view ")) {
                String dateStr = input.trim().substring("view ".length()).trim();
                try {
                    LocalDate date = Parser.parseDate(dateStr);
                    ArrayList<Task> tasksOnDate = tasks.getTasksOnDate(date);
                    ui.showTasksOnDate(tasksOnDate, date);
                } catch (DateTimeParseException e) {
                    ui.showError("OOPS!!! Invalid date format. Use yyyy-MM-dd or d/M/yyyy");
                }
                continue;
            }

            if (input.trim().equals("delete")) {
                ui.showError("OOPS!!! Please provide a task number to delete.");
                continue;
            }

            if (input.trim().startsWith("delete ")) {
                try {
                    int index = Parser.parseTaskIndex(
                            input.trim().substring("delete ".length()), tasks.size());
                    Task deletedTask = tasks.deleteTask(index);
                    storage.saveTasks(tasks);
                    ui.showTaskDeleted(deletedTask, tasks.size());
                } catch (NumberFormatException e) {
                    ui.showError("OOPS!!! Please provide a valid task number.");
                } catch (IndexOutOfBoundsException e) {
                    ui.showError("OOPS!!! That task number is out of range.");
                }
                continue;
            }

            if (input.trim().equals("find")) {
                ui.showError("OOPS!!! Please provide a keyword to find.");
                continue;
            }

            if (input.trim().startsWith("find ")) {
                String keyword = input.trim().substring("find ".length()).trim();
                if (keyword.isEmpty()) {
                    ui.showError("OOPS!!! Please provide a keyword to find.");
                    continue;
                }
                ui.showFoundTasks(tasks.findTasks(keyword));
                continue;
            }

            if (input.trim().equals("mark")) {
                ui.showError("OOPS!!! Please provide a task number to mark.");
                continue;
            }

            if (input.trim().startsWith("mark ")) {
                try {
                    int index = Parser.parseTaskIndex(
                            input.trim().substring("mark ".length()), tasks.size());
                    tasks.markTask(index);
                    storage.saveTasks(tasks);
                    ui.showTaskMarked(tasks.getTask(index));
                } catch (NumberFormatException e) {
                    ui.showError("OOPS!!! Please provide a valid task number.");
                } catch (IndexOutOfBoundsException e) {
                    ui.showError("OOPS!!! That task number is out of range.");
                }
                continue;
            }

            if (input.trim().equals("unmark")) {
                ui.showError("OOPS!!! Please provide a task number to unmark.");
                continue;
            }

            if (input.trim().startsWith("unmark ")) {
                try {
                    int index = Parser.parseTaskIndex(
                            input.trim().substring("unmark ".length()), tasks.size());
                    tasks.unmarkTask(index);
                    storage.saveTasks(tasks);
                    ui.showTaskUnmarked(tasks.getTask(index));
                } catch (NumberFormatException e) {
                    ui.showError("OOPS!!! Please provide a valid task number.");
                } catch (IndexOutOfBoundsException e) {
                    ui.showError("OOPS!!! That task number is out of range.");
                }
                continue;
            }

            if (tasks.isFull()) {
                ui.showError("I can't take it anymore!");
                continue;
            }

            if (input.trim().equals("todo")) {
                ui.showError("OOPS!!! The description of a todo cannot be empty.");
                continue;
            }

            if (input.trim().startsWith("todo ")) {
                String description = input.trim().substring("todo ".length()).trim();
                if (description.isEmpty()) {
                    ui.showError("OOPS!!! The description of a todo cannot be empty.");
                    continue;
                }
                Task task = new Task(description, Task.TaskType.T);
                tasks.addTask(task);
                storage.saveTasks(tasks);
                ui.showTaskAdded(task, tasks.size());
                continue;
            }

            if (input.trim().startsWith("deadline ")) {
                String remainder = input.trim().substring("deadline ".length()).trim();
                String[] parts = remainder.split(" /by ", 2);
                if (parts.length < 2) {
                    ui.showError("OOPS!!! A deadline must have /by followed by a date/time.");
                    continue;
                }

                String description = parts[0].trim();
                String dateTimeStr = parts[1].trim();

                try {
                    LocalDateTime dateTime = Parser.parseDateTime(dateTimeStr);
                    Task task = new Task(description, Task.TaskType.D, dateTime);
                    tasks.addTask(task);
                    storage.saveTasks(tasks);
                    ui.showTaskAdded(task, tasks.size());
                } catch (DateTimeParseException e) {
                    ui.showError("OOPS!!! Invalid date/time format. Use yyyy-MM-dd HHmm or d/M/yyyy HHmm");
                }
                continue;
            }

            if (input.trim().startsWith("event ")) {
                String remainder = input.trim().substring("event ".length()).trim();
                String[] firstSplit = remainder.split(" /from ", 2);

                if (firstSplit.length < 2) {
                    ui.showError("OOPS!!! An event must have /from and /to.");
                    continue;
                }

                String descriptionPart = firstSplit[0].trim();
                String[] secondSplit = firstSplit[1].split(" /to ", 2);

                if (secondSplit.length < 2) {
                    ui.showError("OOPS!!! An event must have /to.");
                    continue;
                }

                String fromTimeStr = secondSplit[0].trim();
                String toTimeStr = secondSplit[1].trim();

                if (descriptionPart.isEmpty() || fromTimeStr.isEmpty() || toTimeStr.isEmpty()) {
                    ui.showError("OOPS!!! The description/from/to of an event cannot be empty.");
                    continue;
                }

                try {
                    LocalDateTime fromTime = Parser.parseDateTime(fromTimeStr);
                    LocalDateTime toTime = Parser.parseDateTime(toTimeStr);

                    String description = descriptionPart + " (from: " + fromTimeStr + "; to: " + toTimeStr + ")";
                    Task task = new Task(description, Task.TaskType.E, fromTime);
                    tasks.addTask(task);
                    storage.saveTasks(tasks);
                    ui.showTaskAdded(task, tasks.size());
                } catch (DateTimeParseException e) {
                    ui.showError("OOPS!!! Invalid date/time format. Use yyyy-MM-dd HHmm or d/M/yyyy HHmm");
                }
                continue;
            }

            ui.showError("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }

        ui.close();
    }

    public static void main(String[] args) {
        new Xmoke().run();
    }
}
