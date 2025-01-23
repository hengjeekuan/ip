import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jeenius {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> storage = new ArrayList<>();

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
                    String input = storage.get(x);
                    System.out.println(num + ". " + input);
                }
            } else {
                storage.add(userInput);
                System.out.println("added:" + userInput);
            }




            System.out.println("----------------------------------------");
            System.out.println(userInput);
            System.out.println("----------------------------------------");
        }
        scanner.close();
    }
}
