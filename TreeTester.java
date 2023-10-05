
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TreeTester {
    private static final String TEST_FILE_PATH = "tree_test";

    static void setup() throws IOException {
        File testFile = new File(TEST_FILE_PATH);
        testFile.createNewFile();
    }

    static void cleanup() {
        File testFile = new File(TEST_FILE_PATH);
        testFile.delete();
    }

    @Test
    @DisplayName("[8] Test if initialize and objects are created correctly")
    void testInitialize() throws Exception {

        Git git = new Git();
        git.initialize();
        File file = new File("index");
        Path path = Paths.get("objects");

        assertTrue(file.exists());
        assertTrue(Files.exists(path));
    }
    // Adds data to Tree and checks if it's correct
    @Test
    void testAddBlob() throws IOException {
        Tree t = new Tree ();
        t.add("blob : f187ae69bdfb0f1510f108534b241abaa5a4ca72 : file1.txt");
        assertTrue(Tree.hMap.containsKey("f187ae69bdfb0f1510f108534b241abaa5a4ca72"));
        assertEquals("file1.txt", Tree.hMap.get("f187ae69bdfb0f1510f108534b241abaa5a4ca72"));
    }
    // Adds data to tree and checks if it's correct
    @Test
    void testAddTree() throws IOException {
        Tree t = new Tree ();
        t.add("tree : fa87e7501e7a09957de75317910a08559d45878f");
        assertTrue(Tree.tree.contains("fa87e7501e7a09957de75317910a08559d45878f"));
    }
    // Removes data from tree and checks if correct
    @Test
    void testRemoveBlob() {
        Tree.hMap.put("fa87e7501e7a09957de75317910a08559d45878f", "file2.txt");
        Tree.remove("fa87e7501e7a09957de75317910a08559d45878f");
        assertFalse(Tree.hMap.containsKey("fa87e7501e7a09957de75317910a08559d45878f"));
    }

    // Removes data from tree and checks if correct
    @Test
    void testRemoveTree() {
        Tree.tree.add("e914baad1d20942535ed0ac3857b43208e3d6bab");
        Tree.remove("e914baad1d20942535ed0ac3857b43208e3d6bab");
        assertFalse(Tree.tree.contains("e914baad1d20942535ed0ac3857b43208e3d6bab"));
    }
    // Gets contents of file and checks if it matches actual contents
    @Test
    void testGetContents() throws IOException {
        // Add data to Tree
        Tree t = new Tree ();
        t.add("blob : f187ae69bdfb0f1510f108534b241abaa5a4ca72 : file1.txt");
        t.add("tree : fa87e7501e7a09957de75317910a08559d45878f");

        Tree.getContents();
        // Compares expected and receieved
        String expectedOutput = "blob : f187ae69bdfb0f1510f108534b241abaa5a4ca72 : file1.txt\ntree : fa87e7501e7a09957de75317910a08559d45878f";
        assertEquals(expectedOutput, Tree.printSB());

        // Clean up SB
        Tree.sb = new StringBuilder();
    }
    // Copies over the contents and name to the objects folder
    @Test
    void testWriteToObjects() throws IOException {
        Tree t = new Tree ();
        t.add("blob : f187ae69bdfb0f1510f108534b241abaa5a4ca72 : file1.txt");
        t.add("tree : fa87e7501e7a09957de75317910a08559d45878f");

        Tree.getContents();
        Tree.writeToObjects();

        File objectsFile = new File("objects/" + Blob.Sha1(Tree.printSB()));
        assertTrue(objectsFile.exists());
        Tree.sb = new StringBuilder();
        objectsFile.delete();
    }
    @Test
    void testaddDirectoryBasic() throws IOException
    {
        //testcase 1
        File dir1 = new File ("dir1");
        dir1.mkdir();
        File a = new File ("dir1/a.txt");
        a.createNewFile();
        PrintWriter pw = new PrintWriter (a);
        pw.write("a");
        pw.close();
        File b = new File ("dir1/b.txt");
        b.createNewFile();
        PrintWriter p = new PrintWriter (b);
        p.write("b");
        p.close();
        File c = new File ("dir1/c.txt");
        c.createNewFile();
        PrintWriter l = new PrintWriter (c);
        l.write("c");
        l.close();
        Tree t = new Tree ();
        t.addDirectory("dir1");
        Tree.getContents();
        Tree.writeToObjects();
        assertTrue(Tree.printSB().contains("blob : 86f7e437faa5a7fce15d1ddcb9eaeaea377667b8 : /dir1/a.txt"));
        assertTrue(Tree.printSB().contains("blob : e9d71f5ee7c92d6dc9e92ffdad17b8bd49418f98 : /dir1/b.txt"));
        assertTrue(Tree.printSB().contains("blob : 84a516841ba77a5b4648de2cd0dfcb30ea46dbb4 : /dir1/c.txt"));
    }
    @Test
    void testaddDirectoryAdvanced() throws IOException
    {
        //testcase 1
        File dir1 = new File ("dir1");
        dir1.mkdir();
        File a = new File ("dir1/a.txt");
        a.createNewFile();
        PrintWriter pw = new PrintWriter (a);
        pw.write("a");
        pw.close();
        File b = new File ("dir1/b.txt");
        b.createNewFile();
        PrintWriter p = new PrintWriter (b);
        p.write("b");
        p.close();
        File c = new File ("dir1/c.txt");
        c.createNewFile();
        PrintWriter l = new PrintWriter (c);
        l.write("c");
        l.close();

        File d = new File ("dir1/d");
        d.mkdir();
        File e = new File ("dir1/e");
        e.mkdir();

        File f = new File ("dir1/d/f.txt");
        f.createNewFile();
        PrintWriter bruh = new PrintWriter (f);
        bruh.write("f");
        bruh.close();

        Tree t = new Tree ();
        t.addDirectory("dir1");
        Tree.getContents();
        Tree.writeToObjects();
        assertTrue(Tree.printSB().contains("blob : 86f7e437faa5a7fce15d1ddcb9eaeaea377667b8 : /dir1/a.txt"));
        assertTrue(Tree.printSB().contains("blob : e9d71f5ee7c92d6dc9e92ffdad17b8bd49418f98 : /dir1/b.txt"));
        assertTrue(Tree.printSB().contains("blob : 84a516841ba77a5b4648de2cd0dfcb30ea46dbb4 : /dir1/c.txt"));
        assertTrue(Tree.printSB().contains("tree : da39a3ee5e6b4b0d3255bfef95601890afd80709"));
        assertTrue(Tree.printSB().contains("blob : 4a0a19218e082a343a1b17e5333409af9d98f0f5 : /dir1/d/f.txt"));
    }
}
