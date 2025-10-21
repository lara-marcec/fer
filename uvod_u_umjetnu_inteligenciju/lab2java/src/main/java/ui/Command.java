package ui;

public class Command
{
    Clause clause;
    String command;

    public Command(Clause clause, String command)
    {
       this.clause = clause;
       this.command = command;
    }

    public Clause getClause() {
        return clause;
    }

    public void setClause(Clause clause) {
        this.clause = clause;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
