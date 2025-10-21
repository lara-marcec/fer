import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class AddUser
{
    public static final int SALT_LENGTH = 12;
    public static void addUser (String username) throws Exception {
        if( checkUserInFile(username) )
        {
            System.out.println(">>> error: username already taken");
            System.exit(1);
        }

        else
        {
            String newPass = enterPasword();

            byte[] salt = CryptoManager.generateSalt_or_IV(SALT_LENGTH);

            String passHash = CryptoManager.generatePassHash(newPass, salt);

            FileManager.addUserToFile(username, passHash, salt);
        }
    }

    public static boolean checkUserInFile (String username) throws IOException
    {
        BufferedReader bReader1 = new BufferedReader(new FileReader("tajna.txt"));

        String line = bReader1.readLine();

        while( line != null )
        {
            String lineUsername = line.split(" ")[0];
            lineUsername = lineUsername.substring(1);
            if( Objects.equals(lineUsername, username) )
            {
                return true;
            }
            line = bReader1.readLine();

        }
        bReader1.close();
        return false;
    }

    public static String enterPasword () throws Exception {
        Console console = System.console();
        try
        {
            if (console != null)
            {
                String newUserPass = String.valueOf(console.readPassword(">>> enter password: "));

                while( !(checkPassword(newUserPass)))
                {
                    newUserPass = String.valueOf(console.readPassword(">>> try again, enter password: "));
                }

                String newUserPassRepeat = String.valueOf(console.readPassword(">>> repeat password: "));

                if(! newUserPass.equals(newUserPassRepeat))
                {
                    System.out.println(">>> provided passwords don't match, try again");
                    System.exit(1);
                }
                else
                {
                    return newUserPass;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(">>> unexpected exception, try again");
            throw new Exception();
        }
        return null;
    }

    public static boolean checkPassword(String password)
    {
        if (password.length() < 8)
        {
            System.out.println(">>> password must have at least 8 characters");
            return false;
        }

        if (!password.matches(".*[A-Z].*"))
        {
            System.out.println(">>> password must have at least one uppercase letter");
            return false;
        }

        if (!password.matches(".*[a-z].*"))
        {
            System.out.println(">>> password must have at least one lowercase letter");
            return false;
        }

        if (!password.matches(".*\\d.*"))
        {
            System.out.println(">>> password must have at least one digit");
            return false;
        }

        return true;
    }

}
