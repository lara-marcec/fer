import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Objects;
import java.util.Scanner;

public class PswrdMng {

    public static final int RAND_DATA_LENGTH = 32;

    public static boolean pswdMngInitialized = false;

    public static void main( String[] args )
    {
        //provjeravam je li datoteka password managera vec stvorena
        File f = new File("tajna.txt");
        if( f.exists() && f.isFile() )
        {
            pswdMngInitialized = true;
        }

        try{
            if( Objects.equals(args[0], "init") )
            {
                if(args.length == 2)
                {
                    String masterPass = args[1];
                    initialize(masterPass);
                }
                else
                {
                    throw new IllegalArgumentException();
                }

            }
            else if( Objects.equals(args[0], "put") )
            {
                if( args.length == 4 )
                {
                    String masterPass = args[1];
                    String domain = args[2];
                    String pass = args[3];

                    if( pswdMngInitialized )
                    {
                        put(masterPass, domain, pass);
                    }
                    else
                    {
                        System.out.println(">>> action cannot be completed because the password manager is not initialized");
                        System.out.println(">>> initialize the password manager and then try again");
                    }
                }
                else
                {
                    throw new IllegalArgumentException();
                }
            }
            else if( Objects.equals(args[0], "get") )
            {
                if( args.length == 3 )
                {
                    String masterPass = args[1];
                    String domain = args[2];

                    if( pswdMngInitialized )
                    {
                        get(masterPass, domain);
                    }
                    else
                    {
                        System.out.println(">>> action cannot be completed because password manager is not initialized.");
                        System.out.println(">>> initialize the password manager and then try again.");
                    }
                }
                else
                {
                    throw new IllegalArgumentException();
                }
            }
            else
            {
                System.out.println(">>> invalid input, try again");
            }
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(">>> invalid number of arguments for action: " + args[0]);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public static void initialize(String masterPass) throws IOException
    {
        File file = new File("tajna.txt");
        Scanner sc = new Scanner(System.in);

        if ( file.exists() && file.isFile() )
        {
            System.out.println(">>> password manager is already initialized");
            System.out.println(">>> do you wish to overwrite the old one and create a new password manager? [y/n]");

            String scInput = sc.nextLine().trim().toLowerCase();

            if( scInput.equals("y") )
            {
                //stvaramo novi password manager sa potencijalno novom master lozinkom
                initializeNewManager(masterPass);
            }
            else if( scInput.equals("n") )
            {
                //koristimo stari password manager sa starom zaporkom
                System.out.println(">>> continuing with the existing password manager");
                pswdMngInitialized = true;
            }
            else
            {
                System.out.println(">>> invalid input, try again");
            }
        }
        else
        {
            //ako ne postoji datoteka, stvaramo novi password manager
            initializeNewManager(masterPass);
        }

    }

    public static void initializeNewManager(String masterPass) throws IOException {
        try
        {
            FileWriter fWriter = new FileWriter("tajna.txt");

            byte[] randomData;

            //koristim istu funkciju kojom generiram salt i iv za generiranje nasumicnog podatka
            //taj nasumicni podatak kriptiram pomocu master lozinke koja je upisana prilikom inicijalizacije managera
            randomData = CryptoManager.generateSalt_or_IV(RAND_DATA_LENGTH);

            String randomDataString = Base64.getEncoder().encodeToString(randomData);
            String encryptedData = CryptoManager.encryptPair(randomDataString, masterPass);

            fWriter.write(encryptedData + System.getProperty("line.separator"));

            pswdMngInitialized = true;
            System.out.println(">>> password manager successfully initialized");

            fWriter.close();

        }
        catch ( IOException e)
        {
            e.printStackTrace();
        }

    }

    public static void put(String masterPass, String domain, String domainPass)
    {
        try
        {
            //provjeram je li master lozinka ispravna tj jednaka onoj zadanoj prilikom inicijalizacije
            checkMasterPass(masterPass);

            //kombinacijom domene i lozinke stvaram jedan zapis koji se kriptira
            //domena i lozinka su u zapisu odvojene zadanim izrazom
            String pairDomainPass = domain + "šć" + domainPass;
            String encryptedPair = CryptoManager.encryptPair(pairDomainPass, masterPass);
            FileManager.putInFile(encryptedPair, domain, masterPass);
        }
        catch( InvalidAlgorithmParameterException | NoSuchPaddingException
                | IllegalBlockSizeException | NoSuchAlgorithmException
                | InvalidKeySpecException | BadPaddingException
                | InvalidKeyException | IOException e )
        {
            e.printStackTrace();
        }

    }

    public static void get(String masterPass, String domain)
    {
        try
        {
            FileManager.getFromFile(masterPass, domain);
        }
        catch( InvalidAlgorithmParameterException | NoSuchPaddingException
                | IllegalBlockSizeException | NoSuchAlgorithmException
                | InvalidKeySpecException | BadPaddingException
                | InvalidKeyException | IOException e )
        {
            e.printStackTrace();
        }
    }

    public static void checkMasterPass(String masterPass) throws IOException {
        //dekriptiranje prve linije datoteke pomocu unesene master lozinke
        //ako dekripcija uspije, znaci da je unesena master lozinka ispravna
        BufferedReader bReader = new BufferedReader(new FileReader("tajna.txt"));
        String line = bReader.readLine();

        CryptoManager.decryptPair(line, masterPass);
        bReader.close();
    }
}
