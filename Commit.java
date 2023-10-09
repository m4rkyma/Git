import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Commit {

    private String author, desc, treeSha, prevCommitSha, nextSha;

    // This is to commit a file that has the previous sha
    public Commit(String prevCommitSha, String author, String desc) throws IOException {
        if (prevCommitSha!= "")
        {
            this.prevCommitSha = prevCommitSha;
        }
        treeSha = treeify();
        this.author = author;
        this.desc = desc;
        // if (prevCommitSha!= "")
        // {
        //     // String treeSha = getPrevCommitTree();
        //     File f = new File (treeSha);
        //     FileWriter fw = new FileWriter (f);
        //     // fw.write ("tree : " + )
        // }
    }

    // this is if you do not have the previous sha
    // public Commit(String author, String desc) throws IOException {
    //     treeSha = treeify();
    //     this.author = author;
    //     this.desc = desc;
    // }

    // gets the sha of the tree, pretty damn inportant
    public String treeify() throws IOException {
        Tree tree = new Tree();
        BufferedReader br = new BufferedReader(new FileReader("index"));
        String line;
        while ((line = br.readLine()) != null)
        {
            tree.add(line);
        }
        if (prevCommitSha != "" && prevCommitSha != null){
            tree.add(getPrevCommitTree());
        }
        br.close();
        tree.getContents();
        tree.writeToObjects();
        FileWriter fw = new FileWriter ("index");
        fw.write("");
        fw.close();
        return tree.getSha();
    }
    

    // Gets a date (not to homecoming)
    public String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    // This is actually what saves the file into the object folder, the new commit
    public void push() throws IOException {
        // if (prevCommitSha != "" && prevCommitSha != null && prevCommitSha != "da39a3ee5e6b4b0d3255bfef95601890afd80709")
        // {
        //     File z = new File (prevCommitSha);
        // }
        // if (prevCommitSha != "" && prevCommitSha != null && prevCommitSha != "da39a3ee5e6b4b0d3255bfef95601890afd80709")
        // {
        //     // File f = new File (prevCommitSha);
        //     File z = new File ("objects/"+prevCommitSha);
        //     int lineNumber = 3;
        //     BufferedReader reader = new BufferedReader(new FileReader(z));
        //     StringBuilder content = new StringBuilder();
        //     String line;

        //     int currentLine = 1;
        //     while ((line = reader.readLine()) != null) {
        //         if (currentLine == lineNumber) {
        //             content.append(getSha()).append("\n");
        //         }
        //         content.append(line).append("\n");
        //         currentLine++;
        //     }
        //     reader.close();

        //     FileWriter writer = new FileWriter(prevCommitSha);
        //     writer.write(content.toString());
        //     writer.close();
        //     // BufferedReader br = new BufferedReader(new FileReader(f));
        //     // FileWriter fw = new FileWriter (f);
        //     // String curline;
        //     // int cur = 0;
        //     // while (br.readLine() != null)
        //     // {
        //     //     cur++;
        //     //     if (cur == 3)
        //     //     {
        //     //         fw.write
        //     //     }
        //     // }
        // }
        // if (nextSha == null)
        // {
        //     nextSha = "";
        // }
        // FileWriter write = new FileWriter(new File("objects/" + getSha()));
        // write.write(treeSha + "\n" + prevCommitSha + "\n" + nextSha + "\n" + author + "\n" + getDate() + "\n" + desc);
        // write.close();
        
        File f = new File("objects/"+getSha());
        f.createNewFile();
        PrintWriter pw = new PrintWriter (f);
        pw.println(treeSha);
        if (prevCommitSha == null)
        {
            pw.println ();
        }
        else
        {
            pw.println (prevCommitSha);
        }
        File a = new File ("objects/"+prevCommitSha);
        if (a.exists())
        {
            int lineNumber = 3;
            BufferedReader reader = new BufferedReader(new FileReader(a));
            StringBuilder content = new StringBuilder();
            String line;

            int currentLine = 1;
            while ((line = reader.readLine()) != null) {
                if (currentLine == lineNumber) {
                    content.append(getSha()).append("\n");
                }
                content.append(line).append("\n");
                currentLine++;
            }
            reader.close();

            FileWriter writer = new FileWriter(a);
            writer.write(content.toString());
            writer.close();
            //
            File input_file = new File("objects/"+prevCommitSha);
            BufferedReader file_reader = new BufferedReader(new FileReader("objects/"+prevCommitSha));
            
            StringBuilder file_content = new StringBuilder();
            String line1;
            int line_number = 0;
            
            while ((line1 = file_reader.readLine()) != null) {
                line_number++;
                if (line_number != 4) {
                    file_content.append(line1).append("\n");
                }
            }
            file_reader.close();
            FileWriter file_writer = new FileWriter("objects/"+prevCommitSha);
            file_writer.write(file_content.toString());
            file_writer.close();
        }
        pw.println();
        pw.println (author);
        pw.println (getDate());
        pw.print (desc);
        pw.close();
    }

    // Gets the sha, this is code from blob.java
    public String getSha() {
        String input = (treeSha + "\n" + prevCommitSha + "\n" + author + "\n" + getDate() + "\n" + desc);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // This is the complicated part! Writes the new sha into the old file
    public void writeInNewCommit() throws IOException {
        File orginalFile = new File("objects/" + prevCommitSha);
        File newFile = new File("balls");
        BufferedReader reader = new BufferedReader(new FileReader(orginalFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
        String curr;
        int i = 0;

        while ((curr = reader.readLine()) != null) {
            if (i == 2) {
                writer.write(getSha());
            } else
                writer.write(curr);

            if (i != 5)
                writer.write("\n");
            i++;
        }
        writer.close();
        reader.close();
        newFile.renameTo(orginalFile);
    }
    public String getPrevCommitTree() throws IOException
    {
        File f = new File ("objects/" + prevCommitSha);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        br.close();
        return line;
    }
    public String getTreeSha() {
        return treeSha;
    }
    
    public String getPrevCommitSha() {
        return prevCommitSha;
    }
    
    public String getNextSha() {
        return nextSha;
    }
    public void makeHead() throws IOException
    {
        File f = new File ("HEAD");
        if (f.exists())
        {
            f.delete();
        }
        f.createNewFile();
        FileWriter fw = new FileWriter (f);
        fw.write(getSha());
        fw.close();
    }
    public static void main(String[] args) throws IOException {
        Git.initialize();
        Git.addFile("a.txt");
        Commit c = new Commit ("","mark","poo");
        // c.writeInNewCommit();
        System.out.println(c.getTreeSha());
        System.out.println(c.getTreeSha());
        c.push();
        System.out.println (c.getSha());
        Commit d = new Commit (c.getSha(),"mark2","poo2");
        d.push();
        Commit e = new Commit (d.getSha(),"mark3","poo3");
        e.push();

    }
}
