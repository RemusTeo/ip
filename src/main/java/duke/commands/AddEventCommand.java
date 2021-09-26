package duke.commands;

import duke.storage.Storage;
import duke.tasks.TaskList;
import duke.tasks.Event;
import duke.parser.Parser;
import duke.ui.Ui;
import duke.exceptions.DateTimeFormatException;

import java.time.LocalDateTime;

/**
 * Adds an Event Task
 * If no arguments provided by user, then error message is printed.
 * Calls splitByDelimiter from Parser to process command and arguments.
 */
public class AddEventCommand extends Command {
    String arguments;

    public AddEventCommand(String command, String arguments) {
        super(command);
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (arguments.equals("")) {
            String output = " ☹ OOPS!!! The description of an event cannot be empty.\n";
            ui.printOutput(output);
        } else {
            String delimiter = "/at";
            try {
                String[] splitArguments = Parser.splitByDelimiter(delimiter, arguments);
                String description = splitArguments[0];
                String at = splitArguments[1];

                LocalDateTime dateTime = Parser.parseDateTime(at);
                Event newEvent = new Event(description, dateTime);
                tasks.addTask(newEvent);
                int taskListSize = tasks.sizeOfTaskList();
                ui.acknowledgeAddedTask(newEvent, taskListSize);
            } catch (StringIndexOutOfBoundsException e) {
                ui.displayDelimiterErrorMessage();
            } catch (DateTimeFormatException e) {
                ui.displayInvalidDateTimeFormatResponse();
            }
        }
    }
}