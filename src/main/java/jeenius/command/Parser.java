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
        assert input != null : "User input should not be null";
        assert tasks != null : "TaskList should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";

        if (input.trim().isEmpty()) {
            throw new JeeniusException("stop pressing enter without typing anything!");
        } else if (input.equalsIgnoreCase("bye")) {
            handleExit(ui);
        } else if (input.equalsIgnoreCase("list")) {
            handleList(ui, tasks);
        } else if (input.startsWith("todo")) {
            handleToDo(input, tasks, storage);
        } else if (input.startsWith("delete")) {
            handleDelete(input, tasks, ui, storage);
        } else if (input.startsWith("deadline")) {
            handleDeadline(input, tasks, ui, storage);
        } else if (input.startsWith("event")) {
            handleEvent(input, tasks, ui, storage);
        } else if (input.startsWith("mark") || input.startsWith("unmark")) {
            handleMark(input, tasks, storage);
        } else if (input.startsWith("find")) {
            handleFind(input, tasks, ui);
        } else {
            throw new JeeniusException("sorry. i'm not that smart. i have limited available commands");
        }
    }

    private void handleExit(Ui ui) {
        ui.printExitMessage();
    }

    private void handleList(Ui ui, TaskList tasks) {
        ui.printTaskList(tasks.getTasks());
    }

    private void handleToDo(String input, TaskList tasks, Storage storage) throws JeeniusException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            throw new JeeniusException("bro how do you todo nothing??? ADD A DESCRIPTION FOR YOUR TODO");
        }
        ToDo todo = new ToDo(parts[1]);
        tasks.addTask(todo);
        storage.save(tasks.getTasks());
        System.out.println("Added: " + todo);
    }

    private void handleDelete(String input, TaskList tasks, Ui ui, Storage storage) throws JeeniusException {
        try {
            String[] parts = input.split(" ");
            int taskNumber = Integer.parseInt(parts[1]) - 1;
            assert taskNumber >= 0 && taskNumber < tasks.size() : "Task number is out of bounds: " + taskNumber;
            Task task = tasks.getSize(taskNumber);
            tasks.deleteTask(taskNumber);
            storage.save(tasks.getTasks());
            ui.printLine();
            System.out.println("deleted: " + task);
            ui.printLine();
        } catch (Exception e) {
            throw new JeeniusException("can't even delete a task properly? Use: delete [task number]");
        }
    }
    private void handleDeadline(String input, TaskList tasks, Ui ui, Storage storage) throws JeeniusException {
        try {
            String[] parts = input.split(" ", 2);
            String[] details = parts[1].split(" /by ", 2);
            assert details.length == 2 : "Invalid deadline format, expected: [task] /by [d/M/yyyy HHmm]";
            Deadline deadline = new Deadline(details[0], details[1]);
            tasks.addTask(deadline);
            storage.save(tasks.getTasks());
            ui.printLine();
            System.out.println("added: " + deadline);
            ui.printLine();
        } catch (Exception e) {
            throw new JeeniusException("??? deadline tasks need to be like this: "
                    + "deadline [task] /by [d/M/yyyy HHmm]");
        }
    }

    private void handleEvent(String input, TaskList tasks, Ui ui, Storage storage) throws JeeniusException {
        try {
            String[] parts = input.split(" ", 2);
            String[] details = parts[1].split(" /from ", 2);
            String[] times = details[1].split(" /to ", 2);
            assert details.length == 2
                    : "Invalid event format, expected: [description] /from [d/M/yyyy HHmm] /to [d/M/yyyy HHmm]";
            assert times.length == 2
                    : "Invalid event format, expected: [description] /from [d/M/yyyy HHmm] /to [d/M/yyyy HHmm]";
            Event event = new Event(details[0], times[0], times[1]);
            tasks.addTask(event);
            storage.save(tasks.getTasks());
            ui.printLine();
            System.out.println("added: " + event);
            ui.printLine();
        } catch (Exception e) {
            throw new JeeniusException("YOU JEENIUS! use this: "
                    + "event [description] /from [d/M/yyyy HHmm] /to [d/M/yyyy HHmm]");
        }
    }


    private void handleMark(String input, TaskList tasks, Storage storage) throws JeeniusException {
        try {
            String[] parts = input.split(" ");
            if (parts.length < 2) {
                throw new JeeniusException("Wrong format. Use: mark/unmark [task number]");
            }
            int taskNumber = Integer.parseInt(parts[1]) - 1;
            assert taskNumber >= 0 && taskNumber < tasks.size() : "Task number is out of bounds: " + taskNumber;
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
    }

    private void handleFind(String input, TaskList tasks, Ui ui) throws JeeniusException {
        try {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                throw new JeeniusException("bro what am i supposed to find? enter a keyword");
            }
            String keyword = parts[1];
            assert keyword != null && !keyword.isEmpty() : "Search keyword must not be null or empty";
            ui.printLine();
            System.out.println("these are your matching tasks in your list:");
            int index = 1;
            for (Task task : tasks.findTasks(keyword)) {
                System.out.println(index + ". " + task);
                index++;
            }
            ui.printLine();
        } catch (Exception e) {
            throw new JeeniusException("finding is easy, just use: find [keyword]");
        }
    }
}
