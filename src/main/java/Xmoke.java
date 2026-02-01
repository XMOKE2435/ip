import java.util.Scanner;

public class Xmoke {
    private static final String LINE_SEPARATOR = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[MAX_TASKS];
        int taskCount = 0;
        boolean[] isDone = new boolean[MAX_TASKS];

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
                    System.out.println((i + 1) + "." + checkStatus(isDone[i]) + " " + tasks[i]);
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
                    System.out.println("  " + "[X]" + " " + tasks[index]);
                    System.out.println(LINE_SEPARATOR);
                }
                continue;
            }
            if (input.trim().startsWith("unmark")) {
                int index = parseIndex(input.trim().substring("unmark ".length()), taskCount);
                if (index != -1) {
                    System.out.println(LINE_SEPARATOR);
                    isDone[index] = false;
                    System.out.println("Nice! I've marked this task as not done yet:");
                    System.out.println("  " + "[ ]" + " " + tasks[index]);
                    System.out.println(LINE_SEPARATOR);
                }
                continue;
            }
            if (taskCount >= MAX_TASKS) {
                System.out.println("I can't take it anymore!");
                continue;
            }
            tasks[taskCount] = input;
            isDone[taskCount] = false;
            taskCount++;
            System.out.println(LINE_SEPARATOR);
            System.out.println("added: " + input);
            System.out.println(LINE_SEPARATOR);
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