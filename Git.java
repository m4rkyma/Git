import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Git{
    public static String Sha1(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
     public static void writeFile (String str, String fileName) throws IOException{
        FileWriter fw = new FileWriter (fileName);
        fw.write(str);
        fw.close();
    }
    public static String readFile (String fileName) throws IOException{
        FileReader fr = new FileReader (fileName);
        StringBuilder s = new StringBuilder ();
        while (fr.ready())
        {
            s.append((char)fr.read());
        }
        fr.close();
        return s.toString();
    }
    public static void convertToBlob (String fileName) throws IOException
    {
        String contents = readFile(fileName);
        writeFile (contents, Sha1(contents));
    }
    public static void main(String[] args) throws IOException {
        System.out.println(Sha1("1010"));
        convertToBlob("doesn'tmatter.txt");
    }
}