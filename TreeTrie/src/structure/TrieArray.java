package structure;

import java.util.Hashtable;

public class TrieArray {

    private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .concat("ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase());

    public static class TrieNode {
        private TrieNode [] array;
        private boolean isLeaf;
        private int capacity;
        private char c;
        public TrieNode(int capacity) {
            this.capacity = capacity;
            this.array = new TrieNode[capacity];
        }
        public TrieNode() {
            this.array = new TrieNode[ALPHA.length()];
            this.capacity = ALPHA.length();
        }
        public TrieNode(char c) {
            this();
            this.c = c;
        }
    }

    private static final Hashtable<Character, Integer> alpha = new Hashtable<>();
    static {
        for(int i=0; i<ALPHA.length(); i++)
            alpha.put(ALPHA.charAt(i), i);
    }

    private TrieNode root;
    public TrieArray( ) {
        root = new TrieNode();
    }

    public void insert(String str) {
        TrieNode node = root;
        for(int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            int index = alpha.get(c);
            if(node.array[index] == null)
                node.array[index] = new TrieNode(c);
            node = node.array[index];
        }
        node.isLeaf = true;
    }

    private TrieNode searchNode(String str) {
        int limit = str.length() - 1;
        TrieNode node = root;
        for(int i=0; i<=limit; i++) {
            int index = alpha.get(str.charAt(i));
            if(node.array[index]==null)
                return null;
            node = node.array[index];
            System.out.printf("%c ", node.c);
        }
        return node;
    }

    public boolean search(String str) {
        TrieNode trieNode = searchNode(str);
        return trieNode != null && trieNode.isLeaf;
    }

    public boolean searchPrefix(String str) {
        return searchNode(str) != null;
    }

    public static void test() {
        TrieArray trieArray = new TrieArray();
        String strs [] = {"Marta", "Maria", "Joana", "Leila", "Madalena", "Leticia", "Juliana"};
        for(String str : strs)
            trieArray.insert(str);

        strs  = new String[] {"Maria", "Leticia", "Mariana"};
        for(String str : strs) {
            System.out.printf("%s %s\n"
                    , str, trieArray.search(str) ? "Encontrado" : "Não encontrado");
        }

        strs = new String [] {"Ma", "Let", "Maria", "Marcos"};
        for(String str : strs) {
            System.out.printf( "Prefixo: %s - %s\n"
                    , str, trieArray.searchPrefix(str) ? "Encontrado" : "Não encontrado");
        }
    }

    public static void main(String[] args) {
        test();
    }
}
