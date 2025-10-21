package ui;

import java.util.ArrayList;
import java.util.List;

import static ui.Resolve.*;
import static ui.Resolve.removeIrrelevant;
import static ui.SelectClauses.selectClauses;
import static ui.Solution.allClauses;

public class Resolution
{
    public static boolean nil;

    public static void plResolution(List<Clause> initClauses, Clause negateGoalState, Clause goalState)
    {
        nil = false;

        List<Clause> clauses = new ArrayList<>(initClauses);

        List<Clause> sos = new ArrayList<>();
        sos.add(negateGoalState);
        sos = removeIrrelevant(sos);

        clauses.addAll( sos );

        clauses = removeIrrelevant(clauses);
        clauses = new ArrayList<>(removeRedundant(clauses));

        List<Clause> newClauses = new ArrayList<>();

        List<Clause> temp = new ArrayList<>(clauses);
        temp.removeAll(sos);
        initClauses = temp;

        while(true)
        {
            clauses = removeIrrelevant(clauses);
            sos = removeIrrelevant(sos);
            initClauses = removeIrrelevant(initClauses);

            initClauses = new ArrayList<>(removeRedundant(initClauses));

            for(Clause sosC : sos)
            {
                if(!allClauses.contains(sosC)){
                    allClauses.add(sosC);
                }
            }
            List<List<Clause>> selectedClauses = selectClauses( initClauses, sos );

            if (selectedClauses.isEmpty()) {
                System.out.println("[CONCLUSION]: " + goalState.toString().substring(2) + " is unknown");
                return;
            }

            for(List<Clause> cl : selectedClauses)
            {
                List<Clause> resolvents = plResolve(cl.get(0), cl.get(1));

                if(nil)
                {
                    System.out.println("[CONCLUSION]: " + goalState.toString().substring(2) + " is true");
                    return;
                }

                resolvents = removeIrrelevant(resolvents);

                List<Clause> removeFromSos = new ArrayList<>();

                for(Clause resC : resolvents)
                {
                    for(Clause sosC : sos)
                    {
                        if(checkSubset( resC, sosC ))
                        {
                            removeFromSos.add(sosC);
                        }

                    }
                }
                sos.removeAll(removeFromSos);

                List<Clause> removeFromClauses = new ArrayList<>();
                for(Clause resC : resolvents)
                {
                    for(Clause cC : clauses)
                    {
                        if(checkSubset( resC, cC ))
                        {
                            removeFromClauses.add(cC);
                        }

                    }
                }
                resolvents = new ArrayList<>(removeRedundant(resolvents));
                System.out.println(resolvents);
                System.out.println(clauses);
                if(clauses.containsAll(resolvents)) {
                    System.out.println("[CONCLUSION]: " + goalState.toString().substring(2) + " is unknown");
                    return;
                }

                clauses.removeAll(removeFromClauses);

                sos.addAll(resolvents);


                clauses.addAll(resolvents);
                newClauses.addAll(resolvents);

                temp.clear();
                temp = new ArrayList<>(removeRedundant(resolvents));
                temp = removeIrrelevant(temp);

                for(Clause c : temp)
                {
                    System.out.println(c.toString());
                }

                sos = new ArrayList<>(removeRedundant(sos));
                clauses = new ArrayList<>(removeRedundant(clauses));

                initClauses = clauses;
                initClauses.removeAll(sos);

            }

            /*
            List<Clause> tempClausesToDelete = new ArrayList<>();
            for(Clause c1 : newClauses) {
                for(Clause c2 : clauses) {
                    if(c1 != c2)
                    {
                        if(checkSubset(c2, c1))
                        {
                            //System.out.println("found subset " + c1 + c2 );
                            //tempClausesToDelete.add(c1);

                        }
                    }

                }
            }
            for(Clause c : tempClausesToDelete){
                newClauses.remove(c);
            }
            */

            //clauses.addAll(newClauses);
            //System.out.println("clauses: " + clauses);
            //System.out.println("new clauses " + newClauses);

        }
    }

    public static List<Clause> getNegate(Clause clause) {
        List<Clause> negateClauses = new ArrayList<>();

        for(Literal lit : clause.getliteralList()) {
            List<Literal> litList = new ArrayList<>();
            litList.add(lit.getNegate());

            negateClauses.add(new Clause(Solution.allClauses.size() + 1, litList));

        }

        return negateClauses;
    }

    public static boolean checkSubset(Clause c1, Clause c2)
    {
        if(c1 != c2 )
        {
            if(!c1.getliteralList().isEmpty() && !c2.getliteralList().isEmpty())
            {
                return c2.getliteralList().containsAll(c1.getliteralList());
            }

        }

        return false;
    }

}
