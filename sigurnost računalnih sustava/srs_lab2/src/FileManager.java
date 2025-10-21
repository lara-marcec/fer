import java.io.*;
import java.util.Base64;
import java.util.Objects;

public class FileManager
{
    public static void addUserToFile(String username, String password, byte[] salt) throws IOException {
        String saltEcoded = Base64.getEncoder().encodeToString(salt);

        String newLine = "0" + username + " " + password + saltEcoded  +  System.getProperty("line.separator");

        BufferedWriter bWriter = new BufferedWriter(new FileWriter("tajna.txt", true));

        bWriter.write(newLine);

        bWriter.close();

        System.out.println(">>> user " + username + " added successfully");
    }

    public static void deleteUserFromFile(String username) throws IOException
    {
        BufferedReader bReader1 = new BufferedReader(new FileReader("tajna.txt"));
        String line = bReader1.readLine();

        StringBuilder newFileContent = new StringBuilder();
        boolean userDeleted = false;

        while(line != null)
        {
            String lineUsername = line.split(" ")[0];
            lineUsername = lineUsername.substring(1);

            if( !Objects.equals(lineUsername, username) )
            {
                newFileContent.append(line).append( System.getProperty("line.separator"));
            }
            else
            {
                userDeleted = true;
            }
            line = bReader1.readLine();
        }
        bReader1.close();

        BufferedWriter bWriter = new BufferedWriter(new FileWriter("tajna.txt", false));

        bWriter.write(String.valueOf(newFileContent));

        bWriter.close();

        if(userDeleted)
        {
            System.out.println(">>> user " + username + " deleted successfully");
        }
        else
        {
            System.out.println("unexpected error, try again");
            System.exit(1);
        }
    }

    public static void  changePassInFile(String username, String pass, byte[] salt) throws IOException {
        String saltEcoded = Base64.getEncoder().encodeToString(salt);

        String newLine = "0" + username + " " + pass + saltEcoded;

        BufferedReader bReader1 = new BufferedReader(new FileReader("tajna.txt"));
        String line = bReader1.readLine();

        StringBuilder newFileContent = new StringBuilder();
        boolean passChanged = false;

        while(line != null)
        {
            String lineUsername = line.split(" ")[0];
            lineUsername = lineUsername.substring(1);

            if( !Objects.equals(lineUsername, username) )
            {
                newFileContent.append(line).append( System.getProperty("line.separator"));
            }
            else
            {
                passChanged = true;
                newFileContent.append(newLine).append( System.getProperty("line.separator"));
            }
            line = bReader1.readLine();
        }
        bReader1.close();

        BufferedWriter bWriter = new BufferedWriter(new FileWriter("tajna.txt", false));

        bWriter.write(String.valueOf(newFileContent));

        bWriter.close();

        if(passChanged)
        {
            System.out.println(">>> password changed for user " + username + " successfully");
        }
        else
        {
            System.out.println("unexpected error, try again");
            System.exit(1);
        }
    }

    public static void forcePasswordInFile(String username) throws IOException {
        BufferedReader bReader1 = new BufferedReader(new FileReader("tajna.txt"));
        String line = bReader1.readLine();

        StringBuilder newFileContent = new StringBuilder();
        boolean flagChanged = false;

        while(line != null)
        {
            String lineUsername = line.split(" ")[0];
            lineUsername = lineUsername.substring(1);

            if( !Objects.equals(lineUsername, username) )
            {
                newFileContent.append(line).append( System.getProperty("line.separator"));
            }
            else
            {
                flagChanged = true;
                String newLine = "1" + username + " " + line.split(" ")[1];
                newFileContent.append(newLine).append( System.getProperty("line.separator"));
            }
            line = bReader1.readLine();
        }
        bReader1.close();

        BufferedWriter bWriter = new BufferedWriter(new FileWriter("tajna.txt", false));

        bWriter.write(String.valueOf(newFileContent));

        bWriter.close();

        if(flagChanged)
        {
            System.out.println(">>> user " + username + " will be requested to change pass on next login");

        }
        else
        {
            System.out.println("unexpected error, try again");
           System.exit(1);
        }
    }
}
