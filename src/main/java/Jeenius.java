import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Jeenius {
    public static void main(String[] args) {

        File folder = new File("./data");
        if (!folder.exists()) {
            folder.mkdir();
        }
        String filePath = "./data/Jeenius.txt";
        List<Task> storage;
        try {
            storage = loadTasks(filePath);
        } catch (JeeniusException e) {
            System.out.println(e.getMessage());
            storage = new ArrayList<>();
        }
        Scanner scanner = new Scanner(System.in);

        printLine();
        System.out.println("Hello! I'm Jeenius");
        System.out.println("What can I do for you today?");
        printLine();

        while (true) {
            String userInput = scanner.nextLine();

            try {
                if (userInput.trim().isEmpty()) {
                    throw new JeeniusException("stop pressing enter without typing anything");
                } else if (userInput.equalsIgnoreCase("bye")) {
                    printLine();
                    System.out.println("Bye. Hope to see you again soon!");
                    printLine();
                    break;
                } else if (userInput.equalsIgnoreCase("list")) {
                    printTaskList(storage);
                } else if (userInput.startsWith("mark")){
                    try {
                        markOrUnmark(storage, userInput, true);
                    } catch (JeeniusException e) {
                        System.out.println(e.getMessage());
                    }
                } else if (userInput.startsWith("unmark")){
                    try {
                        markOrUnmark(storage, userInput,false);
                    } catch (JeeniusException e) {
                        System.out.println(e.getMessage());
                    }
                } else if (userInput.startsWith("deadline")) {
                    try {
                        createDeadlineTask(storage, userInput);
                    } catch (JeeniusException e) {
                        System.out.println(e.getMessage());
                    }
                } else if (userInput.startsWith("event")) {
                    try {
                        createEventTask(storage, userInput);
                    } catch (JeeniusException e) {
                        System.out.println(e.getMessage());
                    }
                } else if (userInput.startsWith("todo")) {
                    try {
                        createToDoTask(storage, userInput);
                    } catch (JeeniusException e) {
                        System.out.println(e.getMessage());
                    }
                } else if (userInput.startsWith("delete")) {
                    try {
                        delete(storage, userInput);
                    } catch (JeeniusException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    throw new JeeniusException("sorry. i'm not that smart. i have limited available commands");
                }
            } catch (JeeniusException e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }

    static String filePath = "./data/Jeenius.txt";

    public static List<Task> loadTasks(String filePath) throws JeeniusException {
        List<Task> storage = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return storage;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                String taskType = parts[0].trim();
                boolean isDone = parts[1].trim().equals("1");
                String description = parts[2].trim();

                switch (taskType) {
                    case "D":
                        String by = parts[3].trim();
                        Deadline deadline = new Deadline(description, by);
                        if (isDone) {
                            deadline.mark();
                        }
                        storage.add(deadline);
                        break;
                    case "E":
                        String from = parts[3].trim();
                        String to = parts[4].trim();
                        Event event  = new Event(description, from, to);
                        if (isDone) {
                            event.mark();
                        }
                        storage.add(event);
                        break;
                    case "T":
                        ToDo todo = new ToDo(description);
                        if (isDone) {
                            todo.mark();
                        }
                        storage.add(todo);
                        break;

                }
            }
        } catch (FileNotFoundException e) {
            throw new JeeniusException("haha file not found");
        }
        return storage;
    }

    public static void saveTasks(List<Task> storage, String filePath) throws JeeniusException {
        printLine();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : storage) {
                if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    writer.write("D | " + (deadline.getDone() ? "1" : "0") + " | " + deadline.getDescription() + " | " + deadline.getBy());
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    writer.write("E | " + (event.getDone() ? "1" : "0") + " | " + event.getDescription() + " | " + event.getFrom() + " | " + event.getTo());
                } else if (task instanceof ToDo) {
                    writer.write("T | " + (task.getDone() ? "1" : "0") + " | "+ task.getDescription());
                }
                writer.newLine();
                System.out.println("saved: " + task.toString());
            }
        } catch (IOException e) {
            throw new JeeniusException("haha file doesn't exist");
        }
    }

    public static void delete(List<Task> storage, String userInput) throws JeeniusException {
        printLine();
        try {
            String[] parts = userInput.split(" ");
            int taskNumber = Integer.parseInt(parts[1]) - 1;
            Task task = storage.get(taskNumber);
            storage.remove(taskNumber);
            saveTasks(storage, filePath);
            System.out.println("deleted: " + task.toString());
        } catch (Exception e) {
            throw new JeeniusException("can't even delete a task properly? use: delete [task number]");
        }
    }

    public static void createDeadlineTask(List<Task> storage, String userInput) throws JeeniusException {
        printLine();
        try {
            String[] desc = userInput.split(" ", 2);
            String[] details = desc[1].split(" /by ", 2);
            String description = details[0];
            String by = details[1];
            Deadline newDeadlineTask = new Deadline(description, by);
            storage.add(newDeadlineTask);
            saveTasks(storage, filePath);
            System.out.println("added: " + newDeadlineTask.toString());
        } catch (Exception e){
                throw new JeeniusException("??? deadline tasks needs to be like this: deadline [task] /by [time]");
        }
        printLine();
    }

    public static void createEventTask(List<Task> storage, String userInput) throws JeeniusException {
        printLine();
        try {
            String[] desc = userInput.split(" ", 2);
            String[] details = desc[1].split(" /from ", 2);
            String[] time = details[1].split(" /to ", 2);

            String description = details[0];
            String from = time[0];
            String to = time[1];

            Event newEventTask = new Event(description, from, to);
            storage.add(newEventTask);
            saveTasks(storage, filePath);
            System.out.println("added: " + newEventTask.toString());
        } catch (Exception e) {
            throw new JeeniusException("YOU JEENIUS! use this: event [description] /from [time] /to [time]");
        }

        printLine();
    }

    public static void createToDoTask(List<Task> storage, String userInput) throws JeeniusException {
        printLine();

        ToDo newToDoTask = null;

        try {
            String[] desc = userInput.split(" ", 2);
            newToDoTask = new ToDo(desc[1]);
            storage.add(newToDoTask);
            saveTasks(storage, filePath);
            System.out.println("added: " + newToDoTask.toString());
        } catch (Exception e) {
            throw new JeeniusException("bro how do you todo nothing??? ADD A DESCRIPTION FOR YOUR TODO");
        }

        printLine();
    }

    public static void printTaskList(List<Task> storage) {
        printLine();
        System.out.println("Task List:");
        for (int x = 0; x < storage.size(); x = x + 1) {
            int num = x + 1;
            Task input = storage.get(x);
            System.out.println(num + "." + input.toString());
        }
        printLine();
    }

    public static void markOrUnmark(List<Task> storage, String userInput, boolean isMark) throws JeeniusException {
        String[] parts = userInput.split(" ");
        if (parts.length < 2 || parts[1].isEmpty() || !parts[1].matches("\\d+")){
            throw new JeeniusException("... wrong format bruh. use: mark/unmark [task number");
        }

        int taskNumber = Integer.parseInt(parts[1]) - 1;

        if (taskNumber < 0 || taskNumber >= storage.size()) {
            throw new JeeniusException("to mark or unmark, the number has to be within the list innit?");
        }



        Task task = storage.get(taskNumber);
        if (isMark) {
            task.mark();
            saveTasks(storage, filePath);
            System.out.println("Marked as done");
        } else {
            task.unmark();
            saveTasks(storage, filePath);
            System.out.println("Marked as undone");
        }
        System.out.println(taskNumber + 1 + "." + task.toString());
    }

    public static void printLine() {
        System.out.println("----------------------------------------");
    }
}
