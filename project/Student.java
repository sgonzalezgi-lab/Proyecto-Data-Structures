/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectdatastructure;

public class Student implements Comparable<Student> {
    
    String name;
    int ID;
    
    SinglyLinkedList<SportEntry> practice;
    SinglyLinkedList<Sport> interests;
    
    boolean visited;
 
 
    
    Student(String name, int ID) {
        this.name = name;
        this.ID = ID;
        this.practice = new SinglyLinkedList<>();
        this.interests = new SinglyLinkedList<>();
        this.visited = false;
    }
 
    
    public void addPractice(Sport sport, DobleNode<Student> ref) {
        SportEntry entry = new SportEntry(sport,ref);
        practice.pushFront(entry);
    }
 
    public void removePractice(Sport sport) {
        SingleNode<SportEntry> current = practice.head;
        while (current != null) {
            if (current.value.sport == sport) {
                
                practice.remove(current.value);
                return;
            }
            current = current.next;
        }
    }
 
    public void addInterest(Sport sport) {
        interests.pushFront(sport);
    }
 
    public void removeInterest(Sport sport) {
        interests.remove(sport);
    }
 
 
    public void printInfo() {
        System.out.println("-----------------------------");
        System.out.println("Name : " + name);
        System.out.println("ID   : " + ID);
        System.out.println("Sports practiced:");
        if (practice.isEmpty()) System.out.println("  None");
        else practice.print();
        System.out.println("Sports of interest:");
        if (interests.isEmpty()) System.out.println("  None");
        else interests.print();
        System.out.println("-----------------------------");
    }
 

    //is sort by id
    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.ID, other.ID);
    }
    
    @Override
    public String toString() {
        return ID + ": " + name;
    }
}