import java.io.IOException;

public class DeleteUser
{
    public static void deleteUser (String user) throws IOException {
        if( !AddUser.checkUserInFile(user) )
        {
            System.out.println(">>> provided username doesn't exist, try again");
            System.exit(1);
        }
        else
        {
            FileManager.deleteUserFromFile(user);
        }
    }
}
