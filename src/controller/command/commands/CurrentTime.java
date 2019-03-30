package controller.command.commands;

import controller.command.Command;
import model.BookwormLibrary;

import java.time.format.DateTimeFormatter;

/**
 * Get the current time Command
 */
public class CurrentTime implements Command {

    private Long clientId;

    public CurrentTime(String[] args) {
        clientId = Long.parseLong(args[0]);
    }

    @Override
    public void execute() {
        System.out.println(clientId + ","
                + BookwormLibrary.getInstance().getTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                + ","
                + BookwormLibrary.getInstance().getTime().format(DateTimeFormatter.ofPattern("hh:mm:ss"))
                + ";");
    }
}