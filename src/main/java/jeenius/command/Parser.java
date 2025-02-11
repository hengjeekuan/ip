package jeenius.command;

import jeenius.exception.JeeniusException;
import jeenius.list.TaskList;
import jeenius.storage.Storage;
import jeenius.task.Deadline;
import jeenius.task.Event;
import jeenius.task.Task;
import jeenius.task.ToDo;
import jeenius.ui.Ui;

/**
 * Parses user input and executes the corresponding command.
 */
public class Parser {
    /**
     * Processes the user input and performs the necessary operations.
     *
     * @param input The user's command as a string.
     * @param tasks The task list that stores all task.
     * @param ui The user interface for displaying messages.
     * @param storage The storage handler for saving and loading tasks.
     * @throws JeeniusException If the user input is invalid or cannot be processed.
     */
    public void parse(String input, TaskList tasks, Ui ui, Storage storage) throws JeeniusException {
        if (input.trim().isEmpty()) {
            throw new JeeniusException("stop pressing enter without typing anything!");
        } else if (input.equalsIgnoreCase("bye")) {
            ui.printExitMessage();
        } else if (input.equalsIgnoreCase("list")) {
            ui.printTaskList(tasks.getTasks());
        } else if (input.startsWith("todo")) {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                throw new JeeniusException("bro how do you todo nothing??? ADD A DESCRIPTION FOR YOUR TODO");
            }
            ToDo todo = new ToDo(parts[1]);
            tasks.addTask(todo);
            storage.save(tasks.getTasks());
            System.out.println("Added: " + todo);
        } else if (input.startsWith("delete")) {
            try {
                String[] parts = input.split(" ");
                int taskNumber = Integer.parseInt(parts[1]) - 1;
                Task task = tasks.getSize(taskNumber);
                tasks.deleteTask(taskNumber);
                storage.save(tasks.getTasks());
                ui.printLine();
                System.out.println("deleted: " + task);
                ui.printLine();
            } catch (Exception e) {
                throw new JeeniusException("can't even delete a task properly? Use: delete [task number]");
            }
        } else if (input.startsWith("deadline")) {
            try {
                String[] parts = input.split(" ", 2);
                String[] details = parts[1].split(" /by ", 2);
                Deadline deadline = new Deadline(details[0], details[1]);
                tasks.addTask(deadline);
                storage.save(tasks.getTasks());
                ui.printLine();
                System.out.println("added: " + deadline);
                ui.printLine();
            } catch (Exception e) {
                throw new JeeniusException("??? deadline tasks need to be like this: deadline [task] /by [d/M/yyyy HHmm]");
            }
        } else if (input.startsWith("event")) {
            try {
                String[] parts = input.split(" ", 2);
                String[] details = parts[1].split(" /from ", 2);
                String[] times = details[1].split(" /to ", 2);
                Event event = new Event(details[0], times[0], times[1]);
                tasks.addTask(event);
                storage.save(tasks.getTasks());
                ui.printLine();
                System.out.println("added: " + event);
                ui.printLine();
            } catch (Exception e) {
                throw new JeeniusException("YOU JEENIUS! use this: event [description] /from [d/M/yyyy HHmm] /to [d/M/yyyy HHmm]");
            }
        } else if (input.startsWith("mark") || input.startsWith("unmark")) {
            try {
                String[] parts = input.split(" ");
                if (parts.length < 2) {
                    throw new JeeniusException("Wrong format. Use: mark/unmark [task number]");
                }
                int taskNumber = Integer.parseInt(parts[1]) - 1;
                boolean isMark = input.startsWith("mark");

                Task task = tasks.getSize(taskNumber);
                if (isMark) {
                    task.mark();
                    System.out.println("Marked as done: " + task);
                } else {
                    task.unmark();
                    System.out.println("Marked as not done: " + task);
                }
                storage.save(tasks.getTasks());
            } catch (Exception e) {
                throw new JeeniusException("Failed to mark/unmark. Use: mark/unmark [task number]");
            }
        } else {
            throw new JeeniusException("sorry. i'm not that smart. i have limited available commands");
        }
    }
}
