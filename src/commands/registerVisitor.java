package commands;

public class registerVisitor extends command{
    @Override
    public String runCommand(String[] args) {
        if(args.length == 5) {
            library.registerUser(args[1], args[2], args[3], args[4], "1");
            return "success";
        }else{
            return "";
        }
    }
}
