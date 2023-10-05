import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Tree {
    static ArrayList<String> tree = new ArrayList<String>();
    static HashMap<String, String> hMap = new HashMap<String, String>();
    static StringBuilder sb = new StringBuilder();

    public Tree() throws IOException {
        File f = new File("tree");
        f.createNewFile();
    }

    public static void add(String line) throws IOException {
        if (line.substring(0, 4).equals("blob")) // checks if adding type: blob
        {
            Scanner sc = new Scanner(line);
            while (sc.hasNextLine()) {
                sc.nextLine();
                if (!line.isEmpty()) {
                    String type = line.split(" : ")[0]; // dummy variable so I can access hash and name
                    String hash = line.split(" : ")[1]; // gets the center of the line (hash)
                    String name = line.split(" : ")[2]; // gets the end of the line (fileName)
                    hMap.put(hash, name); // adds to hashMap of blobs (Strings)
                }
            }
            sc.close(); // necessary close of Scanner (sc)
        } else if (line.substring(0, 4).equals("tree")) // chekcs if adding type: tree
        {
            Scanner k = new Scanner(line);
            while (k.hasNextLine()) {
                k.nextLine();
                if (!line.isEmpty()) {
                    String type = line.split(" : ")[0]; // dummy variable so I can access hash and name
                    String hash = line.split(" : ")[1]; // gets end of the line (hash)
                    tree.add(hash); // adds the hash value to an ArrayList (tree)
                }
            }
            k.close(); // necessary close of Scanner (k)
        }
    }

    public static void remove(String remove) {
        if (hMap.containsKey(remove) || hMap.containsValue(remove)) {
            hMap.remove(remove);
        } else if (tree.indexOf(remove) != -1) {
            tree.remove(remove);
        }
    }

    public static void getContents() throws IOException {
        for (Map.Entry<String, String> k : hMap.entrySet()) {
            String line = "blob : " + k.getKey() + " : " + k.getValue();
            sb.append(line);
            sb.append("\n");
        }
        for (int i = 0; i < tree.size(); i++) {
            String line = "tree : " + tree.get(i);
            // File file = new File (tree.get(i));
            // String line = "tree : " + file.getSha();
            sb.append(line);
            sb.append("\n");
        }
        File fileToDelete = new File("tree");
        fileToDelete.delete();
        sb = new StringBuilder(sb.toString().stripTrailing());
    }

    public String getSha()
    {
        return Blob.Sha1(sb.toString());
    }

    // Made this for JUnit Tester
    public static String printSB() {
        return sb.toString();
    }

    public static void writeToObjects() throws IOException {
        File shaName = new File("objects/" + Blob.Sha1(sb.toString()));
        if (!shaName.exists()) {
            shaName.createNewFile();
        }
        if (shaName.exists()) {
            FileWriter fw = new FileWriter(shaName);
            sb.toString().stripTrailing();
            fw.write(sb.toString());
            fw.close();
        }
    }
    public String addDirectory (String directoryPath) throws IOException
    {
        File f = new File (directoryPath);
        if (f.isFile())
        {
            Git.addFile(directoryPath);
            // add ("blob : " + f.getSha());
        }
        // Tree childTree = new Tree();
        String [] arr = f.list();
        if (arr == null) {
            return null; // Handle non-existent directories
        }
        if (arr != null)
        {
            for (String fileName : arr)
        {
            // Blob b = new Blob (directoryPath + "/"+arr[i].getName());
            File a = new File (directoryPath+"/"+fileName);
            if (a.isFile())
            {
                String hash = Blob.toSha1(a.getAbsolutePath());
                String name = "/"+directoryPath + "/"+fileName;
                add("blob : " + hash + " : " + name);
            }
            else if (a.isDirectory())
            {
                Tree childTree = new Tree();
                String subsha1 = childTree.addDirectory(directoryPath+"/"+fileName);
                
                if (subsha1 != null)
                {
                    add ("tree : " + subsha1 + " : " + fileName);
                }
                // add ("tree : " + subsha1 + " : " + fileName);
            }
        }
        }
        return (getSha());
    }
    public static void main(String[] args) throws IOException {
        Tree t= new Tree ();
        t.add("blob : 86f7e437faa5a7fce15d1ddcb9eaeaea377667b8 : b.txt");
        t.addDirectory ("blu");
        getContents();
        writeToObjects();
    }
}
