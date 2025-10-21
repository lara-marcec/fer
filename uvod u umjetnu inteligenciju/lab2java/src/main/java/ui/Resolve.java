package ui;

import java.util.ArrayList;
import java.util.List;

public class Resolve
{

    public static List<Clause> plResolve(Clause c1, Clause c2)
    {
        List<Clause> resolvedClauses = new ArrayList<>();

        List<Literal> literals1 = c1.getliteralList();
        List<Literal> literals2 = c2.getliteralList();

        List<Literal> toRemoveLiterals = new ArrayList<>();

        for(Literal l1 : literals1)
        {
            for(Literal l2 : literals2)
            {
                if( SelectClauses.checkLiterals(l1, l2) )
                {
                    toRemoveLiterals.add(l1);
                }
            }
        }

        List<Literal> literals = new ArrayList<>();

        literals.addAll(literals1);
        literals.addAll(literals2);
        for(Literal l : toRemoveLiterals){
            literals.remove(l);
            literals.remove(l.getNegate());
        }

        resolvedClauses.add(new Clause(0, literals));


        resolvedClauses = removeIrrelevant(resolvedClauses);
        //resolvedClauses = new ArrayList<>(removeRedundant(resolvedClauses));

        //Solution.allClauses.addAll(resolvedClauses);

//        System.out.println("resolved: ");
//        for(Clause c : resolvedClauses)
//        {
//            System.out.println(c.toString());
//        }


        if(literals.isEmpty() || resolvedClauses.isEmpty()) {Resolution.nil= true;}

        return resolvedClauses;
    }

    public static List<Clause> removeRedundant(List<Clause> clauseList) {
        List<Clause> redundant = new ArrayList<>();

        do
        {
            redundant.clear();
            for (Clause c1 : clauseList)
            {
                for (Clause c2 : clauseList)
                {
                    if (c1 != c2 && c2.getliteralList().containsAll(c1.getliteralList()))
                    {
                        redundant.add(c2);
                        break;
                    }
                }

            }

            clauseList.removeAll(redundant);
        }
        while(!redundant.isEmpty());

        return clauseList;
    }

    public static List<Clause> removeIrrelevant (List<Clause> clauseList)
    {
        List<Clause> output = new ArrayList<>();

        for (Clause c : clauseList)
        {
            boolean isIrrelevant = false;

            for (Literal l : c.getliteralList())
            {
                if (c.getliteralList().contains(l.getNegate()))
                {
                    isIrrelevant = true;
                    break;
                }
            }
            if (!isIrrelevant) {
                output.add(c);
            }
        }
        return output;
    }
}
