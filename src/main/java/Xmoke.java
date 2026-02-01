import java.util.Scanner;

public class Xmoke {
    private static final String LINE_SEPARATOR =
            "____________________________________________________________";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
            System.out.println(LINE_SEPARATOR);
            System.out.println(input);
            System.out.println(LINE_SEPARATOR);
        }
        scanner.close();
    }
}