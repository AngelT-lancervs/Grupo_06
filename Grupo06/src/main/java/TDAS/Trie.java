/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDAS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

    public Trie(String content) {
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

            for (Trie child : this.root.getChildren()) {
                count += child.countLeaves();
            }
            return count;
        }
    }

    public boolean searchLeave(String word) {
        if (isEmpty()) {
            return false;
        } else if (isLeaf()) {
            return false;
        }
        Trie subTrie = this;

        for (char c : word.toCharArray()) {
            subTrie = subTrie.root.getChildren().get(c);

            if (subTrie == null) {
                return false;
            }
        }
        return subTrie.isLeaf();
    }

//    public boolean addLeave(String word) {
//        if (word == null || word.equals("") || word.contains("  ")) {
//        return false;
//        }
//        
//        Trie current = this;
//        for(char ch:word.toCharArray()){
//            if(current.root.getChildren().)
//        }
//        
//
//    }

    public boolean deleteLeave(String leave) {
        return false;
    }

    public List<String> searchPrefix(String letters) {
        return null;
    }

    public List<String> searchSimilar(String word) {

        if (word == null || word.equals("") || word.contains("  ")) {
            return null;

        }
        List<String> leaves = this.getLeaves();
        List<String> similars = new LinkedList<>();

        for(String leave:leaves){
            if(Math.abs(leave.length()-word.length())!=0){
                if(levenshteinAlgorithm(word, leave)<3){
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
           

            for (Trie child : this.root.getChildren()) {
                leaves.addAll(child.getLeaves());
            }
            return leaves;
        }
    }

    //MÉTODO PARA PROBAR EL TRIE (BORRAR AL FINAL)
    //Agrega de forma horizontal al root 
    public void add(Trie n) {
        if (isEmpty()) {
            this.root = n.root;
        } else {
            this.root.getChildren().add(n);
        }
    }

    public Node<String> getRoot() {
        return this.root;
    }

}
