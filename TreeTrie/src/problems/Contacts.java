package problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * https://www.hackerrank.com/challenges/ctci-contacts/problem
 * */

public class Contacts {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);
    private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .concat("ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase());
    private static final Hashtable<Character, Integer> alpha = new Hashtable<>();
    static {
        for(int i=0; i<ALPHA.length(); i++)
            alpha.put(ALPHA.charAt(i), i);
    }

    public static class Node {
        private Node [] children;
        private boolean isLeaf;
        private int quantity;
        private char c;
        public Node() {
            children = new Node[ALPHA.length()];
            for(int i=0; i<ALPHA.length(); i++)
                children[i] = null;
            quantity = 0;
        }
        public Node(char c) {
            this();
            this.c = c;
            this.quantity = 1;
        }
    }

    public static Node root = new Node();

    public static void insert(String str) {
        Node node = root;
        for(int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            int index = alpha.get(c);
            if(node.children[index] == null) {
                node.children[index] = new Node(str.charAt(i));
            }
            else {
                node.children[index].quantity++;
            }
            node = node.children[index];
        }
        node.isLeaf = true;
    }

    public static Node searchNode(String str) {
        Node node = root;
        int limit = str.length() - 1;
        for(int i=0; i<=limit; i++) {
            char c = str.charAt(i);
            int index = alpha.get(c);
            if(node.children[index] == null)
                return null;
            if(i == limit) {
                writer.printf("%d\n", node.quantity);
            }
            node = node.children[index];
        }
        return node;
    }

    public static boolean search(String str) {
        Node node = searchNode(str);
        return node != null && node.isLeaf;
    }

    public static boolean searchPrefix(String str) {
        return searchNode(str) != null;
    }

    public static int countPrefixes(String str) {
        Node node = root;
        int limit = str.length() - 1;
        for(int i=0; i<=limit; i++) {
            int index = alpha.get(str.charAt(i));
            if(node.children[index] == null)
                return 0;
            node = node.children[index];
        }
        return node.quantity;
    }

    private static void test() {
        insert("Maria");
        insert("Marta");
        insert("Marina");
        insert("Amara");
        insert("Amora");
        insert("Bruna");
        insert("Brenda");
        //String [] strs = { "Amanda", "Bruna", "Natalia", "marina", "amara", "manoela", "manoel", "Amara"};
        /*
        for(String str : strs)
            System.out.println(search(str) ? " Existe" : String.format("%s Não existe", str));
        */
        String []  strs = new String [] {"Ma", "Br"};
        for(String str : strs)
            searchPrefix(str);
    }

    public static void run() {
        try {
            int searches = Integer.parseInt(reader.readLine().trim());
            while(searches-->0) {
                StringTokenizer tk = new StringTokenizer(reader.readLine());
                String action  = tk.nextToken();
                String name = tk.nextToken();
                if(action.equals("add"))
                    insert(name);
                else
                    writer.printf("%d\n", countPrefixes(name));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        run();
    }
}
