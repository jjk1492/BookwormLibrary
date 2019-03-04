package commands;

import java.util.ArrayList;

public class borrowBook extends command {

    @Override
    public String runCommand(String[] args) {
        if(args.length >= 3){
            ArrayList<String> ids = new ArrayList<>();

            for(int i = 2; i <= args.length - 1; i++){
                if(i == 2 && i == args.length - 1){
                    //only one book in the list
                    String s = args[i];
                    s = s.replace("{", "");
                    s = s.replace("}", "");
                    ids.add(s);
                }else if(i==2){
                    //first book in the list
                    //remove "{"
                    String s = args[i];
                    s = s.replace("{", "");
                    ids.add(s);
                }else if(i == args.length - 1){
                    //last book in the list
                    //remove "}"
                    String s = args[i];
                    s = s.replace("}", "");
                    ids.add(s);
                }else{
                    //and ID
                    ids.add(args[i]);
                }
            }

            return "borrow," + library.checkOut(args[1], ids);
        }
        return "borrow,visitor ID,{id};";
    }
}
