package controller.command.commands;

import controller.command.Command;

/**
 * Register a user to the library Command
 */
public class RegisterVisitor implements Command {
    public RegisterVisitor() {
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }


    /*@Override
    public String runCommand(String[] args) {
        if (args.length == 5) {
            return "register," + library.registerUser(args[1], args[2], args[3], args[4]) + ";";
        } else {
            return "register,missing-parameters,{first name,last name,address, phone-number};";
        }
    }*/
}