/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectdatastructure;

public class DobleNode<T> {
    T value;
    DobleNode<T> next;
    DobleNode<T> prev;
 
    DobleNode(T value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }
}
