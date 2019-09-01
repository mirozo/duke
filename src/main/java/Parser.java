import Task.Deadline;
import Task.Event;
import Task.Task;
import Task.Todo;

import java.text.ParseException;
import java.util.ArrayList;

public class Parser {
    private String command;
    private String taskDetails;
    private Ui ui;
    private TaskList list;

    public Parser(String line, Ui ui, TaskList list) {
        this.ui = ui;
        this.list = list;
        String[] words = line.split(" ");
        this.command = words[0];

        String taskDetails = "";
        for (int i = 1; i < words.length; i++) {
            if (i == words.length - 1) {
                taskDetails += words[i];
            } else {
                taskDetails += words[i] + " ";
            }
        }
        this.taskDetails = taskDetails;
    }
    /**
     * Returns command or the first word of user input
     *
     * @return command/ first word.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Execute the command that user typed in, together with the
     * additional tasks details given
     *
     * @throws ParseException If date given is in the incorrect format and not dd/MM/yyyy HHmm.
     */
    public void doCommand() throws ParseException {
        if (taskDetails.equals("")) {
            if (command.equals("bye") || command.equals("list") ) {

            } else {
                ui.showDescriptionEmptyError();
                return;
            }
        }
        switch (command) {
            case "list":
                ui.list(list.getList());
                break;
            case "done":
                try {
                    Task task = list.doTask(taskDetails);
                    ui.taskDone(task);
                } catch (Exception ex) {
                    ui.noSuchTaskError();
                }
                break;
            case "delete":
                try {
                    Task task = list.deleteTask(taskDetails);
                    ui.taskDeleted(task);
                    ui.showNumberOfTasks(list.getList());
                } catch (Exception ex) {
                    ui.noSuchTaskError();
                }
                break;
            case "todo":
                Todo task = new Todo(taskDetails);
                list.addTask(task);
                ui.taskCreated(task);
                ui.showNumberOfTasks(list.getList());
                break;
            case "deadline": {
                //split the string by /
                String[] halves = taskDetails.split("/by");
                String description = halves[0];
                String by = halves[1];
                Deadline deadline = new Deadline(description, by);
                list.addTask(deadline);
                ui.taskCreated(deadline);
                ui.showNumberOfTasks(list.getList());
                break;
            }
            case "event": {
                String[] halves = taskDetails.split("/at");
                String description = halves[0];
                String by = halves[1];
                Event event = new Event(description, by);
                list.addTask(event);
                ui.taskCreated(event);
                ui.showNumberOfTasks(list.getList());
                break;
            }
            case "find": {
                String keyword = taskDetails;
                ArrayList<Task> filteredList = new ArrayList<>();
                for (Task item: list.getList()) {
                    if (item.getDescription().contains(keyword)) {
                        filteredList.add(item);
                    }
                }
                ui.list(filteredList);
                break;
            }
            default:
                ui.showWrongCommandError();
                break;
        }
    }
}
