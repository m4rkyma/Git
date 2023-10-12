import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Tree {
    private ArrayList<String> treeEntries, deletedFiles, editedFiles;
    private HashMap<String, String> blobEntries = new HashMap<String, String>();
    private StringBuilder sb = new StringBuilder();

    public Tree() throws IOException {
        // File f = new File("tree");
        // f.createNewFile();
        treeEntries = new ArrayList<>();
        blobEntries = new HashMap<>();
    }

    public void add(String line) throws IOException {
        if (line.substring(0, 4).equals("blob")) // checks if adding type: blob
        {
            Scanner sc = new Scanner(line);
            while (sc.hasNextLine()) {
                sc.nextLine();
                if (!line.isEmpty()) {
                    String type = line.split(" : ")[0]; // dummy variable so I can access hash and name
                    String hash = line.split(" : ")[1]; // gets the center of the line (hash)
                    String name = line.split(" : ")[2]; // gets the end of the line (fileName)
                    if (!blobEntries.containsKey(name)) {
                        blobEntries.put(hash, name);
                        // Blob blob = new Blob (name);
                    }
                    // blobEntries.put(hash, name); // adds to hashMap of blobs (Strings)
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
                    if (!treeEntries.contains(hash)) {
                        treeEntries.add(hash);
                    }
                    // tree.add(hash); // adds the hash value to an ArrayList (tree)
                }
            }
            k.close(); // necessary close of Scanner (k)
        }
    }

    public void remove(String remove) {
        if (blobEntries.containsKey(remove) || blobEntries.containsValue(remove)) {
            blobEntries.remove(remove);
            deletedFiles.add(remove);
        } else if (treeEntries.indexOf(remove) != -1) {
            treeEntries.remove(remove);
            deletedFiles.add(remove);
        }
    }

    public void getContents() throws IOException {
        for (Map.Entry<String, String> k : blobEntries.entrySet()) {
            String line = "blob : " + k.getKey() + " : " + k.getValue();
            sb.append(line);
            sb.append("\n");
        }
        for (int i = 0; i < treeEntries.size(); i++) {
            String line = "tree : " + treeEntries.get(i);
            // File file = new File (tree.get(i));
            // String line = "tree : " + file.getSha();
            sb.append(line);
            sb.append("\n");
        }
        File fileToDelete = new File("tree");
        fileToDelete.delete();
        sb = new StringBuilder(sb.toString().stripTrailing());
    }

    public String getSha() throws IOException
    {
        // getContents();
        return Blob.Sha1(sb.toString());
    }

    // Made this for JUnit Tester
    public String printSB() {
        return sb.toString();
    }

    public void writeToObjects() throws IOException {
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
        // File dir = new File(directoryPath);
        // // if (!dir.isDirectory()) {
        // //     throw new Exception("Not a directory");
        // // }
        // File[] files = dir.listFiles();

        // Tree tree = new Tree();

        // for (File file : files) {
        //     if (file.isDirectory()) {
        //         Tree childTree = new Tree();
        //         tree.add ("tree : " + childTree.addDirectory(file.getPath()) + " : " + file.getName());
        //     } else {
        //         Blob blob = new Blob(file.getPath());
        //         tree.add("blob : " + Blob.toSha1(file.getPath()) + " : " + file.getName());
        //     }
        // }
        // return tree.getSha();
        File f = new File (directoryPath);
        String [] arr = f.list();
        if (arr == null) {
            return null; // Handle non-existent directories
        }
        if (arr != null)
        {
            for (String fileName : arr)
        {
            File a = new File (directoryPath+"/"+fileName);
            if (a.isFile())
            {
                Blob blob = new Blob (directoryPath+"/"+fileName);
                String name = "/"+directoryPath + "/"+fileName;
                add("blob : " + Blob.toSha1(directoryPath+"/"+fileName) + " : " + name);
            }
            else if (a.isDirectory())
            {
                Tree childTree = new Tree();
                add ("tree : " + childTree.addDirectory(directoryPath+"/"+fileName) + " : " + fileName);
                childTree.getContents();
                childTree.writeToObjects();
            }
        }
        }
        // getContents();

        return (getSha());
    }
    public HashMap<String, String> getBlobEntries() {
        return blobEntries;
    }
    
    public ArrayList<String> getTreeEntries() {
        return treeEntries;
    }

    public String searchTree (String tree, String target) throws IOException
    {
        
        String searchTxt = "";
        
        try (BufferedReader br = new BufferedReader(new FileReader(tree))) {
            String curLine;
            String treeSha = tree;
            
            while ((curLine = br.readLine()) != null) {
                if (curLine.startsWith("tree")) {
                    treeSha = curLine.substring(7, 47);
                    searchTxt += searchTree("objects/" + treeSha, target) + "\n";
                    return searchTxt;
                } else if (curLine.substring(curLine.lastIndexOf(":") + 2).equals(target)) {
                    while ((curLine = br.readLine()) != null) {
                        searchTxt += curLine + "\n";
                    }
                    return searchTxt;
                } else {
                    searchTxt += curLine + "\n";
                }
            }
        }
        
        System.out.println(searchTxt);
        return searchTxt;
    }
    public String delete (String fileName, String tree) throws IOException
    {
        //gets tree of latest commit
        // File f = new File ("HEAD");
        // BufferedReader fr = new BufferedReader(new FileReader(f));
        // String curCommit = fr.readLine();
        // fr.close();
        // File c = new File ("objects/" + curCommit);
        // BufferedReader r = new BufferedReader(new FileReader(c));
        // String s = ("blob : " + "");
        // return (r.readLine());
        String delete = searchTree (tree,fileName.substring(fileName.lastIndexOf("/")+1));
        System.out.println(delete);
        return delete;
    }
    public static void main(String[] args) throws IOException {
        Tree t= new Tree ();
        // t.add("blob : 86f7e437faa5a7fce15d1ddcb9eaeaea377667b8 : b.txt");
        t.addDirectory ("blu2");
        t.getContents();
        t.writeToObjects();
    }
}
