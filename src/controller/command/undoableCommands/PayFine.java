package controller.command.undoableCommands;

import controller.command.UndoableCommand;
import model.BookwormLibrary;
import model.Visitor;

/**
 * Pay a user's fine Command
 */
public class PayFine implements UndoableCommand {

    private double finePayment;
    private Visitor visitor;

    public PayFine(String[] commandArgs) {
        this.finePayment = Double.parseDouble(commandArgs[2]);
        this.visitor = BookwormLibrary.getInstance().getVisitor(Long.parseLong(commandArgs[3]));
    }

    @Override
    public void execute() {
        if( this.visitor != null){
            if(this.visitor.payFine(this.finePayment) ){
                System.out.println("pay,success," + this.visitor.getFine() + ";");
            }else{
                System.out.println("pay,invalid-amount," + this.finePayment + "," + visitor.getFine() + ":");
            }
        }else {
            System.out.println("pay,invalid-visitor-id;");
        }
    }

    @Override
    public void undo() {
        if( this.visitor != null ){
            this.visitor.addFine(this.finePayment);
        }
    }

    @Override
    public void redo() {
        this.visitor.addFine(this.finePayment);
    }


}