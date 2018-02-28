package problems.spoj;

import java.io.*;
import java.util.*;

/**
 * http://www.spoj.com/problems/DICT/
 * */

public class DictSearchIn {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);

    static class TrieNode {
        private char c;
        private boolean isLeaf;
        private HashMap<Character, TrieNode> prefixes = new HashMap<>();
        private TrieNode() {}
        private TrieNode(char c) {
            this();
            this.c = c;
        }
        @Override
        public String toString() { return String.format("val: %c", c); }
    }

    private static TrieNode rootPrefixTrie = new TrieNode();

    private static void insert(String word) {
        HashMap<Character, TrieNode> prefix = rootPrefixTrie.prefixes;
        for (int i = 0; i < word.length() ; i++) {
            TrieNode node;
            char c = word.charAt(i);
            if(prefix.containsKey(c))
                node = prefix.get(c);
            else {
                node = new TrieNode(c);
                prefix.put(c, node);
            }
            if(i == word.length()-1)
                node.isLeaf = true;
            prefix = node.prefixes;
        }
    }

    private static TrieNode searchNode(String str) {
        Map<Character, TrieNode> prefix = rootPrefixTrie.prefixes;
        TrieNode node = null;
        for(int idx =0; idx<str.length(); idx++) {
            char c = str.charAt(idx);
            if(prefix.containsKey(c)) {
                node = prefix.get(c);
                prefix = node.prefixes;
            }
            else
                return null;
        }
        return node;
    }

    private static boolean search(String str) {
        TrieNode node = searchNode(str);
        return node != null && node.isLeaf;
    }

    // verifica se alguma palavra na arvore comeca com o prefixo
    private static TrieNode startsWith(String prefix) {
        return searchNode(prefix);
    }

    private static List<String> searchWordsWithPrefix(String prefix) {
        TrieNode node = startsWith(prefix);
        if(node==null)
            return null;
        List<String> words = new ArrayList<>();
        searchWords(node, prefix, words);
        return words;
    }

    private static void searchWords(TrieNode node, String prefix,  List<String> words) {
        if(node.isLeaf) {
            words.add(prefix);
            if(node.prefixes.isEmpty())
                return;
        }
        for (Map.Entry<Character, TrieNode> entry: node.prefixes.entrySet()) {
            TrieNode n = entry.getValue();
            searchWords(n, prefix.concat(String.valueOf(n.c)), words);
        }
    }


    private static void test() {
        String [][] words = {
            {"set", "setter", "lol", "setting", "settings"}
        };
        int idx = 0;
        for(String str : words[idx])
            insert(str);

        String [][] pre = {{"s", "set", "happy", "sett"}};

        for(String str : pre[idx]) {
            System.out.printf("Prefixo: %s\n", str);
            List<String> wordsWithPrefix = searchWordsWithPrefix(str);
            if(wordsWithPrefix != null && wordsWithPrefix.size() > 0) {
                for(String word : wordsWithPrefix) {
                    if(word.equals(str))
                        continue;
                    writer.printf("%s\n", word);
                }
            }
            else {
                System.out.printf("No match\n");
            }
            writer.println("");
        }
    }

    private static void solver() {
        try {
            int qWords = Integer.parseInt(reader.readLine().trim());
            for (int i = 0; i < qWords; i++) {
                insert(reader.readLine().trim());
            }
            int qPrefixes = Integer.parseInt(reader.readLine());
            for (int i = 0; i < qPrefixes ; i++) {
                writer.printf("Case #%d:\n", i+1);
                String prefix = reader.readLine().trim();
                List<String> wordsWithPrefix = searchWordsWithPrefix(prefix);
                if(wordsWithPrefix != null && wordsWithPrefix.size() > 0) {
                    for(String word : wordsWithPrefix) {
                        if(word.equals(prefix))
                            continue;
                        writer.printf("%s\n", word);
                    }
                }
                else {
                    writer.printf("No match.\n");
                }
            }
        } catch (IOException ioex) {}
    }

    public static void main(String[] args) {
        solver();
    }
}
