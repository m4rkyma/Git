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
        if (!(new File ("objects").isDirectory()))
        {
            File objects = new File("objects");
            objects.mkdir();
        }
    }
    public static String readFile(String from) throws IOException {
        return Files.readString(Path.of(from));
    }
    public static void addFile (String fileName) throws IOException
    {
        String s = Blob.Sha1(Blob.getContents(fileName));
        if (new File ("objects/" + s).exists())
        {
            return;
        }
        System.out.println ("added " + fileName);
        Blob blob = new Blob (fileName);
        FileWriter fw = new FileWriter ("index",true);
        fw.write("blob : " + Blob.Sha1(Blob.getContents(fileName)) + " : " + fileName + "\n");
        fw.close();
    }
    public static void addTree (String fileName) throws IOException
    {
        Tree t = new Tree ();

        // if (new File ("objects/" + s).exists())
        // {
        //     return;
        // }
        System.out.println ("added " + fileName);
        FileWriter fw = new FileWriter ("index",true);
        fw.write("tree : " + t.addDirectory(fileName) + " : " + fileName + "\n");
        fw.close();
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
        boolean deleted = false;
        while((currentLine = br.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
                bw.write(currentLine + System.getProperty("line.separator"));
                deleted = true;
        }
        bw.close();
        br.close();
        temp.renameTo(f);
        // if (deleted)
        // {
        //     FileWriter fw = new FileWriter (f,true);
        //     fw.write("*deleted*"+fileName+ "\n");
        //     fw.close();
        // }
    }
    public void editFile(String fileName) throws IOException
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter("index", true));
        bw.write("*edited* : " + fileName);  
        bw.close();
    }
    public void deleteFile(String fileName) throws IOException
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter("index", true));
        bw.write("*deleted* : " + fileName);  
        bw.close();
    }
public static void main(String[] args) throws IOException {
    Git g = new Git ();
    g.initialize();
    g.addFile ("a.txt");
    // g.remove("a.txt");
    // g.addTree ("blu");

}
}