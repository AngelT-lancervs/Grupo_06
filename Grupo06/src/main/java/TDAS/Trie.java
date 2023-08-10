/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDAS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
public class Trie implements Tree<String>,Serializable {

    private TrieNode<String> root;

    public Trie() {
        this.root = null;
    }

    private Trie(String content) {
        this.root = new TrieNode<>(content);
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public boolean isLeaf() {
        return this.root.getChildren().isEmpty();
    }

    public Map<String, Trie> getChildren() {
        return root.getChildren();
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

    public int countLetter(String letter) {
        if (isEmpty()) {
            return 0;
        }

        int count = 0;
        if (this.root.getContent().equals(letter)) {
            count += this.countLeaves();
        }

        Map<String, Trie> children = this.root.getChildren();
        for (Trie childTrie : children.values()) {
            count += childTrie.countLetter(letter);
        }

        return count;
    }

    @Override
    public boolean search(String word) {
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

    @Override
    public boolean add(String word) {
        if (!isValidWord(word)) {
            return false;
        }
        if (this.isEmpty()) {
            this.root = new TrieNode("");
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

    @Override
    public boolean remove(String leave) {
        if (this.isEmpty() || !search(leave)) {
            return false;
        }
        Trie currentTrie = this;
        String content = "";

        for (int i = 0; i < leave.length() - 1; i++) {
            content = Character.toString(leave.charAt(i));
            Map<String, Trie> children = currentTrie.root.getChildren();
            if (children.get(content).getLeaves().size() > 1) {
                currentTrie = children.get(content);
            } else {
                break;
            }
        }

        if (currentTrie.root.getChildren().get(leave) != null) {
            currentTrie.root.getChildren().remove(leave);
            return true;
        } else {
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
        if (!isValidWord(word) || this.isEmpty()) {
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

    //Revisar este metodo ya que no se si esto sea legal
    public List<String> searchReverse(String letters) {
        if (!isValidWord(letters) || this.isEmpty()) {
            return null;
        }
        List<String> words = getLeaves();
        List<String> wordsEndWithLetters = new LinkedList<>();

        for (String word : words) {
            if (word.endsWith(letters)) {
                wordsEndWithLetters.add(word);
            }
        }

        return wordsEndWithLetters;
    }

    private static int levenshteinAlgorithm(String word1, String word2) {
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

    public List<Integer> getLeavesLevels() {
        if (this.isEmpty()) {
            return null;
        }

        return getAllLeavesLevelsRecursive(0);
    }

    private List<Integer> getAllLeavesLevelsRecursive(int i) {
        List<Integer> list = new LinkedList<>();
        if (this.isEmpty()) {
            return null;
        }
        if (this.isLeaf()) {
            list.add(i);

            return list;
        }
        Map<String, Trie> children = this.root.getChildren();
        Set<String> keys = children.keySet();

        for (String key : keys) {
            list.addAll(children.get(key).getAllLeavesLevelsRecursive(i + 1));
        }
        return list;

    }

    private boolean isValidWord(String word) {
        return word != null && !word.equals("") && !word.contains(" ");
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

    private void toStringRecursive(TrieNode<String> node, StringBuilder sb, int depth) {
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

    public List<String> getLeavesByLevel(int level) {
        List<String> list = new LinkedList<>();
        if (this.isEmpty()) {
            return null;

        }
        if (level == 0&& this.isLeaf()) {
            list.add(this.root.getContent());
            return list;
        }
        
        for(Trie childTrie: this.getChildren().values()){
            list.addAll(childTrie.getLeavesByLevel(level-1));
        }
        return list;
        
    }

    @Override
    public int countLevel() {
        if (this.isEmpty()) {
            return 0;
        }

        int maxChildLevel = 0;
        for (Trie child : this.getChildren().values()) {
            int childLevel = child.countLevel() + 1;
            maxChildLevel = Math.max(maxChildLevel, childLevel);
        }

        return maxChildLevel;

    }


}
