/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package TDAS;

/**
 *
 * @author ASUS
 * @param <E>
 */
public interface Tree<E> {

    public boolean isEmpty();

    public boolean isLeaf();

    public boolean search(E element);
    
    public boolean add(E element);
    
    public boolean remove(E element);
    
    public int countLevel();
    
    
    
}
