/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDAS;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Grupo06
 */
public class Trie {

    private Node<String> root;

    public Trie() {
        this.root = null;
    }
    
    public Trie(String content){
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

            for(Trie child: this.root.getChildren()){
                count += child.countLeaves();
            }
            return count;
        }
    }

    public boolean searchLeave(String word) {
        return false;
    }

    public void addLeave(String leave) {
        
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
    public void add(Trie n){
        if(isEmpty()){
            this.root = n.root;
        }else{
            this.root.getChildren().add(n);
        }
    }
    
    public Node<String> getRoot(){
        return this.root;
    }
    
}
