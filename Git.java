import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Git{
    public HashMap <String, String> blobList = new HashMap<>();
    public void initialize() throws IOException
    {
        if (!(new File ("index").isFile()))
        {
            File index = new File("index");
            index.createNewFile(); 
        }
        else
        {
            File index = new File("index");
            FileWriter fw = new FileWriter ("index");
            fw.flush();
            fw.close();
        }
        if (!(new File ("objects").isDirectory()))
        {
            File objects = new File("objects");
            objects.mkdir();
        }
    }
    public void add (String fileName) throws IOException
    {
        String s = Blob.Sha1(Blob.getContents(fileName));
        if (new File ("/Users/markma/Documents/Honors topics/Git/objects/" + s).exists())
        {
            return;
        }
        if (blobList.containsKey(fileName))
        {
            return;
        }
        System.out.println ("added " + fileName);
        Blob blob = new Blob (fileName);
        // FileWriter fw = new FileWriter ("index",true);
        // fw.write(fileName + " : " + Blob.Sha1(Blob.getContents(fileName)) + "\n");
        // fw.close();
        blobList.put(fileName, Blob.Sha1(Blob.getContents(fileName)));
        writeIndex();
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
    public void remove (String fileName) throws IOException
    {
        String s = Blob.Sha1(Blob.getContents(fileName));
        File f = new File ("/Users/markma/Documents/Honors topics/Git/objects/" + s);
        f.delete();
        blobList.remove(fileName, Blob.Sha1(Blob.getContents(fileName)));
        writeIndex();
    }
    public void writeIndex() throws IOException
    {
        FileWriter fw = new FileWriter ("index", true);
        PrintWriter pw = new PrintWriter (fw);
        for (String file : blobList.keySet())
        {
            pw.write (file + " : " + blobList.get(file) + "\n");
        }
        fw.close();
        pw.close();
    }
}