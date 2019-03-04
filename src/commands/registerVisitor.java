package commands;

/**
 * Register a user to the library command
 */
public class registerVisitor extends command {
    @Override
    public String runCommand(String[] args) {
        if (args.length == 5) {
            return "register," + library.registerUser(args[1], args[2], args[3], args[4]) + ";";
        } else {
            return "register,missing-parameters,{first name,last name,address, phone-number};";
        }
    }
}