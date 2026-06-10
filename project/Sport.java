/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectdatastructure;

public class Sport implements Comparable<Sport> {
 
    String name;
    int amountStu;
    DoublyLinkedList<Student> practicers;
    boolean visited;
 
    Sport(String name) {
        this.name = name;
        this.amountStu = 0;
        this.practicers = new DoublyLinkedList<>();
        this.visited = false;
    }
 
    public DobleNode<Student> addPracticer(Student student) {
        //save the reference of the student
        //to make more efficient the removing of student
        DobleNode<Student> ref = practicers.pushFront(student);
        amountStu++;
        return ref;
    }
 
    public void removePracticerByRef(DobleNode<Student> ref) {
        //take advantage of the reference to remove the student
        practicers.removeByReference(ref);
        amountStu--;
    }
 
    public void removePracticer(Student student) {
        //this is used when we wanna remove a student only from a sport
        practicers.remove(student);
        amountStu--;
    }
 
 
    public void printInfoPracticers() {
        System.out.println("Students who practice " + this.name + " (" + amountStu + "):");
        practicers.print();
    }
 
    //they are ordered by name
    @Override
    public int compareTo(Sport other) {
        return this.name.compareTo(other.name);
    }
 
    @Override
    public String toString() {
        return name + " (" + amountStu + " students)";
    }
}