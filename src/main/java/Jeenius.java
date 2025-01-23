import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Jeenius {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Task> storage = new ArrayList<>();

        System.out.println("----------------------------------------");
        System.out.println("Hello! I'm Jeenius");
        System.out.println("What can I do for you today?");
        System.out.println("----------------------------------------");

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("----------------------------------------");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("----------------------------------------");
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                for (int x = 0; x < storage.size(); x = x + 1) {
                    int num = x + 1;
                    Task input = storage.get(x);
                    String check = input.getStatusIcon();
                    System.out.println(num + ".[" + check + "] "+ input.getDescription());
                }
            } else if (userInput.startsWith("mark")){
                String[] parts = userInput.split(" ");
                int taskNumber = Integer.parseInt(parts[1]) - 1;
                Task task = storage.get(taskNumber);
                task.mark();
            } else if (userInput.startsWith("unmark")){
                String[] parts = userInput.split(" ");
                int taskNumber = Integer.parseInt(parts[1]) - 1;
                Task task = storage.get(taskNumber);
                task.unmark();
            } else {
                Task nextInput = new Task(userInput);
                storage.add(nextInput);
                System.out.println("added:" + userInput);
            }




            System.out.println("----------------------------------------");
            System.out.println(userInput);
            System.out.println("----------------------------------------");
        }
        scanner.close();
    }
}
