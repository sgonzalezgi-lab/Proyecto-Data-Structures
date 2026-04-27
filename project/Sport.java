public class Sport implements Comparable<Sport> {
    //attributes
    String name;
    int amountStu;
    DoublyLinkedList<Student> practicers;
 
    
    
    Sport(String name) {
        this.name = name;
        this.amountStu = 0;
        this.practicers = new DoublyLinkedList<>();
    }
 
    
    public DobleNode<Student> addPracticer(Student student) {
        DobleNode<Student> ref = practicers.pushFront(student);
        amountStu++;
        //return the reference to save in the dinamicArray of references in the student
        return ref;
    }
 
    
    public void removePracticerByRef(DobleNode<Student> ref) {
        //to remove the students of sports when a student is removed
        practicers.removeByReference(ref);
        amountStu--;
    }
 
    public void removePracticer(Student student) {
        practicers.remove(student);
        amountStu--;
    }
 
 
    public void printInfoPracticers() {
        System.out.println("Students who practice " + this.name + " (" + amountStu + "):");
        practicers.print();
    }
 
 
    @Override
    public int compareTo(Sport other) {
        return this.name.compareTo(other.name);
    }
 
    @Override
    public String toString() {
        return name + " (" + amountStu + " students)";
    }
}
