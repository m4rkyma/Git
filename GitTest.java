import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class GitTest {
    @Test
    void testAdd() throws IOException {
        File testFile = new File("test.txt");
        testFile.createNewFile();

        // Call add method
        Git.add("test.txt");

        // Checks to see if index file has the text from test.txt inside it
        String indexContents = Git.readFile("index");
        assertTrue(indexContents.contains("test.txt"));
    }

    @Test
    void testInitialize() throws IOException {

        // Call init method
        Git.initialize();

        // Checks if index  and objects were created
        assertTrue(new File("index").isFile());
        assertTrue(new File("objects").isDirectory());
    }

    @Test
    void testReadFile() throws IOException {
        File testFile = new File("test.txt");
        String fileContent = "abcdefg"; // Makes a String for what we want to be inside of test.txt
        try (FileWriter fw = new FileWriter(testFile)) {
            fw.write(fileContent); // Writes the string to test.txt
        }

        // Call the readFile method to read the content
        String readContent = Git.readFile("test.txt"); // Reads through test.txt

        // Check if the content matches what we wrote
        assertEquals(fileContent, readContent); // Compares the 2 Strings
    }

    @Test
    void testRemove() {

    }
}
