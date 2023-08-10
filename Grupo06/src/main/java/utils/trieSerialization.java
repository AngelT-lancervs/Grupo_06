/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;


import TDAS.Tree;
import TDAS.Trie;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 *
 * @author Jorge Daniel
 */
public class trieSerialization<E> {
    
    public static <E> void serialize(Trie tree, String name) {
        try {
            FileOutputStream fout = new FileOutputStream("userData/serialized/"+name+".ser");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(tree);
            out.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
    public static <E> Trie unserialize(String name) {
        try {
            FileInputStream file = new FileInputStream("userData/serialized/"+name+".ser");
            ObjectInputStream in = new ObjectInputStream(file);
            Trie tree = (Trie) in.readObject();

            in.close();
            file.close();
            return tree;
        } catch (Exception e) {
            return null;
        }
    }
    
}
