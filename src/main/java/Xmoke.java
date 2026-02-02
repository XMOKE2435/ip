import java.util.Scanner;

public class Xmoke {
    private static final String LINE_SEPARATOR = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[MAX_TASKS];
        int taskCount = 0;
        boolean[] isDone = new boolean[MAX_TASKS];
        String[] types = new String[MAX_TASKS];

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
            if (input.trim().startsWith("mark ")) {
                int index = parseIndex(input.trim().substring("mark ".length()), taskCount);
                if (index != -1) {
                    System.out.println(LINE_SEPARATOR);
                    isDone[index] = true;
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  [" + types[index] + "][X]" + " " + tasks[index]);
                    System.out.println(LINE_SEPARATOR);
                }
                continue;
            }
            if (input.trim().startsWith("unmark ")) {
                int index = parseIndex(input.trim().substring("unmark ".length()), taskCount);
                if (index != -1) {
                    System.out.println(LINE_SEPARATOR);
                    isDone[index] = false;
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
            if (input.trim().startsWith("todo ")) {
                String description = input.trim().substring("todo ".length()).trim();
                tasks[taskCount] = description;
                isDone[taskCount] = false;
                types[taskCount] = "T";
                taskCount++;

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
                String deadline = parts[1].trim();
                String description = parts[0].trim() + " (by: " + deadline + ")";
                tasks[taskCount] = description;
                isDone[taskCount] = false;
                types[taskCount] = "D";
                taskCount++;

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
                    System.out.println("OOPS!!! An event must have /from and /to.");
                    continue;
                }

                String descriptionPart = firstSplit[0].trim();
                String[] secondSplit = firstSplit[1].split(" /to ", 2);

                if (secondSplit.length < 2) {
                    System.out.println("OOPS!!! An event must have /to.");
                    continue;
                }

                String fromTime = secondSplit[0].trim();
                String toTime = secondSplit[1].trim();

                if (descriptionPart.isEmpty() || fromTime.isEmpty() || toTime.isEmpty()) {
                    System.out.println("OOPS!!! The description/from/to of an event cannot be empty.");
                    continue;
                }

                String description = descriptionPart + " (from: " + fromTime + "; to: " + toTime + ")";
                tasks[taskCount] = description;
                isDone[taskCount] = false;
                types[taskCount] = "E";
                taskCount++;

                System.out.println(LINE_SEPARATOR);
                System.out.println("Got it. I've added this task:");
                System.out.println("[E][ ] " + description);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(LINE_SEPARATOR);
                continue;
            }

        }
        scanner.close();
    }

    private static int parseIndex(String indexText, int taskCount) {
        String trimmed = indexText.trim();
        int oneBasedIndex;

        try {
            oneBasedIndex = Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            System.out.println("OOPS!!! Please provide a valid task number.");
            return -1;
        }

        int zeroBasedIndex = oneBasedIndex - 1;
        if (zeroBasedIndex < 0 || zeroBasedIndex >= taskCount) {
            System.out.println("OOPS!!! That task number is out of range.");
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
}