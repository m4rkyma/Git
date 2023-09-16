import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Git{
    // public HashMap <String, String> blobList = new HashMap<>();
    public static void initialize() throws IOException
    {
        if (!(new File ("index").isFile()))
        {
            File index = new File("index");
            index.createNewFile(); 
        }
        // else
        // {
        //     File index = new File("index");
        //     FileWriter fw = new FileWriter ("index");
        //     fw.flush();
        //     fw.close();
        // }
        if (!(new File ("objects").isDirectory()))
        {
            File objects = new File("objects");
            objects.mkdir();
        }
    }
    public static String readFile(String from) throws IOException {
        return Files.readString(Path.of(from));
    }
    public static void add (String fileName) throws IOException
    {
        String s = Blob.Sha1(Blob.getContents(fileName));
        if (new File ("objects/" + s).exists())
        {
            return;
        }
        System.out.println ("added " + fileName);
        Blob blob = new Blob (fileName);
        // File f = new File (fileName);

        FileWriter fw = new FileWriter ("index",true);
        // PrintWriter pw = new PrintWriter (fw);
        // if (!readFile("index").contains(s)) {
        //     if (readFile("index").isEmpty())
        //         pw.print(f.getName() + " : " + s);
        //     else
        //         pw.print("\n" + f.getName() + " : " + s);
        // }
        fw.write(fileName + " : " + Blob.Sha1(Blob.getContents(fileName)) + "\n");
        fw.close();
        // pw.close();
        // blobList.put(fileName, Blob.Sha1(Blob.getContents(fileName)));
        // writeIndex();
        // File index = new File("index");
        // index.createNewFile();
        // if (new File ("index").isFile())
        // {
        //     FileWriter fw = new FileWriter ("index",true);
        //     fw.write(fileName + " : " + Blob.Sha1(Blob.getContents(fileName)) + "\n");
        //     fw.close();
        // }
        // else
        // {
        //     System.out.println ("poop");
        // }
    }
    public static void remove (String fileName) throws IOException
    {
        String s = Blob.Sha1(Blob.getContents(fileName));
        File bruh = new File ("objects/" + s);
        if (bruh.exists())
        {
            bruh.delete();
        }
        File f = new File ("index");
        File temp = new File ("temp.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
        String lineToRemove = fileName + " : " + Blob.Sha1(Blob.getContents(fileName));
        String currentLine;

        while((currentLine = br.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
                bw.write(currentLine + System.getProperty("line.separator"));
        }
        bw.close();
        br.close();
        temp.renameTo(f);
}
}