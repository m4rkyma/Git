import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 


public class Commit {


    private String sha, author, desc, treeSha, prevSha, nextSha;

    //This is to commit a file that has the previous sha
    public Commit(String sha, String author, String desc) throws IOException{
        prevSha = sha;
        treeSha = treeify();
        this.author = author;
        this.desc = desc;  
    }

    //this is if you do not have the previous sha
    public Commit(String author, String desc) throws IOException{
        treeSha = treeify();
        this.author = author;
        this.desc = desc; 
    }


    //gets the sha of the tree, pretty damn inportant
    public String treeify() throws IOException{
        Tree tree = new Tree();
        return tree.getSha();
    }


    //Gets a date (not to homecoming)
    public String getDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    //This is actually what saves the file into the object folder, the new commit
    public void push() throws IOException
    {
        FileWriter write = new FileWriter(new File("objects/" + sha));
        write.write(treeSha + "\n" + prevSha + "\n" + nextSha + "\n" + author + "\n" + getDate() + "\n" + desc);
        write.close();
    }

    public String getSha()
    {
        
    }



    
}

