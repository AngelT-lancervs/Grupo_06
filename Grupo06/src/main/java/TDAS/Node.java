/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDAS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Grupo06
 */
public class Node<E> {
    
    private E content;
    private Map<E, Trie> children;
    
    
    public Node(E content){
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
    
}
