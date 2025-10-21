import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ChangePass
{
    public static final int SALT_LENGTH = 12;

    public static void changePass (String user) throws Exception {
        if( !AddUser.checkUserInFile(user) )
        {
            System.out.println(">>> provided username doesn't exist, try again");
            System.exit(1);
        }
        else
        {

            String newPass = AddUser.enterPasword();

            byte[] salt = CryptoManager.generateSalt_or_IV(SALT_LENGTH);

            String passHash = CryptoManager.generatePassHash(newPass, salt);

            FileManager.changePassInFile(user, passHash, salt);
        }
    }


}
