/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDAS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Grupo06
 */
public class Trie {

    private Node<String> root;

    public Trie() {
        this.root = null;
    }

    private Trie(String content) {
        this.root = new Node<>(content);
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public boolean isLeaf() {
        return this.root.getChildren().isEmpty();
    }

    public int countLeaves() {
        if (isEmpty()) {
            return 0;
        } else if (isLeaf()) {
            return 1;
        } else {

            int count = 0;
            Map<String, Trie> children = this.root.getChildren();
            Set<String> keys = children.keySet();

            for (String key : keys) {
                count += children.get(key).countLeaves();
            }

            return count;
        }
    }

    public boolean searchLeave(String word) {
        if (!isValidWord(word) || this.isEmpty()) {
            return false;
        }
        Trie subTrie = this;

        for (int i = 0; i < word.length() - 1; i++) {

            Map<String, Trie> children = subTrie.root.getChildren();
            subTrie = children.get(Character.toString(word.charAt(i)));
            if (subTrie == null) {
                return false;
            }
        }
        return subTrie.root.getChildren().get(word) != null;
    }

    public boolean addLeave(String word) {
        if (!isValidWord(word)) {
            return false;
        }
        if (this.isEmpty()) {
            this.root = new Node("");
        }

        Trie current = this;
        for (int i = 0; i < word.length(); i++) {
            String content = Character.toString(word.charAt(i));
            Map<String, Trie> children = current.root.getChildren();

            if (children.get(content) == null) {
                children.put(content, new Trie(content));
            }
            current = children.get(content);

            if (i == (word.length() - 1)) {
                if (!children.get(content).isLeaf()) {
                    children.put(word, new Trie(word));
                } else {
                    children.remove(content);
                    children.put(word, new Trie(word));
                }
            }
        }

        return true;

    }

    public boolean deleteLeave(String leave) {

        if (this.isEmpty() || !getLeaves().contains(leave)) {
            return false;
        }

        Trie currentTrie = this;

        
//        String nextChar=" ";
        
        
        String content ="";
        
        
        for (int i = 0; i < leave.length() - 1; i++) {
            content = Character.toString(leave.charAt(i));
            Map<String, Trie> children = currentTrie.root.getChildren();

            
            
            if(children.get(content).getLeaves().size()>1){
                currentTrie = children.get(content);
            }else{
               break;
            } 
            
        }
        
        if(currentTrie.root.getChildren().get(leave)!=null){
            currentTrie.root.getChildren().remove(leave);
            return true;
        }else{
            currentTrie.root.getChildren().remove(content);
            return true;
        }
        

    }

    public List<String> searchByPrefix(String prefix) {
        if (!isValidWord(prefix) || this.isEmpty()) {
            return null;
        }
        Trie subTrie = this;

        for (char ch : prefix.toCharArray()) {

            Map<String, Trie> children = subTrie.root.getChildren();
            subTrie = children.get(Character.toString(ch));
            if (subTrie == null) {
                return null;
            }
        }
        return subTrie.getLeaves();
    }

    public List<String> searchSimilar(String word) {

        if (!isValidWord(word)) {
            return null;

        }
        List<String> leaves = this.getLeaves();
        List<String> similars = new LinkedList<>();

        for (String leave : leaves) {
            if (Math.abs(leave.length() - word.length()) != 0) {
                if (levenshteinAlgorithm(word, leave) < 3) {
                    similars.add(leave);
                }
            }
        }
        return similars;
    }

    public List<String> searchReverse(String letters) {
        return null;
    }

    public static int levenshteinAlgorithm(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        // Casos base: inicialización de la primera fila y la primera columna
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // Cálculo de la distancia de edición usando programación dinámica
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = (word1.charAt(i - 1) == word2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }

        return dp[m][n];
    }

    public List<String> getLeaves() {
        List<String> leaves = new LinkedList<>();
        if (isEmpty()) {
            return null;
        } else if (isLeaf()) {
            leaves.add(this.root.getContent());
            return leaves;
        } else {
            Map<String, Trie> children = this.root.getChildren();
            Set<String> keys = children.keySet();

            for (String key : keys) {
                leaves.addAll(children.get(key).getLeaves());
            }
            return leaves;
        }
    }

    private boolean isValidWord(String word) {
        return word != null && !word.equals("") && !word.contains("  ");
    }

    //MÉTODO PARA PROBAR EL TRIE (BORRAR AL FINAL)
    //Agrega de forma horizontal al root 
    public void add(Trie n) {
        if (isEmpty()) {
            this.root = new Node("");
        }
        this.root.getChildren().put(n.getRoot().getContent(), n);

    }

    public Node<String> getRoot() {
        return this.root;
    }

//    @Override
//    public String toString() {
//        return "Trie{" + "root=" + root + ", leaves=" + getLeaves() + '}';
//    }

    @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    toStringRecursive(root, sb, 0);
    return sb.toString();
}

private void toStringRecursive(Node<String> node, StringBuilder sb, int depth) {
    if (node == null) {
        return;
    }

    for (int i = 0; i < depth; i++) {
        sb.append("  ");
    }

    sb.append("|-- ");
    sb.append(node.getContent());
    sb.append("\n");

    Map<String, Trie> children = node.getChildren();
    for (Trie childTrie : children.values()) {
        toStringRecursive(childTrie.root, sb, depth + 1);
    }
}

    
    
}
