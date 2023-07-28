/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDAS;

import java.util.List;

/**
 *
 * @author Grupo06
 */
public class Trie<E> {
    
    private Node<E> root;
    
    public Trie(){
        this.root = null;
    }
    
    public boolean isEmpty(){
        return this.root == null;
    }
    
    public boolean isLeaf(){
        return this.root.getChildren().isEmpty();
    }
    
    public int countLeaves(){
        return 0;
    }
    
    public boolean searchLeave(E content){
        return false;
    }
    
    public void addLeave(E content){
    
    }
    
    public boolean deleteLeave(E content){
      return false;  
    }
    
    public List<E> searchPrefix(E content){
    
        return null;
    }
    
    public List<E> searchSimilar(E content){
        return null;
    }
    
    public List<E> searchReverse(E content){
        return null;
    }
    
    
    
        
}
