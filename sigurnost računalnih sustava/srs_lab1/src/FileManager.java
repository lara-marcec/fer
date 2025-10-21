import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

public class FileManager
{
    public static String lineForWriting;

    public static void putInFile(String encryptedPair, String domain, String masterPass) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException
    {

        BufferedReader bReader1 = new BufferedReader(new FileReader("tajna.txt"));
        String line = bReader1.readLine();

        boolean domainExists = false;

        while(line != null)
        {
            String decryptedLinePair = CryptoManager.decryptPair(line, masterPass);
            String lineDomain = decryptedLinePair.split("šć")[0];

            if( Objects.equals(lineDomain, domain) )
            {
                //domena i lozinka vec postoje u datoteci
                //potrebno je zamijeniti postojecu zadanom
                lineForWriting = line;
                domainExists = true;

                break;
            }
            else
            {
                line = bReader1.readLine();
            }
        }
        bReader1.close();

        if( domainExists )
        {   //overwrite liniju u datoteci sa postojecom domenom
            //zapravo zamijenjujem cijeli sadrzaj datoteke ali promjena je u jednoj liniji

            BufferedReader bReader2 = new BufferedReader(new FileReader("tajna.txt"));
            StringBuilder newFileContent = new StringBuilder();

            //preskacem prvu liniju, ona je za provjeru master lozinke
            String line2 = bReader2.readLine();
            newFileContent.append(line2).append( System.getProperty("line.separator"));

            line2 = bReader2.readLine();

            while( line2 != null )
            {
                if( line2.equals(lineForWriting) )
                {
                    newFileContent.append(encryptedPair).append( System.getProperty("line.separator"));
                }
                else
                {
                    newFileContent.append(line2).append( System.getProperty("line.separator"));
                }
                line2 = bReader2.readLine();
            }

            BufferedWriter bWriter = new BufferedWriter(new FileWriter("tajna.txt"));
            bWriter.write(String.valueOf(newFileContent));

            bWriter.close();
            bReader2.close();
        }
        else
        {   //inace ako domena ne postoji, dodaj novu liniju na kraj datoteke
            BufferedWriter bWriter = new BufferedWriter(new FileWriter("tajna.txt", true));

            String newLine = encryptedPair + System.getProperty("line.separator");
            bWriter.write(newLine);

            bWriter.close();
        }

        System.out.println(">>> stored password for " + domain);
    }

    public static void getFromFile(String masterPass, String targetDomain) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException
    {
        BufferedReader bReader1 = new BufferedReader(new FileReader("tajna.txt"));

        bReader1.readLine(); //preskacemo prvu liniju
        String line = bReader1.readLine();

        String pass = "";

        while( line != null )
        {
            String decryptedFileLine = CryptoManager.decryptPair(line, masterPass);
            String domainFromLine = decryptedFileLine.split("šć")[0];

            if( Objects.equals(domainFromLine, targetDomain) )
            {
                pass = decryptedFileLine.split("šć")[1];
                break;
            }
            else
            {
                line = bReader1.readLine();
            }
        }

        bReader1.close();

        if( pass.isEmpty() )
        {
            System.out.println(">>> master password incorrect or integrity check failed");
            System.exit(1);
        }
        else
        {
            System.out.println(">>> password for " + targetDomain + " is: " + pass);
        }
    }
}
