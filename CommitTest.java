import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

public class CommitTest {
    @Test
    void testCommit() throws IOException {
        Commit github = new Commit("kevin", "cool file");
        github.push();

        Path location = Paths.get("objects/" + github.getSha());

        String correctFill = ""

        assertTrue("File contents do not match", correctFill.equals(Files.toString(location)));
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
