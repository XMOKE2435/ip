import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Xmoke {
    private static final String LINE_SEPARATOR = "____________________________________________________________";
    private static final int MAX_TASKS = 100;
    private enum TaskType {
        T, D, E
    }
    private static final Path DATA_FOLDER_PATH = Paths.get("data");
    private static final Path DATA_FILE_PATH = DATA_FOLDER_PATH.resolve("XMOKE.txt");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[MAX_TASKS];
        int taskCount = 0;
        boolean[] isDone = new boolean[MAX_TASKS];
        TaskType[] types = new TaskType[MAX_TASKS];
        ensureDataFileExists();
        taskCount = loadTasks(tasks, isDone, types);

        System.out.println(LINE_SEPARATOR);
        System.out.println("Hello! I'm XMOKE");
        System.out.println("What can I do for you?");
        System.out.println(LINE_SEPARATOR);

        while (true) {
            String input = scanner.nextLine();
            if (input.trim().equalsIgnoreCase("bye")) {
                System.out.println(LINE_SEPARATOR);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE_SEPARATOR);
                break;
            }
            if (input.equals("list")) {
                System.out.println(LINE_SEPARATOR);
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ".[" + types[i] + "]" + checkStatus(isDone[i]) + " " + tasks[i]);
                }
                System.out.println(LINE_SEPARATOR);
                continue;
            }

            if (input.trim().equals("delete")) {
                printError("OOPS!!! Please provide a task number to delete.");
                continue;
            }
            if (input.trim().startsWith("delete ")) {
                int index = parseIndex(input.trim().substring("delete ".length()), taskCount);
                if (index != -1) {
                    System.out.println(LINE_SEPARATOR);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  [" + types[index] + "]" + checkStatus(isDone[index]) + " " + tasks[index]);

                    for (int i = index; i < taskCount - 1; i++) {
                        tasks[i] = tasks[i + 1];
                        isDone[i] = isDone[i + 1];
                        types[i] = types[i + 1];
                    }

                    tasks[taskCount - 1] = null;
                    isDone[taskCount - 1] = false;
                    types[taskCount - 1] = null;

                    taskCount--;
                    saveTasks(tasks, isDone, types, taskCount);

                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println(LINE_SEPARATOR);
                }
                continue;
            }


            if (input.trim().equals("mark")) {
                printError("OOPS!!! Please provide a task number to mark.");
                continue;
            }
            if (input.trim().startsWith("mark ")) {
                int index = parseIndex(input.trim().substring("mark ".length()), taskCount);
                if (index != -1) {
                    System.out.println(LINE_SEPARATOR);
                    isDone[index] = true;
                    saveTasks(tasks, isDone, types, taskCount);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  [" + types[index] + "][X]" + " " + tasks[index]);
                    System.out.println(LINE_SEPARATOR);
                }
                continue;
            }

            if (input.trim().equals("unmark")) {
                printError("OOPS!!! Please provide a task number to unmark.");
                continue;
            }
            if (input.trim().startsWith("unmark ")) {
                int index = parseIndex(input.trim().substring("unmark ".length()), taskCount);
                if (index != -1) {
                    System.out.println(LINE_SEPARATOR);
                    isDone[index] = false;
                    saveTasks(tasks, isDone, types, taskCount);
                    System.out.println("Nice! I've marked this task as not done yet:");
                    System.out.println("  [" + types[index] + "][ ]" + " " + tasks[index]);
                    System.out.println(LINE_SEPARATOR);
                }
                continue;
            }
            if (taskCount >= MAX_TASKS) {
                System.out.println("I can't take it anymore!");
                continue;
            }

            if (input.trim().equals("todo")) {
                printError("OOPS!!! The description of a todo cannot be empty.");
                continue;
            }
            if (input.trim().startsWith("todo ")) {
                String description = input.trim().substring("todo ".length()).trim();
                if (description.isEmpty()) {
                    printError("OOPS!!! The description of a todo cannot be empty.");
                    continue;
                }
                tasks[taskCount] = description;
                isDone[taskCount] = false;
                types[taskCount] = TaskType.T;
                taskCount++;
                saveTasks(tasks, isDone, types, taskCount);

                System.out.println(LINE_SEPARATOR);
                System.out.println("Got it. I've added this task:");
                System.out.println("[T][ ] " + description);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(LINE_SEPARATOR);
                continue;
            }

            if (input.trim().startsWith("deadline ")) {
                String remainder = input.trim().substring("deadline ".length()).trim();
                String[] parts = remainder.split(" /by ", 2);
                if (parts.length < 2) {
                    printError("OOPS!!! A deadline must have /by followed by a time.");
                    continue;
                }
                String deadline = parts[1].trim();
                String description = parts[0].trim() + " (by: " + deadline + ")";
                tasks[taskCount] = description;
                isDone[taskCount] = false;
                types[taskCount] = TaskType.D;
                taskCount++;
                saveTasks(tasks, isDone, types, taskCount);

                System.out.println(LINE_SEPARATOR);
                System.out.println("Got it. I've added this task:");
                System.out.println("[D][ ] " + description);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(LINE_SEPARATOR);
                continue;
            }

            if (input.trim().startsWith("event ")) {
                String remainder = input.trim().substring("event ".length()).trim();
                String[] firstSplit = remainder.split(" /from ", 2);

                if (firstSplit.length < 2) {
                    printError("OOPS!!! An event must have /from and /to.");
                    continue;
                }

                String descriptionPart = firstSplit[0].trim();
                String[] secondSplit = firstSplit[1].split(" /to ", 2);

                if (secondSplit.length < 2) {
                    printError("OOPS!!! An event must have /to.");
                    continue;
                }

                String fromTime = secondSplit[0].trim();
                String toTime = secondSplit[1].trim();

                if (descriptionPart.isEmpty() || fromTime.isEmpty() || toTime.isEmpty()) {
                    printError("OOPS!!! The description/from/to of an event cannot be empty.");
                    continue;
                }

                String description = descriptionPart + " (from: " + fromTime + "; to: " + toTime + ")";
                tasks[taskCount] = description;
                isDone[taskCount] = false;
                types[taskCount] = TaskType.E;
                taskCount++;
                saveTasks(tasks, isDone, types, taskCount);

                System.out.println(LINE_SEPARATOR);
                System.out.println("Got it. I've added this task:");
                System.out.println("[E][ ] " + description);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(LINE_SEPARATOR);
                continue;
            }
            printError("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
        scanner.close();
    }

    private static int parseIndex(String indexText, int taskCount) {
        String trimmed = indexText.trim();
        int oneBasedIndex;

        try {
            oneBasedIndex = Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            printError("OOPS!!! Please provide a valid task number.");
            return -1;
        }

        int zeroBasedIndex = oneBasedIndex - 1;
        if (zeroBasedIndex < 0 || zeroBasedIndex >= taskCount) {
            printError("OOPS!!! That task number is out of range.");
            return -1;
        }

        return zeroBasedIndex;
    }
    private static String checkStatus(boolean isDone) {
        if (isDone) {
            return "[X]";
        }
        return "[ ]";
    }
    private static void printError(String message) {
        System.out.println(LINE_SEPARATOR);
        System.out.println(message);
        System.out.println(LINE_SEPARATOR);
    }

    private static void ensureDataFileExists() {
        try {
            Files.createDirectories(DATA_FOLDER_PATH);
            if (Files.notExists(DATA_FILE_PATH)) {
                Files.createFile(DATA_FILE_PATH);
            }
        } catch (IOException e) {
            printError("OOPS!!! I couldn't set up the data file: " + e.getMessage());
        }
    }

    private static int loadTasks(String[] tasks, boolean[] isDone, TaskType[] types) {
        int taskCount = 0;

        try {
            List<String> lines = Files.readAllLines(DATA_FILE_PATH);
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|", 3);
                if (parts.length < 3) {
                    continue;
                }

                TaskType type;
                try {
                    type = TaskType.valueOf(parts[0].trim());
                } catch (IllegalArgumentException e) {
                    continue; // corrupted type
                }

                String doneFlag = parts[1].trim();
                boolean done = doneFlag.equals("1");

                String description = parts[2].trim();

                if (taskCount >= MAX_TASKS) {
                    break;
                }

                types[taskCount] = type;
                isDone[taskCount] = done;
                tasks[taskCount] = description;
                taskCount++;
            }
        } catch (IOException e) {
            printError("OOPS!!! I couldn't load saved data: " + e.getMessage());
        }

        return taskCount;
    }

    private static void saveTasks(String[] tasks, boolean[] isDone, TaskType[] types, int taskCount) {
        try {
            StringBuilder content = new StringBuilder();
            for (int i = 0; i < taskCount; i++) {
                String doneFlag = isDone[i] ? "1" : "0";
                content.append(types[i]).append("|").append(doneFlag).append("|").append(tasks[i]).append("\n");
            }
            Files.writeString(DATA_FILE_PATH, content.toString());
        } catch (IOException e) {
            printError("OOPS!!! I couldn't save data: " + e.getMessage());
        }
    }
}