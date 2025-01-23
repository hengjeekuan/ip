import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Jeenius {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Task> storage = new ArrayList<>();

        printLine();
        System.out.println("Hello! I'm Jeenius");
        System.out.println("What can I do for you today?");
        printLine();

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                printLine();
                System.out.println("Bye. Hope to see you again soon!");
                printLine();
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                printTaskList(storage);
            } else if (userInput.startsWith("mark")){
                markOrUnmark(storage, userInput, true);
            } else if (userInput.startsWith("unmark")){
                markOrUnmark(storage, userInput,false);
            } else {
                Task nextInput = new Task(userInput);
                storage.add(nextInput);
                System.out.println("added: " + userInput);
            }

            printLine();
            System.out.println(userInput);
            printLine();
        }
        scanner.close();
    }

    public static void printTaskList(List<Task> storage) {
        printLine();
        System.out.println("Task List:");
        for (int x = 0; x < storage.size(); x = x + 1) {
            int num = x + 1;
            Task input = storage.get(x);
            System.out.println(num + input.toString());
        }
        printLine();
    }

    public static void markOrUnmark(List<Task> storage, String userInput, boolean isMark) {
        String[] parts = userInput.split(" ");
        int taskNumber = Integer.parseInt(parts[1]) - 1;
        int printNumber = taskNumber + 1;
        if (taskNumber >= 0 && taskNumber < storage.size()) {
            Task task = storage.get(taskNumber);
            if (isMark) {
                task.mark();
                System.out.println("Marked as done");
            } else {
                task.unmark();
                System.out.println("Marked as undone");
            }
            System.out.println(printNumber + task.toString());
        } else {
            System.out.println("Invalid task number");
        }
    }

    public static void printLine() {
        System.out.println("----------------------------------------");
    }
}
