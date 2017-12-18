package structure;

import java.util.Hashtable;

/**
 * http://www.geeksforgeeks.org/trie-insert-and-search/
 * */
public class ImplTrieAlphabetic {

    private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .concat("ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase());
    public static class TrieNode {
        public static final int CAP = ALPHA.length();
        private TrieNode [] children;
        private char c;
        private boolean isLeaf;
        public TrieNode(char c) {
            this();
            this.c = c;
        }
        public TrieNode() {
            this.children = new TrieNode[CAP];
            this.isLeaf = false;
            for(int i=0; i<CAP; i++)
                children[i] = null;
        }
    }
    private static TrieNode root = new TrieNode();
    private static final Hashtable<Character, Integer> alpha = new Hashtable<>();
    static {
        for(int i=0; i<ALPHA.length(); i++)
            alpha.put(ALPHA.charAt(i), i);
    }

    public static void insert(String str) {
        int len = str.length();
        int index;
        TrieNode trieNode = root;
        for(int level=0; level<len; level++) {
            index = alpha.get(str.charAt(level));
            if(trieNode.children[index] == null)
                trieNode.children[index] = new TrieNode(str.charAt(level));
            trieNode = trieNode.children[index];
        }
        trieNode.isLeaf = true;
    }

    private static TrieNode search(String str) {
        int len = str.length() - 1;
        TrieNode trieNode = root;
        for (int level = 0; level <=len; level++) {
            int index =  alpha.get(str.charAt(level) );
            if(trieNode.children[index] == null)
                return null;
            if(level == len)
                System.out.printf("Quantidade de sufixxos %d\n", trieNode.children.length);
            trieNode = trieNode.children[index];
            System.out.printf("%c ", trieNode.c);
        }
        return trieNode;
    }

    private static boolean exists(String str) {
        TrieNode trieNode = search(str);
        return trieNode != null && trieNode.isLeaf;
    }

    private static void test() {
        insert("Manoela");
        insert("Bruna");
        insert("Amanda");
        insert("Amara");
        insert("manoele");
        String [] strs = { "Amanda", "Bruna", "Natalia"
                , "marina", "amara", "manoela", "manoel", "Amara"};
        for(String str : strs) {
            System.out.println(exists(str) ? " Existe" : String.format("%s Não existe", str));
        }
    }

    public static void main(String[] args) {
        test();
    }
}
