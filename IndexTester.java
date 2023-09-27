import java.io.IOException;

public class IndexTester{
    public static void main (String [] args) throws IOException
    {
        Git git = new Git();
        git.initialize();

<<<<<<< Updated upstream
        // git.add("b.txt");
        //git.add("a.txt");
        //git.remove ("a.txt");
        // git.add("doesn'tmatter.txt");
        // git.add("doesn'tmatter.txt");
        // git.remove ("a.txt");
        Tree.add("blob : piwuhfbwepiugh : check");
        Tree.add("tree : piwuhfbwepiugh");
        Tree.getContents();
        Tree.printSB();
        Tree.writeToObjects();
=======
        git.add("a.txt");
        git.add("b.txt");
        // git.add("c.txt");
>>>>>>> Stashed changes

        git.remove ("a.txt");
        // git.remove("b.txt");
        // git.remove("c.txt");
        git.add("c.txt");
    }
}