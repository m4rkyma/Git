import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class GitTest {
    @Test
    void testAdd() throws IOException {
        File testFile = new File("test.txt");
        testFile.createNewFile();

        // Call the add method
        Git.add("test.txt");

        // Check if 'index' file was updated
        String indexContents = Git.readFile("index");
        assertTrue(indexContents.contains("test.txt"));
    }

    @Test
    void testInitialize() {

    }

    @Test
    void testReadFile() {

    }

    @Test
    void testRemove() {

    }
}
