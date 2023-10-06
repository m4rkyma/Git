import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Blob{
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
    public Blob (String fileName) throws IOException
    {
        String contents = readFile(fileName);
        File f = new File ("objects/"+Sha1(contents));
        f.createNewFile();
        FileWriter fw = new FileWriter (f);
        fw.write(contents);
        fw.close();
    }
    public static String toSha1 (String fileName) throws IOException
    {
        String contents = readFile(fileName);
        return Sha1(contents);
    }
    public static String getContents (String fileName) throws IOException
    {
        String contents = readFile(fileName);
        return contents;
    }
    public static void main(String[] args) throws IOException {
        // System.out.println(Sha1("10101"));
        // convertToBlob("doesn'tmatter.txt");
        // System.out.println(toSha1("doesn'tmatter.txt"));
        Blob blob = new Blob ("a.txt");
    }
}