package controller.command.commands;
import controller.command.Command;
import model.BookwormLibrary;

public class shutdown implements Command {

    @Override
    public void execute() {
        BookwormLibrary.getInstance().shutdown();
    }
}
