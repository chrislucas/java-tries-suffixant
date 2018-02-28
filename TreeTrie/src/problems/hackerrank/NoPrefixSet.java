package problems.hackerrank;


import structure.ImplTrie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * https://www.hackerrank.com/challenges/no-prefix-set/problem
 * */

public class NoPrefixSet {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);

    private static class TrieNode {
        private char c = ' ';
        private HashMap<Character, TrieNode> prefixes = new HashMap<>();
        private int countPrefix = 1;
        private boolean isLeaf;
        public TrieNode() {}
        public TrieNode(char c) {
            this();
            this.c = c;
        }

        @Override
        public String toString() { return String.format("val: %c", c); }
    }

    private static TrieNode rootNode = new TrieNode();

    /**
     * Da para resolver esse problema ao inserir. Mas vou tentar outra abordagem
     * */
    private static boolean insertWord(String word) {
        HashMap<Character, TrieNode> prefixes = rootNode.prefixes;
        boolean flag = false;
        for (int i = 0; i < word.length() ; i++) {
            char c = word.charAt(i);
            TrieNode trieNode = null;
            if( prefixes.containsKey(c) ) {
                trieNode = prefixes.get(c);
                trieNode.countPrefix++;
                flag = trieNode.countPrefix > 1 && trieNode.isLeaf || (trieNode.countPrefix > 1 && i == word.length()-1);
            }
            else {
                trieNode = new TrieNode(c);
                prefixes.put(c, trieNode);
            }
            if(i == word.length()-1) {
                trieNode.isLeaf = true;
            }
            prefixes = trieNode.prefixes;
        }
        return flag;
    }

    private static TrieNode findNodePrefix() {
        return findNodePrefix(rootNode);
    }

    private static TrieNode findNodePrefix(TrieNode trieNode) {
        TrieNode node = null;
        if(trieNode.countPrefix > 0)
            return trieNode;

        else if(trieNode.isLeaf && trieNode.prefixes.isEmpty())
            return null;

        for(Map.Entry<Character, TrieNode> entry: trieNode.prefixes.entrySet()) {
            node = findNodePrefix(entry.getValue());
            return node;
        }
        return node;
    }

    private static void test() {
        String [][] words = {
             {"Amanda", "Madalena", "Bianca", "Patricia"}
            ,{"Amanda", "Madalena", "Bianca", "Patricia", "Bruna"}
            ,{"aab", "defgab", "abcde", "aabcde", "cedaaa", "bbbbbbbbbb", "jabjjjad"}
            ,{"aab", "aac", "aacghgh", "aabghgh"}
            ,{"aab", "defgab", "abcde", "abc", "cedaaa", "bbbbbbbbbb", "jabjjjad"}
            ,{"aab", "defgab", "abc", "abcde", "cedaaa", "bbbbbbbbbb", "jabjjjad"}
            ,{"b", "aa", "aa"}
        };
        int idx = 4;
        boolean flag = false;
        String word = null;
        for(String str : words[idx]) {
            if(insertWord(str) && !flag) {
                flag = true;
                word = str;
            }
        }

        if(flag) {
            System.out.println("BAD SET");
            System.out.println(word);
        }
        else {
            System.out.println("GOOD SET");
        }
    }


    private static void solver() {

    }

    public static void main(String[] args) {
        test();
    }
}
