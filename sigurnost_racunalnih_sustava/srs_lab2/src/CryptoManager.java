import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class CryptoManager
{ ;
    public static final int ITERATION_COUNT = 10000;
    public static final int KEY_LENGTH = 256;

    public static byte[] generateSalt_or_IV(int length)
    {
        byte[] value = new byte[length];

        SecureRandom random = new SecureRandom();
        random.nextBytes(value);

        return value;
    }

    public static String generatePassHash(String pass, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        char[] charPass = pass.toCharArray();

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        PBEKeySpec keySpec = new PBEKeySpec(charPass, salt, ITERATION_COUNT, KEY_LENGTH);

        byte[] passHash = secretKeyFactory.generateSecret(keySpec).getEncoded();

        return Base64.getEncoder().encodeToString(passHash);
    }

}
