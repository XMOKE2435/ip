import java.util.Scanner;

public class Xmoke {
    private static final String LINE_SEPARATOR = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[MAX_TASKS];
        int taskCount = 0;

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
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println(LINE_SEPARATOR);
                continue;
            }
            if (taskCount >= MAX_TASKS) {
                System.out.println("I can't take it anymore!");
                continue;
            }
            tasks[taskCount] = input;
            taskCount++;
            System.out.println(LINE_SEPARATOR);
            System.out.println("added: " + input);
            System.out.println(LINE_SEPARATOR);
        }
        scanner.close();
    }
}