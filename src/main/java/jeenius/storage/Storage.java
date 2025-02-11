package jeenius.storage;

import jeenius.exception.JeeniusException;
import jeenius.task.Deadline;
import jeenius.task.Event;
import jeenius.task.Task;
import jeenius.task.ToDo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles reading from and writing to the storage file.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a Storage instance to manage saving and loading tasks.
     * @param filePath
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return A list of tasks loaded from the file.
     * @throws JeeniusException If an error occurs while reading the file.
     */
    public List<Task> load() throws JeeniusException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return tasks;
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
                        tasks.add(new Deadline(description, parts[3].trim()));
                        break;
                    case "E":
                        tasks.add(new Event(description, parts[3].trim(), parts[4].trim()));
                        break;
                    case "T":
                        tasks.add(new ToDo(description));
                        break;
                    default:
                        throw new JeeniusException("unknown task type in file.");
                }
            }
        } catch (IOException e) {
            throw new JeeniusException("error loading tasks from file.");
        }
        return tasks;
    }

    /**
     * Saves the list of tasks to the storage file.
     *
     * @param tasks The list of tasks to be saved.
     * @throws JeeniusException If an error occurs while writing to the file.
     */
    public void save(List<Task> tasks) throws JeeniusException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(task.toFileFormat());
                writer.newLine();
                System.out.println("saved: " + task.getDescription());
            }
        } catch (IOException e) {
            throw new JeeniusException("error saving tasks to file.");
        }
    }
}
