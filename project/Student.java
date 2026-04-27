public class Student implements Comparable<Student> {
    //attributes
    String name;
    int ID;
 
    
    SinglyLinkedList<Sport> practice;
    SinglyLinkedList<Sport> interests;
    DinamicArray<DobleNode<Student>> nodeRefs;
    
    //to carry out the searching
    boolean visited;
 
 
    
    Student(String name, int ID) {
        this.name = name;
        this.ID = ID;
        this.practice = new SinglyLinkedList<>();
        this.interests = new SinglyLinkedList<>();
        this.nodeRefs = new DinamicArray<>();
        this.visited = false;
    }
 
    
    Student(int ID) {
        this.ID = ID;
        this.practice = new SinglyLinkedList<>();
        this.interests = new SinglyLinkedList<>();
        this.nodeRefs = new DinamicArray<>();
        this.visited = false;
    }
 
    
    public void addPractice(Sport sport, DobleNode<Student> ref) {
        practice.pushFront(sport);
        nodeRefs.insert(ref);
    }
 
    public void removePractice(Sport sport, DobleNode<Student> ref) {
        practice.remove(sport);
        nodeRefs.delete(ref);
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
 

    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.ID, other.ID);
    }
 
    @Override
    public String toString() {
        return ID + ": " + name;
    }
}
