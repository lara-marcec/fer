package ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class DataLoader
{
    public static List<Clause> loadClausesData(String clausesFile) throws IOException
    {
        Path clausesFilePath = Paths.get(clausesFile);
        BufferedReader br = new BufferedReader(new FileReader(clausesFilePath.toFile()));

        String line = br.readLine();

        List<Clause> clauseList = new ArrayList<>();
        int index = 1;
        while(line != null)
        {
            if(line.charAt(0) != '#')
            {
                line = line.toLowerCase(Locale.ROOT);
                String[] splitLine = line.split(" v ");

                List<Literal> literalList = new ArrayList<>();

                for(String s : splitLine) {
                    Literal l = new Literal(!s.contains("~"), s.replaceAll("~", ""));
                    literalList.add(l);
                }

                clauseList.add(new Clause(index, literalList));


            }
            index++;
            line = br.readLine();
        }

        return clauseList;
    }


    public static List<Command> loadCommandsData(String commandFile) throws IOException
    {
        Path commandsFilePath = Paths.get(commandFile);
        BufferedReader br = new BufferedReader(new FileReader(commandsFilePath.toFile()));

        String line = br.readLine();

        int index = 1;
        List<Command> commandList = new ArrayList<>();

        while(line != null)
        {
            if(line.charAt(0) != '#')
            {
                line = line.toLowerCase(Locale.ROOT);
                String[] splitLine = line.split(" ");

                String command = splitLine[splitLine.length-1];
                List<Literal> newlist = new ArrayList<>();
                for(String s : splitLine)
                {
                    if(!Objects.equals(s, command) && !Objects.equals(s, "v"))
                    {
                        Literal newLiteral;
                        if (s.charAt(0) == '~')
                        {
                            newLiteral = new Literal(false, s.substring(1));
                        }
                        else
                        {
                            newLiteral = new Literal(true, s);
                        }
                        newlist.add(newLiteral);
                    }
                }

                Clause newClause = new Clause(index, newlist);
                commandList.add(new Command(newClause, command));

                index++;
            }
            line = br.readLine();
        }
        return commandList;
    }
}
