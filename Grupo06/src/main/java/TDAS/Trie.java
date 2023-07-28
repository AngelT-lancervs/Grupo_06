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

    public void addLeave(String word) {
        
    }

    public boolean deleteLeave(String leave) {
        return false;
    }

    public List<String> searchPrefix(String letters) {
        return null;
    }

    public List<String> searchSimilar(String word) {
        return null;
    }

    public List<String> searchReverse(String letters) {
        return null;
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
