/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.datastructuresproject;

import java.util.Scanner;
 
public class SportsSystem {
 
    
    HashMap<Integer, Student> students;
    AVL<Sport> sports;
 
    SinglyLinkedList<String> practicedSportsBuffer = new SinglyLinkedList<>();
    SinglyLinkedList<String> interestSportsBuffer  = new SinglyLinkedList<>();
 
    SportsSystem() {
        students = new HashMap<>();
        sports   = new AVL<>();
    }
 
    
 
    public void createStudent(String name, int id, boolean silent) {
        if (getStudent(id) != null) {
            if (!silent) System.out.println("A student with ID " + id + " already exists.");
            return;
        }
 
        Student newStudent = new Student(name, id);
 
        //using GUI
        if (silent) {
            SingleNode<String> currentPracticed = practicedSportsBuffer.head;
            while (currentPracticed != null) {
                String sportName = currentPracticed.value;
                Sport found = getSport(sportName);
                if (found != null) {
                    DobleNode<Student> ref = found.addPracticer(newStudent);
                    newStudent.addPractice(found, ref);
                }
                currentPracticed = currentPracticed.next;
            }
            
            SingleNode<String> currentInterest = interestSportsBuffer.head;
            while (currentInterest != null) {
                String sportName = currentInterest.value;
                Sport found = getSport(sportName);
                if (found != null) {
                    newStudent.addInterest(found);
                }
                currentInterest = currentInterest.next;
            }
        } else {// By console
            Scanner scan = new Scanner(System.in);
            System.out.println("Which sports does " + name + " practice? (type 0 to finish)");
            showSports();
            String input = scan.nextLine().trim();
            while (!input.equals("0")) {
                Sport found = getSport(input);
                if (found == null) System.out.println("  Sport not found: " + input);
                else {
                    DobleNode<Student> ref = found.addPracticer(newStudent);
                    newStudent.addPractice(found, ref);
                }
                input = scan.nextLine().trim();
            }
            System.out.println("Which sports is " + name + " interested in? (type 0 to finish)");
            showSports();
            input = scan.nextLine().trim();
            while (!input.equals("0")) {
                Sport found = getSport(input);
                if (found == null) System.out.println("  Sport not found: " + input);
                else newStudent.addInterest(found);
                input = scan.nextLine().trim();
            }
        }
 
        students.put(id, newStudent);
        if (!silent) System.out.println("Student " + name + " (ID " + id + ") registered.");
    }
 
 
    public void removeStudent(int id) {
        Student target = getStudent(id);
        if (target == null) {
            if (Main.consoleLog) System.out.println("Student with ID " + id + " not found.");
            return;
        }
 
        SingleNode<Sport> pos = target.practice.head;
        while (pos != null) {
            Sport sport = pos.value;
            DobleNode<Student> ref = findRefForSport(target, sport);
            if (ref != null) {
                sport.removePracticerByRef(ref);
            }
            pos = pos.next;
        }
 
        students.remove(id);
        if (Main.consoleLog) System.out.println("Student " + target.name + " removed.");
    }
 
 
    public void addSport(String sportName) {
        if (getSport(sportName) != null) {
            if (Main.consoleLog) System.out.println("Sport '" + sportName + "' already exists.");
            return;
        }
        sports.insert(new Sport(sportName));
        if (Main.consoleLog) System.out.println("Sport '" + sportName + "' added.");
    }
 
    public void removeSport(String sportName) {
        Sport target = getSport(sportName);
        if (target == null) {
            if (Main.consoleLog) System.out.println("Sport '" + sportName + "' not found.");
            return;
        }
 
        DobleNode<Student> curr = target.practicers.head;
        while (curr != null) {
            DobleNode<Student> next = curr.next;
            Student stu = curr.value;
            stu.practice.remove(target);
            stu.nodeRefs.delete(curr);
            curr = next;
        }
 
        DinamicArray<Student> all = students.getAll();
        for (int i = 0; i < all.getSize(); i++) {
            all.access(i).interests.remove(target);
        }
 
        sports.delete(target);
        if (Main.consoleLog) System.out.println("Sport '" + sportName + "' removed from the system.");
    }
 
 
    public void addSportToStudent(int id, String sportName) {
        Student stu   = getStudent(id);
        Sport   sport = getSport(sportName);
 
        if (stu == null)   { if (Main.consoleLog) System.out.println("Student not found.");  return; }
        if (sport == null) { if (Main.consoleLog) System.out.println("Sport not found.");    return; }
 
        if (stu.practice.find(sport) != null) {
            if (Main.consoleLog) System.out.println(stu.name + " already practices " + sportName + ".");
            return;
        }
 
        DobleNode<Student> ref = sport.addPracticer(stu);
        stu.addPractice(sport, ref);
        if (Main.consoleLog) System.out.println(sportName + " added to " + stu.name + "'s practice list.");
    }
 
