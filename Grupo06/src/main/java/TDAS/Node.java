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
public class Node<E> {
    
    private E content;
    private List<Trie<E>> children;
    
    
    public Node(E content){
        this.content = content;
        this.children = new ArrayList<>();
    }
    
    
    public void setContent(E content){
        this.content = content;
    }
    
    public E getContent(){
        return this.content;
    }
    
    public List<Trie<E>> getChildren(){
        return this.children;
    }
    
}
