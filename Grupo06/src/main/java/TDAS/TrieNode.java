/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDAS;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Grupo06
 */
public class TrieNode<E> implements Serializable{
    
    private E content;
    private Map<E, Trie> children;
    
    
    public TrieNode(E content){
        this.content = content;
        this.children = new HashMap<>();
    }
    
    public void setContent(E content){
        this.content = content;
    }
    
    public E getContent(){
        return this.content;
    }
    
    public Map<E, Trie> getChildren(){
        return this.children;
    }

    @Override
    public String toString() {
        return "Node{" + "content=" + content + ", children=" + children + '}';
    }
    
}
