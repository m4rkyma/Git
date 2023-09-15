
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

    void testAddBlob() throws IOException {
        Tree.add("blob : f187ae69bdfb0f1510f108534b241abaa5a4ca72 : file1.txt");
        assertTrue(Tree.hMap.containsKey("f187ae69bdfb0f1510f108534b241abaa5a4ca72"));
        assertEquals("file1.txt", Tree.hMap.get("f187ae69bdfb0f1510f108534b241abaa5a4ca72"));
    }

    void testAddTree() throws IOException {
        Tree.add("tree : fa87e7501e7a09957de75317910a08559d45878f");
        assertTrue(Tree.tree.contains("fa87e7501e7a09957de75317910a08559d45878f"));
    }

    void testRemoveBlob() {
        Tree.hMap.put("fa87e7501e7a09957de75317910a08559d45878f", "file2.txt");
        Tree.remove("fa87e7501e7a09957de75317910a08559d45878f");
        assertFalse(Tree.hMap.containsKey("fa87e7501e7a09957de75317910a08559d45878f"));
    }

    @Test
    void testRemoveTree() {
        Tree.tree.add("e914baad1d20942535ed0ac3857b43208e3d6bab");
        Tree.remove("e914baad1d20942535ed0ac3857b43208e3d6bab");
        assertFalse(Tree.tree.contains("e914baad1d20942535ed0ac3857b43208e3d6bab"));
    }
}
