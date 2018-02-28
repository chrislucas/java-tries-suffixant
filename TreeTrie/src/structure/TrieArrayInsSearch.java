package structure;

import java.util.Hashtable;

/**
 * https://www.geeksforgeeks.org/trie-insert-and-search/
 *
 * Ler: https://en.wikipedia.org/wiki/Ternary_search_tree
 * */
public class TrieArrayInsSearch {
    private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".concat("ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase());
    private static final Hashtable<Character, Integer> table = new Hashtable<>();
    static {
        for(int i=0; i<ALPHA.length(); i++)
            table.put(ALPHA.charAt(i), i);
    }

    private static class TrieNode {
        private TrieNode [] prefix;
        private char c;
        private boolean isLeaf;
        private TrieNode() {
            this.prefix = new TrieNode[ALPHA.length()];
        }
        private TrieNode(char c) {
            this();
            this.c = c;
        }

        @Override
        public String toString() {
            return String.format("val: %c", c);
        }
    }

    private static TrieNode rootNode = new TrieNode();

    private static int getIndex(char c) {
        return table.get(c);
    }

    private static void insert(String word) {
        TrieNode node = rootNode;
        for (char c : word.toCharArray()) {
            int idx = getIndex(c);
            if(node.prefix[idx] == null) {
                node.prefix[idx] = new TrieNode(c);
            }
            node = node.prefix[idx];
        }
        node.isLeaf = true;
    }

    private static TrieNode search(String word) {
        TrieNode node = rootNode;
        int limit = word.length();
        System.out.printf("Prefixo: ");
        for (int i = 0; i < limit ; i++) {
            int idx = getIndex(word.charAt(i));
            if(node.prefix[idx] == null)
                return null;
            System.out.printf(i == 0 ? "%c" : " %c", word.charAt(i));
            node = node.prefix[idx];
        }
        return node;
    }

    private static boolean existsWord(String word) {
        TrieNode node = search(word);
        return node != null && node.isLeaf;
    }

    private static boolean existsPrefix(String word) {
        return search(word) != null;
    }

    private static void test() {
        String [][] words = {
             {"Am", "Amanda", "Amara", "Madalena", "Bianca", "Patricia", "Bruna"}
            ,{"a", "t", "b", "any", "the", "by", "answer", "there", "bye"}
        };
        int idx = 0;
        for(String word : words[idx])
            insert(word);

        String [][] words2 = {
             {"Marta", "Amanda", "Bruno", "Bruna", "Priscila", "Patricio"}
            ,{"by", "bye", "ani", "any"}
        }
        , prefixes = {
             {"Amanda", "Bruna", "Priscila", "Patricio", "Prisc", "Patric"}
            ,{"ther", "there", "ani", "by", "ye", "answ", "answir", "answe"}
        };

        System.out.println("Procurando as palavras:\n");
        for(String word : words2[idx]) {
            System.out.printf(" %s -> %s.\n"
                    , word, (existsWord(word) ? "Existe" : "Não existe"));
        }

        System.out.println("\nProcurando os prefixos:\n");
        for(String word : prefixes[idx]) {
            System.out.printf(" prefixo %s -> %s.\n"
                    , word, (existsPrefix(word) ? "Existe" : "Não existe"));
        }
    }

    public static void main(String[] args) {
        test();
    }
}
