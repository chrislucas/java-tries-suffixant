package problems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * https://www.hackerrank.com/challenges/no-prefix-set/problem
 * */

public class NoPrefixSet {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out), true);

    public static class TrieNode {
        private char c;
        private int counterPrefixes;
        private HashMap<Character, TrieNode> tree;
        private boolean isLeaf;
        private TrieNode() {
            this.counterPrefixes = 0;
            this.tree = new HashMap<>();
        }
        private TrieNode(char c) {
            this();
            this.c = c;
            this.counterPrefixes = 1;
        }
        @Override
        public String toString() {
            return String.format("%c -> %d", c, counterPrefixes);
        }
    }

    private static TrieNode root;

    private static void insert(String str) {
        HashMap<Character, TrieNode> map = root.tree;
        for(int idx=0; idx<str.length(); idx++) {
            char c = str.charAt(idx);
            TrieNode newNode;
            if(map.containsKey(c)) {
                newNode = map.get(c);
                newNode.counterPrefixes++;
            }
            else {
                newNode = new TrieNode(c);
                map.put(c, newNode);
            }
            if(idx == str.length()-1)
                newNode.isLeaf = true;
            map = newNode.tree;
        }
    }


    /**
     *
     * */
    public static boolean findPrefix(String str) {
        HashMap<Character, TrieNode> map = root.tree;
        TrieNode node = null;
        for(int idx=0; idx<str.length(); idx++) {
            char c = str.charAt(idx);
            if(map.containsKey(c)) {
                node    = map.get(c);
                map     = node.tree;
                if(node.counterPrefixes > 1 && idx > 1)
                    return true;
            }
            else
                return false;
        }
        return false;
    }

    public static void run() {
        root = new TrieNode();
        try {
            int cases = Integer.parseInt(reader.readLine().trim());
            String fname = "";
            String [] names = new String[cases];
            int idx = 0;
            while (cases-->0) {
                String name = reader.readLine();
                insert(name);
                names[idx++] = name;
            }
            for(idx=1; idx < names.length; idx++) {
                String name = names[idx];
                if(findPrefix(name)) {
                    fname = name;
                    break;
                }
            }
            writer.printf("%s\n", fname.equals("") ? "GOOD SET" : "BAD SET\n" + fname);
        } catch (Exception e) {}
    }

    public static void main(String[] args) {
        run();
    }

}
