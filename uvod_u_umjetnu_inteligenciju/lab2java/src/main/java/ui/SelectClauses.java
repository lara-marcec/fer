package ui;

import java.util.ArrayList;
import java.util.List;

public class SelectClauses
{
    static List<List<Clause>> selectClauses(List<Clause> initialPremises, List<Clause> sos)
    {
        List<List<Clause>> selectedCaluses = new ArrayList<>();

        for(Clause sos_clause1 : sos)
        {
            for(Clause init_clause : initialPremises)
            {
                for(Literal init_literal : init_clause.getliteralList())
                // svaki literal u pocetnoj premisi usporedi s literalima u sos klauzuli
                {
                    for(Literal sos_literal : sos_clause1.getliteralList())
                    {
                        if(checkLiterals(sos_literal, init_literal))
                        {
                            List<Clause> clausePairTempList = new ArrayList<>();
                            clausePairTempList.add(sos_clause1);

                            clausePairTempList.add(init_clause);

                            //System.out.println(clausePairTempList + "\n");
                            if(! selectedCaluses.contains(clausePairTempList))  selectedCaluses.add(clausePairTempList);
                        }
                    }

                }
            }
            for(Clause sos_clause2 : sos)
            {
                for(Literal sos_literal1 : sos_clause2.getliteralList())
                // svaki literal u sos premisi usporedi s literalima u sos klauzuli
                {
                    for(Literal sos_literal2 : sos_clause1.getliteralList())
                    {
                        if(checkLiterals(sos_literal1, sos_literal2))
                        {
                            List<Clause> clausePairTempList = new ArrayList<>();
                            clausePairTempList.add(sos_clause1);

                            clausePairTempList.add(sos_clause2);

                            //System.out.println(clausePairTempList + "\n");

                            if(! selectedCaluses.contains(clausePairTempList))  selectedCaluses.add(clausePairTempList);
                        }

                    }

                }

            }
        }

        if(selectedCaluses.size() == 0 )
        {
            for(Clause init_clause1 : initialPremises)
            {
                for (Clause init_clause2 : initialPremises)
                {
                    for (Literal init_literal1 : init_clause1.getliteralList())
                    // svaki literal u pocetnoj premisi usporedi s literalima u sos klauzuli
                    {
                        for (Literal init_literal2 : init_clause2.getliteralList()) {
                            if (checkLiterals(init_literal1, init_literal2)) {
                                List<Clause> clausePairTempList = new ArrayList<>();
                                clausePairTempList.add(init_clause1);

                                clausePairTempList.add(init_clause2);

                                //System.out.println(clausePairTempList + "\n");
                                if (!selectedCaluses.contains(clausePairTempList))
                                    selectedCaluses.add(clausePairTempList);
                            }
                        }

                    }
                }
            }
        }

        System.out.println("selected: " + selectedCaluses);

        for(Clause c : Solution.allClauses)
        {
            Clause parent1 =
        }

        return selectedCaluses;
    }

    static boolean checkLiterals(Literal l1, Literal l2)
    {

        return l1.equals(l2.getNegate());
    }
}
