package problems.spoj;

import java.io.*;
import java.util.HashMap;
import java.util.NavigableMap;
import java.util.TreeMap;

/***
 * Verificar se nenhum telefone da lista eh prefixo de outro telefone
 * http://www.spoj.com/problems/PHONELST/
 * DONE
 * */

public class PhoneLST {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);


    public static class TrieNode {
        private char c;
        private int counter;
        private HashMap<Character, TrieNode> prefixes = new HashMap<>();
        private boolean isLeaf;

        public TrieNode() {
            this.c = ' ';
            this.counter = 1;
        }
        public TrieNode(char c) {
            this();
            this.c = c;
        }
        @Override
        public String toString() { return String.format("val: %c", c); }
    }

    private static TrieNode rootNode;
    private static boolean insertAndVerifyHasPrefix(String word) {
        HashMap<Character, TrieNode> prefixes = rootNode.prefixes;
        boolean flag = false;
        for (int idx = 0; idx < word.length(); idx++) {
            TrieNode node = null;
            char c = word.charAt(idx);
            if(prefixes.containsKey(c)) {
                node = prefixes.get(c);
                node.counter += 1;
                // caso
                flag = node.counter > 1 && node.isLeaf || (node.counter > 1 && idx == word.length()-1);
            }
            else {
                node = new TrieNode(c);
                prefixes.put(c, node);
            }
            if(idx == word.length() - 1)
                node.isLeaf = true;
            prefixes = node.prefixes;
        }
        return flag;
    }

    private static void test() {}

    private static void solver() {
        try {
            int testCases = Integer.parseInt(reader.readLine());
            for (int i = 0; i < testCases ; i++) {
                rootNode = new TrieNode();
                int numbers = Integer.parseInt(reader.readLine());
                boolean flag = false;
                for (int j = 0; j < numbers ; j++) {
                    String number = reader.readLine();
                    if(!flag) {
                        flag = insertAndVerifyHasPrefix(number);
                    }
                }
                writer.printf("%s\n", flag ? "NO" : "YES");
            }
        } catch (IOException ioex) {}
    }

    public static void main(String[] args) {
        solver();
    }
}
