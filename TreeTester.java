import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TreeTester {
    private static final String TEST_FILE_PATH = "tree_test";

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
        Tree.add("blob : f187ae69bdfb0f1510f108534b241abaa5a4ca72 : file1.txt");
        assertTrue(Tree.hMap.containsKey("abc123"));
        assertEquals("file1.txt", Tree.hMap.get("f187ae69bdfb0f1510f108534b241abaa5a4ca72"));
    }

    void testAddTree() throws IOException {
        Tree.add("tree : fa87e7501e7a09957de75317910a08559d45878f");
        assertTrue(Tree.tree.contains("fa87e7501e7a09957de75317910a08559d45878f"));
    }
}
