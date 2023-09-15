import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TreeTester {
    private static final String TEST_FILE_PATH = "tree_test";

    @BeforeAll
    static void setup() throws IOException {
        // Create a test file for the Tree class
        File testFile = new File(TEST_FILE_PATH);
        testFile.createNewFile();
    }
    static void cleanup() {
        // Delete the test file after tests are done
        File testFile = new File(TEST_FILE_PATH);
        testFile.delete();
    }
    void testAddBlob() throws IOException {
        Tree.add("blob : abc123 : file1.txt");
        assertTrue(Tree.hMap.containsKey("abc123"));
        assertEquals("file1.txt", Tree.hMap.get("abc123"));
    }
    void testAddTree() throws IOException {
        Tree.add("tree : def456");
        assertTrue(Tree.tree.contains("def456"));
    }
    void testRemoveBlob() {
        Tree.hMap.put("xyz789", "file2.txt");
        Tree.remove("xyz789");
        assertFalse(Tree.hMap.containsKey("xyz789"));
    }
    void testRemoveTree() {
        Tree.tree.add("ghi789");
        Tree.remove("ghi789");
        assertFalse(Tree.tree.contains("ghi789"));
    }
    void testGetContents() throws IOException {
        // Add some data to the Tree class
        Tree.add("blob : abc123 : file1.txt");
        Tree.add("tree : def456");

        Tree.getContents();

        String expectedOutput = "blob : abc123 : file1.txt\ntree : def456";
        assertEquals(expectedOutput, Tree.printSB());

        // Clean up the StringBuilder
        Tree.sb = new StringBuilder();
    }
    void testWriteToObjects() throws IOException {
        // Add some data to the Tree class
        Tree.add("blob : abc123 : file1.txt");
        Tree.add("tree : def456");

        Tree.getContents();
        Tree.writeToObjects();

        // Check if the objects file was created
        File objectsFile = new File("objects/" + Blob.Sha1(Tree.printSB()));
        assertTrue(objectsFile.exists());

        // Clean up the StringBuilder and delete the objects file
        Tree.sb = new StringBuilder();
        objectsFile.delete();
    }
}
