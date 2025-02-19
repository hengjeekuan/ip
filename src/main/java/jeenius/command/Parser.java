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
    public String parse(String input, TaskList tasks, Ui ui, Storage storage) throws JeeniusException {
        assert input != null : "User input should not be null";
        assert tasks != null : "TaskList should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";
        if (input.trim().isEmpty()) {
            throw new JeeniusException("stop pressing enter without typing anything!");
        } else if (input.equalsIgnoreCase("bye")) {
            return handleExit(ui);
        } else if (input.equalsIgnoreCase("list")) {
            return handleList(ui, tasks);
        } else if (input.startsWith("todo")) {
            return handleToDo(input, tasks, storage);
        } else if (input.startsWith("delete")) {
            return handleDelete(input, tasks, ui, storage);
        } else if (input.startsWith("deadline")) {
            return handleDeadline(input, tasks, ui, storage);
        } else if (input.startsWith("event")) {
            return handleEvent(input, tasks, ui, storage);
        } else if (input.startsWith("mark") || input.startsWith("unmark")) {
            return handleMark(input, tasks, storage);
        } else if (input.startsWith("find")) {
            return handleFind(input, tasks, ui);
        } else {
            throw new JeeniusException("sorry. i'm not that smart. i have limited available commands");
        }
    }

    private String handleExit(Ui ui) {
        return ui.printExitMessage();
    }

    private String handleList(Ui ui, TaskList tasks) {
        return ui.printTaskList(tasks.getTasks());
    }

    private String handleToDo(String input, TaskList tasks, Storage storage) throws JeeniusException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            throw new JeeniusException("bro how do you todo nothing??? ADD A DESCRIPTION FOR YOUR TODO");
        }
        ToDo todo = new ToDo(parts[1]);
        tasks.addTask(todo);
        storage.save(tasks.getTasks());
        return "Added: " + todo;
    }

    private String handleDelete(String input, TaskList tasks, Ui ui, Storage storage) throws JeeniusException {
        try {
            String[] parts = input.split(" ");
            int taskNumber = Integer.parseInt(parts[1]) - 1;
            assert taskNumber >= 0 && taskNumber < tasks.size() : "Task number is out of bounds: " + taskNumber;
            Task task = tasks.getSize(taskNumber);
            tasks.deleteTask(taskNumber);
            storage.save(tasks.getTasks());
            return "deleted: " + task;
        } catch (Exception e) {
            throw new JeeniusException("can't even delete a task properly? Use: delete [task number]");
        }
    }
    private String handleDeadline(String input, TaskList tasks, Ui ui, Storage storage) throws JeeniusException {
        try {
            String[] parts = input.split(" ", 2);
            String[] details = parts[1].split(" /by ", 2);
            assert details.length == 2 : "Invalid deadline format, expected: [task] /by [d/M/yyyy HHmm]";
            Deadline deadline = new Deadline(details[0], details[1]);
            tasks.addTask(deadline);
            storage.save(tasks.getTasks());
            return "added: " + deadline;
        } catch (Exception e) {
            throw new JeeniusException("??? deadline tasks need to be like this: "
                    + "deadline [task] /by [d/M/yyyy HHmm]");
        }
    }

    private String handleEvent(String input, TaskList tasks, Ui ui, Storage storage) throws JeeniusException {
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
            return "added: " + event;
        } catch (Exception e) {
            throw new JeeniusException("YOU JEENIUS! use this: "
                    + "event [description] /from [d/M/yyyy HHmm] /to [d/M/yyyy HHmm]");
        }
    }


    private String handleMark(String input, TaskList tasks, Storage storage) throws JeeniusException {
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
                storage.save(tasks.getTasks());
                return "Marked as done: " + task;
            } else {
                task.unmark();
                storage.save(tasks.getTasks());
                return "Marked as not done: " + task;
            }
        } catch (Exception e) {
            throw new JeeniusException("Failed to mark/unmark. Use: mark/unmark [task number]");
        }
    }

    private String handleFind(String input, TaskList tasks, Ui ui) throws JeeniusException {
        try {
            String[] parts = input.split(" ", 2);
            if (parts.length < 2) {
                throw new JeeniusException("bro what am i supposed to find? enter a keyword");
            }
            String keyword = parts[1];
            assert keyword != null && !keyword.isEmpty() : "Search keyword must not be null or empty";
            StringBuilder response = new StringBuilder("these are your matching tasks in your list:");
            int index = 1;
            for (Task task : tasks.findTasks(keyword)) {
                response.append(index).append(". ").append(task).append("\n");
                index++;
            }
            return response.toString();
        } catch (Exception e) {
            throw new JeeniusException("finding is easy, just use: find [keyword]");
        }
    }
}
