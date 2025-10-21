import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

public class CryptoManager
{
    //konstante
    public static final int SALT_LENGTH = 12;
    public static final int IV_LENGTH = 16;
    public static final int TAG_LENGTH = 128;
    public static final int ITERATION_COUNT = 10000;
    public static final int KEY_LENGTH = 256;


    public static byte[] generateSalt_or_IV(int length)
    {
        byte[] value = new byte[length];

        SecureRandom random = new SecureRandom();
        random.nextBytes(value);

        return value;
    }

    public static SecretKey generateAESKey (char[] masterPass, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        //password based key derivation function 2
        //hash based message authentication code
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        PBEKeySpec keySpec = new PBEKeySpec(masterPass, salt, ITERATION_COUNT, KEY_LENGTH);

        return new SecretKeySpec(secretKeyFactory.generateSecret(keySpec).getEncoded(), "AES");
    }

    public static String encryptPair(String pair, String masterPass)
    {
        try
        {
            byte[] pairPlainText = pair.getBytes(StandardCharsets.UTF_8);
            byte[] salt = generateSalt_or_IV(SALT_LENGTH);
            byte[] iv = generateSalt_or_IV(IV_LENGTH);

            SecretKey aesSecretKey = generateAESKey(masterPass.toCharArray(), salt);

            Cipher encCipher = Cipher.getInstance("AES/GCM/NoPadding");

            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(TAG_LENGTH, iv);

            encCipher.init(Cipher.ENCRYPT_MODE, aesSecretKey, gcmParameterSpec);

            byte[] cipherTxt = encCipher.doFinal(pairPlainText);

            byte[] ivSaltAndCipherTxt = ByteBuffer
                    .allocate(iv.length + salt.length + cipherTxt.length)
                    .put(iv).put(salt).put(cipherTxt)
                    .array();

            return Base64.getEncoder().encodeToString(ivSaltAndCipherTxt);
        }
        catch( InvalidAlgorithmParameterException | NoSuchPaddingException
                | IllegalBlockSizeException | NoSuchAlgorithmException
                | InvalidKeySpecException | BadPaddingException
                | InvalidKeyException e )
        {
            System.out.println(">>> master password incorrect or integrity check failed");
            System.exit(1);
        }
        return null;
    }

    public static String decryptPair (String pair, String masterPass)
    {
        try
        {
            byte[] ivSaltAndCipherTxt = Base64.getDecoder().decode(pair);
            byte[] iv = new byte[IV_LENGTH];
            byte[] salt = new byte[SALT_LENGTH];
            byte[] cipherTxt = new byte[ivSaltAndCipherTxt.length - IV_LENGTH - SALT_LENGTH];

            ByteBuffer bBuffer = ByteBuffer.wrap(ivSaltAndCipherTxt);
            bBuffer.get(iv);
            bBuffer.get(salt);
            bBuffer.get(cipherTxt);

            SecretKey aesSecretKey = generateAESKey(masterPass.toCharArray(), salt);

            Cipher decCipher = Cipher.getInstance("AES/GCM/NoPadding");

            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(TAG_LENGTH, iv);

            decCipher.init(Cipher.DECRYPT_MODE, aesSecretKey, gcmParameterSpec);

            byte[] decryptedBytes = decCipher.doFinal(cipherTxt);


            return new String(decryptedBytes, StandardCharsets.UTF_8);

        }
        catch( InvalidAlgorithmParameterException | NoSuchPaddingException
                | IllegalBlockSizeException | NoSuchAlgorithmException
                | InvalidKeySpecException | BadPaddingException
                | InvalidKeyException | IllegalArgumentException | NegativeArraySizeException e )
        {
            System.out.println(">>> master password incorrect or integrity check failed");
            System.exit(1);
        }
        return null;
    }
}
