import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Login
{
    public static void main(String[] args) throws Exception {
        File f = new File("tajna.txt");

        if(!(f.exists() && f.isFile()))
        {
            new FileWriter("tajna.txt");
        }

        if(Objects.equals(args[0], "login"))
        {
            loginUser(args[1]);
        }
        else
        {
            System.out.println(">>> input not valid, try again");
        }
    }

    public static void loginUser(String username) throws Exception {
        if( !AddUser.checkUserInFile(username) )
        {
            System.out.println(">>> username or password incorrect");
            System.exit(1);
        }
        else
        {
            int i = 0;
            while(i < 3)
            {
                String enteredPassword = enterPassword();
                if(checkUserPass(username, enteredPassword))
                {
                    if( checkForcePassChange(username) )
                    {
                        forceChangePass(username);
                    }
                    ProcessBuilder pb = new ProcessBuilder("notepad.exe");
                    pb.start();
                    return;
                }
                else
                {
                    i++;
                }
            }
            System.exit(1);

        }

    }

    public static String enterPassword() throws Exception {
        Console console = System.console();
        try
        {
            if (console != null)
            {
                return String.valueOf(console.readPassword(">>> enter password: "));
            }
        }
        catch (Exception e)
        {
            System.out.println(">>> unexpected exception, try again");
            throw new Exception();
        }
        return null;
    }

    public static boolean checkUserPass(String username, String password) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        BufferedReader bReader1 = new BufferedReader(new FileReader("tajna.txt"));

        String line = bReader1.readLine();

        while( line != null )
        {
            String lineUsername = line.split(" ")[0];
            lineUsername = lineUsername.substring(1);
            if( Objects.equals(lineUsername, username) )
            {
                int len = line.split(" ")[1].length();
                String saltEncoded = line.split(" ")[1].substring(len - 16, len);

                byte[]salt = Base64.getDecoder().decode(saltEncoded.getBytes(UTF_8));

                String originalHashPass =  line.split(" ")[1].substring(0, len - 16);

                String newHashPass = CryptoManager.generatePassHash(password, salt);

                if( !originalHashPass.equals(newHashPass) )
                {
                    System.out.println(">>> username or password incorrect");
                    return false;
                }
                else
                {
                    return true;
                }

            }
            line = bReader1.readLine();

        }
        bReader1.close();

        return false;
    }

    public static boolean checkForcePassChange(String username) throws IOException {
        BufferedReader bReader1 = new BufferedReader(new FileReader("tajna.txt"));

        String line = bReader1.readLine();

        while( line != null )
        {
            String lineUsername = line.split(" ")[0];
            String forcePassFlag = String.valueOf(lineUsername.charAt(0));
            lineUsername = lineUsername.substring(1);


            if( Objects.equals(lineUsername, username) )
            {
                if(forcePassFlag.equals("0"))
                {
                    return false;
                }
                else if(forcePassFlag.equals("1"))
                {
                    return true;
                }

            }
            line = bReader1.readLine();

        }
        bReader1.close();

        return false;
    }

    public static void forceChangePass(String username) throws Exception {
        System.out.println(">>> password change required");

        String newPass = AddUser.enterPasword();

        byte[] salt = CryptoManager.generateSalt_or_IV(AddUser.SALT_LENGTH);

        String passHash = CryptoManager.generatePassHash(newPass, salt);

        while( !checkOldPassword(username, newPass) ) {
            newPass = AddUser.enterPasword();

            salt = CryptoManager.generateSalt_or_IV(AddUser.SALT_LENGTH);

            passHash = CryptoManager.generatePassHash(newPass, salt);

        }

        FileManager.changePassInFile(username, passHash, salt);

    }

    public static boolean checkOldPassword(String username, String newPass) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        BufferedReader bReader1 = new BufferedReader(new FileReader("tajna.txt"));
        String line = bReader1.readLine();

        while(line != null)
        {
            String lineUsername = line.split(" ")[0];
            lineUsername = lineUsername.substring(1);

            if( Objects.equals(lineUsername, username) )
            {
                int len = line.split(" ")[1].length();
                String saltEncoded = line.split(" ")[1].substring(len - 16, len);

                byte[]salt = Base64.getDecoder().decode(saltEncoded.getBytes(UTF_8));

                String originalHashPass =  line.split(" ")[1].substring(0, len - 16);

                String newHashPass =  CryptoManager.generatePassHash(newPass, salt);

                if( originalHashPass.equals(newHashPass) )
                {
                    System.out.println(">>> incorrect input,  try again with a different password");
                    return false;
                }

            }
            line = bReader1.readLine();
        }
        bReader1.close();
        return true;
    }
}
