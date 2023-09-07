import java.io.IOException;

public class IndexTester{
    public static void main (String [] args) throws IOException
    {
        Git git = new Git();
        git.initialize();

        git.add("a.txt");
        git.add("b.txt");
        git.remove ("a.txt");
    }
}