package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ui.Resolution.getNegate;

public class Solution {

	private static String alg;
	private static String clausesFile;
	private static String commandFile;
	static List<Clause> allClauses;

	public static void main(String ... args) throws IOException {
		allClauses = new ArrayList<>();
		int i = 0;

		for (String arg : args)
		{
			if (Objects.equals(arg, "resolution"))
			{
				alg = arg;
				clausesFile = args[i + 1];
			}
			else if (Objects.equals(arg, "cooking"))
			{
				alg = arg;
				clausesFile = args[i + 1];
				commandFile = args[i + 2];
			}
			i++;

		}

		List<Clause> initClausesList;
		if(Objects.equals(alg, "resolution"))
		{
			initClausesList = DataLoader.loadClausesData(clausesFile);

			Clause goalClause = initClausesList.get(initClausesList.size()-1);
			initClausesList.remove(goalClause);

			for(Clause c : initClausesList)
			{
				System.out.println(c.toString());
			}
			allClauses.addAll(initClausesList);

			List<Clause> negateGoalState = getNegate(goalClause);

			allClauses.addAll(negateGoalState);

			System.out.println(negateGoalState.get(0).toString());

			System.out.println("===============");

			Resolution.plResolution(initClausesList, negateGoalState.get(0), goalClause);
		}

		else if( Objects.equals(alg, "cooking"))
		{
			initClausesList = DataLoader.loadClausesData(clausesFile);

			List<Command> commandsMap = DataLoader.loadCommandsData(commandFile);

			Cooking.cooking(commandsMap, initClausesList);

		}
	}

}
