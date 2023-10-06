import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.Assert.assertNull;
import java.io.File;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CommitTest {
    @BeforeAll
    public static void setUp() throws IOException
    {
        // File a = new File ("a.txt");
        // a.createNewFile();
        // PrintWriter pw = new PrintWriter (a);
        // pw.write("a");
        // pw.close();
    }
    @Test
    void testCommit() throws IOException {
        Commit github = new Commit("","kevin", "cool file");
        github.push();

        Path location = Paths.get("objects/" + github.getSha());

        String correctFill = "";

        assertTrue("File contents do not match", correctFill.equals(location.toString()));
    }
    @Test
    void testOneCommit() throws IOException {
        // Git g = new Git();
        File a = new File ("a.txt");
        a.createNewFile();
        PrintWriter pw = new PrintWriter (a);
        pw.write("a");
        pw.close();
        File b = new File ("b.txt");
        b.createNewFile();
        PrintWriter w = new PrintWriter (b);
        w.write("b");
        w.close();

        Git.initialize();
        Git.addFile("a.txt");
        Git.addFile("b.txt");
        // File f = new File ("index");
        Commit c = new Commit("","kevin", "1 cool file");
        // assertNull (c.getPrevCommitTree());
        // assertEquals ("Prev sha is correct", )
        assertNotNull(c.getSha());
        assertNotNull(c.getTreeSha());
        assertNull(c.getPrevCommitSha());
        assertNull(c.getNextSha());
    }
    @Test
    void testTwoCommits() throws IOException {
        File a = new File ("a.txt");
        a.createNewFile();
        PrintWriter pw = new PrintWriter (a);
        pw.write("a");
        pw.close();
        File b = new File ("b.txt");
        b.createNewFile();
        PrintWriter w = new PrintWriter (b);
        w.write("b");
        w.close();
        
        File f = new File ("f");
        f.mkdir();
        File c = new File ("f/c.txt");
        c.createNewFile();
        PrintWriter aa = new PrintWriter (c);
        aa.write("c");
        aa.close();
        File d = new File ("f/d.txt");
        d.createNewFile();
        PrintWriter dd = new PrintWriter (d);
        dd.write("d");
        dd.close();

        Git.initialize();
        Git.addFile("a.txt");
        Git.addFile("b.txt");
        Commit commit1 = new Commit("","kevin", "1 cool file");
        commit1.push();
        Git.initialize();
        Git.addTree("f");
        Commit commit2 = new Commit(commit1.getSha(),"mark", "2 cool file");
        commit2.push();
        assertNotNull(commit1.getSha());
        assertNotNull(commit1.getTreeSha());
        assertNull(commit1.getPrevCommitSha());
        assertNotNull(commit1.getNextSha());

        assertNotNull(commit2.getSha());
        assertNotNull(commit2.getTreeSha());
        assertNotNull(commit2.getPrevCommitSha());
        assertNull(commit2.getNextSha());
    }

    @Test
    void testFourCommits() throws IOException {
        File d = new File ("d");
        d.mkdir();
        File e = new File ("e");
        e.mkdir();
        File[] files = new File[8];
        PrintWriter[] writers = new PrintWriter[8];
        String[] fileNames = {"d/a.txt", "d/b.txt", "e/c.txt", "e/d.txt", "e.txt", "f.txt", "g.txt", "h.txt"};
        String[] fileContents = {"Data for file a", "Data for file b", "Data for file c", "Data for file d", "Data for file e", "Data for file f", "Data for file g", "Data for file h"};

        for (int i = 0; i < 8; i++) {
            files[i] = new File(fileNames[i]);
            writers[i] = new PrintWriter(files[i]);
            writers[i].write(fileContents[i]);
            writers[i].close();
        }
        File fileA = files[0];
        File fileB = files[1];
        File fileC = files[2];
        File fileD = files[3];
        File fileE = files[4];
        File fileF = files[5];
        File fileG = files[6];
        File fileH = files[7];

        

        Git.initialize();
        Git.addTree ("d");
        // Git.addFile(fileA.getName());
        // Git.addFile(fileB.getName());
        Commit a = new Commit("","kevin", "1 cool file");
        a.push();

        Git.initialize();
        Git.addTree ("e");
        // Git.addFile(fileC.getName());
        // Git.addFile(fileD.getName());
        Commit b = new Commit(a.getSha(),"mark", "2 cool file");
        b.push();
        Git.initialize();
        Git.addFile(fileE.getName());
        Git.addFile(fileF.getName());
        Commit c = new Commit(b.getSha(),"william", "3 cool file");
        c.push();
        Git.initialize();
        Git.addFile(fileG.getName());
        Git.addFile(fileH.getName());
        Commit commit4 = new Commit(c.getSha(),"sammy", "4 cool file");
        commit4.push();

        assertNotNull(a.getSha());
        assertNotNull(a.getTreeSha());
        assertNull(a.getPrevCommitSha());
        assertNotNull(a.getNextSha());

        assertNotNull(b.getSha());
        assertNotNull(b.getTreeSha());
        assertNotNull(b.getPrevCommitSha());
        assertNotNull(b.getNextSha());

        assertNotNull(c.getSha());
        assertNotNull(c.getTreeSha());
        assertNotNull(c.getPrevCommitSha());
        assertNotNull(c.getNextSha());

        assertNotNull(commit4.getSha());
        assertNotNull(commit4.getTreeSha());
        assertNotNull(commit4.getPrevCommitSha());
        assertNull(commit4.getNextSha());
    }
    @Test
    void testCommitWithPrevSha() throws IOException {

    }

    @Test
    void testPush() throws IOException {

    }

    @Test
    void testGetSha() throws IOException {
    }

    @Test
    void testWriteInNextSha() throws IOException {
    }
}
