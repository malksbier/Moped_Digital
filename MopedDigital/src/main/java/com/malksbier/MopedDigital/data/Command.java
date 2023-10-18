package com.malksbier.MopedDigital.data;
import java.util.Objects;

public class Command {
    public static String TAG = "[Command] ";
    private String command;

    private final String taskSeperator = ",";
    private final String commandSeperator = ";";

    public Command() {
        this.command = "";
    }

    private Command(String maybeCommand) {
        this.command = maybeCommand;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getTaskSeperator() {
        return this.taskSeperator;
    }


    public String getCommandSeperator() {
        return this.commandSeperator;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Command)) {
            return false;
        }
        Command command = (Command) o;
        return Objects.equals(command, command.command) && Objects.equals(taskSeperator, command.taskSeperator) && Objects.equals(commandSeperator, command.commandSeperator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, taskSeperator, commandSeperator);
    }

    @Override
    public String toString() {
        return "{" +
            " command='" + getCommand() + "'" +
            ", taskSeperator='" + getTaskSeperator() + "'" +
            ", commandSeperator='" + getCommandSeperator() + "'" +
            "}";
    }
   

    public static Command fromString(String command) {
        if(command.endsWith(",;") && command.split(";").length == 1) {
            return new Command(command);
        } else {
            //TODO formatCommand
            System.out.println(TAG + " difficult Command not verified: " + command);
            return new Command(command);
        }
        
    }

    public String buildCommand() {
        this.validate();
        return command;
    }

    public void addToCommand(String task) {
        command += taskSeperator + task;
    }

    private void validate() {
        if(command.endsWith(taskSeperator + commandSeperator)) {
            return;
        } else {
            if(command.endsWith(taskSeperator)) {
                command += commandSeperator;
            } else {
                command += taskSeperator + commandSeperator;
            }
            return;
        }
    }
}
