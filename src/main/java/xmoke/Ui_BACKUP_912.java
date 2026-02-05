package xmoke;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all user-facing output messages for the chatbot.
 */

public class Ui {
    private static final String LINE_SEPARATOR = "____________________________________________________________";
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Hello! I'm XMOKE");
        System.out.println("What can I do for you?");
        System.out.println(LINE_SEPARATOR);
    }

    public void showGoodbye() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE_SEPARATOR);
    }

    public void showLine() {
        System.out.println(LINE_SEPARATOR);
    }

    public void showError(String message) {
        System.out.println(LINE_SEPARATOR);
        System.out.println(message);
        System.out.println(LINE_SEPARATOR);
    }

    public void showTaskList(TaskList taskList) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + "." + taskList.getTask(i));
        }
        System.out.println(LINE_SEPARATOR);
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE_SEPARATOR);
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE_SEPARATOR);
    }

    public void showTaskMarked(Task task) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        System.out.println(LINE_SEPARATOR);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Nice! I've marked this task as not done yet:");
        System.out.println("  " + task);
        System.out.println(LINE_SEPARATOR);
    }

    public void showTasksOnDate(ArrayList<Task> tasks, LocalDate date) {
        System.out.println(LINE_SEPARATOR);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
        if (tasks.isEmpty()) {
            System.out.println("No tasks found on " + date.format(formatter));
        } else {
            System.out.println("Tasks on " + date.format(formatter) + ":");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
        System.out.println(LINE_SEPARATOR);
    }

<<<<<<< HEAD
    public void showFoundTasks(ArrayList<Task> found) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < found.size(); i++) {
            System.out.println((i + 1) + "." + found.get(i));
        }
        System.out.println(LINE_SEPARATOR);
    }

=======
    public void showCheer(String quote) {
        System.out.println(LINE_SEPARATOR);
        System.out.println(quote);
        System.out.println(LINE_SEPARATOR);
    }


>>>>>>> branch-A-Cheer
    public String readCommand() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}