package ui;

import java.util.List;
import java.util.Objects;

import static ui.Resolution.getNegate;

public class Cooking
{
    public static void cooking(List<Command> commandsList, List<Clause> initClausesList)
    {
        System.out.println("Constructed with knowledge:");
        for(Clause c : initClausesList)
        {
            System.out.println(c);
        }
        System.out.println();

        for( Command c : commandsList )
        {
            System.out.println("User's command: " + (c.getClause()).toString().substring(3) + " " + c.getCommand());

            if(Objects.equals(c.getCommand(), "?"))
            {
                List<Clause> negateGoalState = getNegate(c.getClause());
                Resolution.plResolution(initClausesList, negateGoalState.get(0), c.getClause());
            }
            else if(Objects.equals(c.getCommand(), "+"))
            {
                initClausesList.add(c.getClause());
                System.out.println(initClausesList);
            }
            else if(Objects.equals(c.getCommand(), "-"))
            {
                initClausesList.remove(c.getClause());
                System.out.println(initClausesList);
            }

        }
    }
}
