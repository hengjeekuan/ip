import java.util.Scanner;

public class Jeenius {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
            }

            System.out.println("----------------------------------------");
            System.out.println(userInput);
            System.out.println("----------------------------------------");
        }
        scanner.close();
    }
}
