/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectdatastructure;

public class HashSet<T> {
    
    private DoublyLinkedList<T>[] harray;
    private int capacity;
    private int size;
    
    public HashSet() {
        this(16);
    }
    
    
    public HashSet(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        
        this.harray = (DoublyLinkedList<T>[]) new DoublyLinkedList[capacity];
        
        for (int i = 0; i < capacity; i++) {
            harray[i] = new DoublyLinkedList<T>();
        }
    }
    
   
    private int hash(T val) {
        if (val == null) return 0;
        return (val.hashCode() & 0x7fffffff) % capacity;
    }
    
    public boolean find(T val) {
        int index = hash(val);
        return harray[index].find(val) != null;
    }
    
    public void add(T val) {
        if (find(val)) {
            return;
        }
        int index = hash(val);
        harray[index].pushFront(val);
        size++;
    }
    
    public void remove(T val) {
        int index = hash(val);
        if (harray[index].find(val) != null) {
            harray[index].remove(val);
            size--;
        }
    }
    
    public int getSize() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            harray[i] = new DoublyLinkedList<T>();
        }
        size = 0;
    }
}
