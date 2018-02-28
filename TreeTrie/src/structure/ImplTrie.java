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
        private HashMap<Character, TrieNode> prefix = new HashMap<>();
        boolean isLeaf;
        private int qPrefixes = 0;
        private TrieNode() { }
        private TrieNode(char c) { this.c = c; }
        @Override
        public String toString() { return String.format("val: %c", c); }
    }
    /**
     * Interessante: O nó raiz nao tem caracter somente os nos filhos
     * possuem
     * */
    private TrieNode root;
    private ImplTrie() {
        root = new TrieNode();
    }

    private void insert(String str) {
        HashMap<Character, TrieNode> map = root.prefix;
        int i = 1, len = str.length();
        for(char c : str.toCharArray()) {
            TrieNode node;
            /**
             * Se o mapa ja tiver uma chave igual ao caracterer atual da palavra
             * que esta sendo analisada, retorne o nó vinculado a esse caracter
             * para usarmos como prefixo e armazenamos nesse ramo somente os caracteres
             * diferentes
             * */
            if(map.containsKey(c)) {
                node = map.get(c);
                node.qPrefixes++;
            }
            /**
             * Do contrario criamos um no novo e adicionamos no mapa para criarmos
             * um nofo prefixo
             * */
            else {
                node = new TrieNode(c);
                /**
                 * map aponta para a referencia do atributo {@link root.prefix}
                 * entao quando adicionamos um valor ao map tambem adicionamos ao root e
                 * posteriormente aos filhos de root.
                 * */
                node.qPrefixes = 1;
                map.put(c, node);
            }
            /**
             * A variavel 'map' aponta primeiramente para 'root.prefix' e depois
             * sempre aponta para uma referencia de um no 'prefix' dentro de root.
             * A atribuição 'map = node.prefix' faz com que a variavel 'map' aponte ou
             * para um no novo, caso o caracter avaliado nao esteja num prefixo ou para
             * um no num prefixo ja existente
             * */
            map = node.prefix;
            if(i == len) {
                // marcar o no como folha quando chegar ao ultimo caracter da palavra
                node.isLeaf = true;
            }
            i++;
        }
    }

    private TrieNode searchNode(String str) {
        Map<Character, TrieNode> prefix = root.prefix;
        TrieNode node = null;
        for(int idx =0; idx<str.length(); idx++) {
            char c = str.charAt(idx);
            System.out.printf("%c ", c);
            if(prefix.containsKey(c)) {
                node = prefix.get(c);
                prefix = node.prefix;
            }
            else
                return null;
        }
        System.out.println("");
        return node;
    }

    /**
     * verifica se a palavra existe inteira na arvore de prefixo
     * sabemos que ela existe se obtivermos um no nao nulo e ele for
     * um no folha da arvore
     * */
    private boolean search(String str) {
        TrieNode node = searchNode(str);
        return node != null && node.isLeaf;
    }

    // verifica se alguma palavra na arvore comeca com o prefixo
    private boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }

    private static void test1() {
        String [][] words = {
            {"Amanda", "Madalena", "Bianca", "Patricia", "Amara", "Bruna"}
        };
        int idx = 0;
        ImplTrie implTrie = new ImplTrie();
        for(String str : words[idx])
            implTrie.insert(str);

        String [] strs = {"Pruno", "Bruno", "Bruna", "Maira", "Maiara", "Amanda", "Patricia", "patricia"};
        for(String str : strs)
            System.out.printf("%s %s\n", str
                    , implTrie.search(str) ? "Existe" : "Não Existe");

        System.out.println("\nPesquisando prefixos");
        strs = new String[] {"Mada", "Mai", "Pa", "Am", "B", "Br", "Bruno"};
        for(String str : strs)
            System.out.printf("Prefixo: %s %s\n", str
                    , implTrie.startsWith(str) ? "Existe" : "Não Existe");
    }

    public static void main(String[] args) {
        test1();
    }
}
