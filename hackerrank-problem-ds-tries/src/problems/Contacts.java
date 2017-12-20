package problems;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * https://www.hackerrank.com/challenges/contacts/problem
 * DONE
 * */

public class Contacts {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);

    public static class TrieNode {
        private char c;
        private int counterPrefixes;
        private boolean isLeaf;
        private HashMap<Character, TrieNode> tree;

        public TrieNode() {
            this.tree = new HashMap<>();
            this.counterPrefixes = 0;
        }

        public TrieNode(char c) {
            this();
            this.c = c;
            this.counterPrefixes = 1;
        }

        @Override
        public String toString() {
            return String.format("%c", c);
        }
    }

    private static TrieNode root;

    public static void insert(String str) {
        HashMap<Character, TrieNode> map = root.tree;
        for(int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            TrieNode newNode;
            if(map.containsKey(c)) {
                newNode = map.get(c);
                newNode.counterPrefixes++;
            }
            else {
                newNode = new TrieNode(c);
                map.put(c, newNode);
            }
            if(i == str.length()-1)
                newNode.isLeaf = true;
            map = newNode.tree;
        }
    }

    /**
     * Busca uma cadeia de caracteres
     * */
    public static TrieNode search(String str) {
        HashMap<Character, TrieNode> map = root.tree;
        TrieNode node = null;
        for(int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if(map.containsKey(c)) {
                node = map.get(c);
                map = node.tree;
            }
            else
                return null;
        }
        return node;
    }

    /**
     *
     * Metodo auxiliar que procura pela a cadeia de carateres
     * */
    public static boolean exists(String str) {
        TrieNode node = search(str);
        return node != null && node.isLeaf;
    }

    /**
     * Procura na estrutura de dados se existe pelo menos uma cadeia
     * de caracteres com o prefixo passado por parametro
     * */
    public static boolean startsWith(String str) {
        return search(str) != null;
    }

    public static int countPrefixes(String str) {
        HashMap<Character, TrieNode> map = root.tree;
        TrieNode node = root;
        for(int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if(map.containsKey(c)) {
                node = map.get(c);
                map = node.tree;
            }
            else
                return 0;
        }
        return node.counterPrefixes;
    }

    public static void solver() {
        try {
            int cases = Integer.parseInt(reader.readLine().trim());
            while(cases-->0) {
                StringTokenizer tk = new StringTokenizer(reader.readLine(), " ");
                String action = tk.nextToken();
                String name = tk.nextToken();
                if(action.equals("add"))
                    insert(name);
                else
                    writer.printf("%d\n", countPrefixes(name));
            }
        } catch (IOException e) { }
    }

    public static void test() {
        insert("s");
        insert("ss");
        insert("sss");
        insert("ssss");
        insert("sssss");
        insert("Manoela");
        insert("Bruna");
        insert("Bruno");
        insert("Brenda");
        insert("Amanda");
        insert("Amara");
        insert("manoele");
        String [] strs = { "Amanda", "Bruna", "Natalia"
                , "s", "ss", "sss", "Bruno", "Bruna", "Maira", "Maiara"};
        for(String str : strs)
            writer.printf("%s %s\n", str, exists(str) ? " Existe" : "Não existe");

        writer.println("\nPesquisando prefixos");
        strs = new String[] {"Mada", "Mai", "Mai", "Am", "B", "Br", "Bruno", "mano"};
        for(String str : strs)
            writer.printf("Prefixo: %s %s\n", str, startsWith(str) ? "Existe" : "Não Existe");

        writer.println("\nContando prefixos");
        strs = new String[] { "s", "ss", "sss", "sssss", "ssssss", "Br", "Bru", "Brun", "Ma"};
        for(String str : strs)
            writer.printf("Prefixo: %s Quantidade de palavras %d\n", str, countPrefixes(str));
    }

    public static void main(String[] args) {
        root = new TrieNode();
        //test();
        solver();
    }

}
