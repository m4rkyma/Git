import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class BlobTest {
    @Test
    void testSha1() {
        String expected = "f187ae69bdfb0f1510f108534b241abaa5a4ca72";
        String actual = Blob.Sha1("wyattvanamburg77");
        assertTrue(expected.equals(actual));
    }

    @Test
    void testGetContents() throws IOException {
        Blob.writeFile("f187ae69bdfb0f1510f108534b241abaa5a4ca72", "file1.txt");
        String expectedContents = "f187ae69bdfb0f1510f108534b241abaa5a4ca72";
        String actual = Blob.readFile("file1.txt");
        File f = new File ("file1.txt");
        f.delete();
        assertTrue(expectedContents.equals(actual));
    }

    @Test
    void testReadFile() throws IOException {
        Blob.writeFile("f187ae69bdfb0f1510f108534b241abaa5a4ca72", "file1.txt");
        String expectedContents = "f187ae69bdfb0f1510f108534b241abaa5a4ca72";
        String actual = Blob.readFile("file1.txt");
        File f = new File ("file1.txt");
        f.delete();
        assertTrue(expectedContents.equals(actual));
    }

    @Test
    void testToSha1() throws IOException {
        Blob.writeFile("wyattvanamburg77", "file1.txt");
        String expectedContents = "f187ae69bdfb0f1510f108534b241abaa5a4ca72";
        String actual = Blob.toSha1("file1.txt");
        assertTrue(expectedContents.equals(actual));
    }

    @Test
    void testWriteFile() throws IOException {
        String expected = "wyattvanamburg77";
        Blob.writeFile("wyattvanamburg77", "file1.txt");
        String actual = Blob.readFile("file1.txt");
        assertTrue(expected.equals(actual));
    }
}
