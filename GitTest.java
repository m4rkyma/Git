import static org.junit.Assert.assertTrue;

import java.io.File;
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
    void testReadFile() {

    }

    @Test
    void testRemove() {

    }
}
