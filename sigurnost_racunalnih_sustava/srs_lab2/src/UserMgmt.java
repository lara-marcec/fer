import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class UserMgmt
{
    public static void main(String[] args) throws Exception
    {

        File f = new File("tajna.txt");

        if(!(f.exists() && f.isFile()))
        {
            new FileWriter("tajna.txt");
        }

        if(Objects.equals(args[0], "add"))
        {
            AddUser.addUser(args[1]);
        }
        else if(Objects.equals(args[0], "del"))
        {
            DeleteUser.deleteUser(args[1]);
        }
        else if(Objects.equals(args[0], "passwd"))
        {
            ChangePass.changePass(args[1]);
        }
        else if(Objects.equals(args[0], "forcepass"))
        {
            ForceChange.forceChangePass(args[1]);
        }
        else
        {
            System.out.println(">>> input not valid, try again");
        }

    }

}
