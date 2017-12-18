package structure;


import java.util.HashMap;
import java.util.Map;

/**
 * ImplTrieAlphabetic = reTRIEval. Arvore de recuperacao de dados
 * Pronuncia-se Tree ou Try para nao confundir outras Trees
 * https://www.programcreek.com/2014/05/leetcode-implement-trie-prefix-tree-java/
 * */
public class ImplTrie {
    static class TrieNode {
        private char c;
        private HashMap<Character, TrieNode> children = new HashMap<>();
        boolean isLeaf;
        public TrieNode() { }
        public TrieNode(char c) { this.c = c; }
        @Override
        public String toString() { return String.format("%c", c); }
    }
    private TrieNode root;
    public ImplTrie() {
        root = new TrieNode();
    }

    public void insert(String str) {
        HashMap<Character, TrieNode> map = root.children;
        int i = 1, len = str.length();
        for(char c : str.toCharArray()) {
            TrieNode node;
            if(map.containsKey(c)) {
                node = map.get(c);
            }
            else {
                node = new TrieNode(c);
                map.put(c, node);
            }
            map = node.children;
            if(i == len) {
                // marcar o no como folha quando chegar ao ultimo caracter da palavra
                node.isLeaf = true;
            }
            i++;
        }
    }

    private TrieNode searchNode(String str) {
        Map<Character, TrieNode> children = root.children;
        TrieNode node = null;
        for(int idx =0; idx<str.length(); idx++) {
            char c = str.charAt(idx);
            System.out.printf("%c ", c);
            if(children.containsKey(c)) {
                node = children.get(c);
                children = node.children;
            }
        }
        System.out.println("");
        return node;
    }

    public boolean search(String str) {
        TrieNode node = searchNode(str);
        return node != null && node.isLeaf;
    }

    // verifica se alguma palavra na arvore comeca com o prefixo
    public boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }

    public static void test1() {
        ImplTrie implTrie = new ImplTrie();
        implTrie.insert("Amanda");
        implTrie.insert("Amara");
        implTrie.insert("Madalena");
        implTrie.insert("Bianca");
        implTrie.insert("Bruna");

        String [] strs = {"Bruno", "Bruna", "Maira", "Maiara", "Amanda"};
        for(String str : strs)
            System.out.printf("%s %s\n", str
                    , implTrie.search(str) ? "Existe" : "Não Existe");

        System.out.println("\nPesquisando prefixos");
        strs = new String[] {"Mada", "Mai", "Mai", "Am", "B", "Br", "Bruno"};
        for(String str : strs)
            System.out.printf("Prefixo: %s %s\n", str
                    , implTrie.startsWith(str) ? "Existe" : "Não Existe");
    }

    public static void main(String[] args) {
        test1();
    }
}
