import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Git{
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
        Blob blob = new Blob (fileName);
        FileWriter fw = new FileWriter ("index",true);
        fw.write(fileName + " : " + Blob.Sha1(Blob.getContents(fileName)) + "\n");
        fw.close();
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

    }
}