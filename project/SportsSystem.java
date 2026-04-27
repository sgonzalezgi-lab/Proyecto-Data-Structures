import java.util.Scanner;
 
public class SportsSystem {
    
    //main structures
    AVL<Student> students;
    AVL<Sport>   sports;
 
    SportsSystem() {
        students = new AVL<>();
        sports   = new AVL<>();
    }
 
 
    public void createStudent(String name, int id) {
        if (getStudent(id) != null) {
            System.out.println("A student with ID " + id + " already exists.");
            return;
        }
 
        Scanner scan = new Scanner(System.in);
        Student newStudent = new Student(name, id);
 
        System.out.println("Which sports does " + name + " practice? (type 0 to finish)");
        showSports();
        String input = scan.nextLine();
 
        while (!input.equals("0")) {
            Sport found = getSport(input);
            if (found == null) {
                System.out.println("  Sport not found: " + input);
            } else {
                //add student into the lists of the sports which he practice
                DobleNode<Student> ref = found.addPracticer(newStudent);
                newStudent.addPractice(found, ref);
            }
            
            input = scan.nextLine();
        }
 

        System.out.println("Which sports is " + name + " interested in? (type 0 to finish)");
        showSports();
        input = scan.nextLine();
 
        while (!input.equals("0")) {
            Sport found = getSport(input);
            if (found == null) {
                System.out.println("  Sport not found: " + input);
            } else {
                newStudent.addInterest(found);
            }
            input = scan.nextLine();
        }
 
        students.insert(newStudent);
        System.out.println("Student " + name + " (ID " + id + ") registered.");
    }
 
 
    public void removeStudent(int id) {
        Student target = getStudent(id);
        if (target == null) {
            System.out.println("Student with ID " + id + " not found.");
            return;
        }
 

        int total = target.nodeRefs.getSize();
        for (int i = 0; i < total; i++) {
            DobleNode<Student> ref = target.nodeRefs.access(i);
            
            SingleNode<Sport> pos = target.practice.head;
            while (pos != null) {
                Sport s = pos.value;
                if (s.practicers.find(target) == ref ||
                    s != null && refBelongsToSport(ref, s)) {
                    s.removePracticerByRef(ref);
                    break;
                }
                pos = pos.next;
            }
        }
 
        students.delete(target);
        System.out.println("Student " + target.name + " removed.");
    }
 

    private boolean refBelongsToSport(DobleNode<Student> ref, Sport sport) {
        DobleNode<Student> temp = sport.practicers.head;
        while (temp != null) {
            if (temp == ref) return true;
            temp = temp.next;
        }
        return false;
    }
 
    
 
    public void addSport(String sportName) {
        if (getSport(sportName) != null) {
            System.out.println("Sport '" + sportName + "' already exists.");
            return;
        }
        sports.insert(new Sport(sportName));
        System.out.println("Sport '" + sportName + "' added.");
    }
 

    public void removeSport(String sportName) {
        Sport target = getSport(sportName);
        if (target == null) {
            System.out.println("Sport '" + sportName + "' not found.");
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
 
        
        resetVisited(students.root);
        removeFromInterests(students.root, target);
 
        sports.delete(target);
        System.out.println("Sport '" + sportName + "' removed from the system.");
    }
 
    private void removeFromInterests(AVL<Student>.Node node, Sport sport) {
        if (node == null) return;
        node.value.interests.remove(sport);
        removeFromInterests(node.left, sport);
        removeFromInterests(node.right, sport);
    }
 
    
 
    public void addSportToStudent(int id, String sportName) {
        Student stu   = getStudent(id);
        Sport   sport = getSport(sportName);
 
        if (stu == null)   { System.out.println("Student not found.");  return; }
        if (sport == null) { System.out.println("Sport not found.");    return; }
 
        
        if (stu.practice.find(sport) != null) {
            System.out.println(stu.name + " already practices " + sportName + ".");
            return;
        }
 
        DobleNode<Student> ref = sport.addPracticer(stu);
        stu.addPractice(sport, ref);
        System.out.println(sportName + " added to " + stu.name + "'s practice list.");
    }
 
    
 
    public void removeSportFromStudent(int id, String sportName) {
        Student stu   = getStudent(id);
        Sport   sport = getSport(sportName);
 
        if (stu == null)   { System.out.println("Student not found.");  return; }
        if (sport == null) { System.out.println("Sport not found.");    return; }
 
        
        DobleNode<Student> ref = findRefForSport(stu, sport);
        if (ref == null) {
            System.out.println(stu.name + " does not practice " + sportName + ".");
            return;
        }
 
        sport.removePracticerByRef(ref);
        stu.removePractice(sport, ref);
        System.out.println(sportName + " removed from " + stu.name + "'s practice list.");
    }
 
    
    private DobleNode<Student> findRefForSport(Student stu, Sport sport) {
        DobleNode<Student> sportDLLHead = sport.practicers.head;
        for (int i = 0; i < stu.nodeRefs.getSize(); i++) {
            DobleNode<Student> ref = stu.nodeRefs.access(i);
            
            if (refBelongsToSport(ref, sport)) {
                return ref;
            }
        }
        return null;
    }
 
    
 
    public void searchStudent(int id) {
        Student found = getStudent(id);
        if (found != null) {
            found.printInfo();
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }
 
    
 
    public void printStudentsBySport(String sportName) {
        Sport found = getSport(sportName);
        if (found != null) {
            found.printInfoPracticers();
        } else {
            System.out.println("Sport '" + sportName + "' not found.");
        }
    }
 
   
 
    public void printSportsByCount() {
        
        DinamicArray<Sport> arr = new DinamicArray<>();
        sports.inOrderToArray(sports.root, arr);
 
       
        int n = arr.getSize();
        for (int i = 1; i < n; i++) {
            Sport key = arr.access(i);
            int j = i - 1;
            while (j >= 0 && arr.access(j).amountStu < key.amountStu) {
                arr.values[j + 1] = arr.values[j];
                j--;
            }
            arr.values[j + 1] = key;
        }
 
        System.out.println("Sports ranked by number of practitioners:");
        for (int i = 0; i < n; i++) {
            System.out.println("  " + (i + 1) + ". " + arr.access(i));
        }
    }
 
    
 
    
    public boolean isConnected(int studentId, String sportName) {
        Student source = getStudent(studentId);
        Sport   target = getSport(sportName);
 
        if (source == null) { System.out.println("Student not found.");  return false; }
        if (target == null) { System.out.println("Sport not found.");    return false; }
 
        
        if (source.practice.find(target) != null) {
            System.out.println(source.name + " already practices " + sportName + ".");
            return true;
        }
 
        resetVisited(students.root);
 
        Queue<Student>  queue         = new Queue<>();
        AVL<Sport>      visitedSports = new AVL<>();
 
        source.visited = true;
        queue.enqueue(source);
 
        while (!queue.isEmpty()) {
            Student current = queue.dequeue();
 
            
            SingleNode<Sport> sportNode = current.practice.head;
            while (sportNode != null) {
                Sport currentSport = sportNode.value;
 
                
                if (currentSport == null) {
                    sportNode = sportNode.next;
                    continue;
                }
 
                
                if (currentSport.compareTo(target) == 0) {
                    System.out.println("Connected! " + current.name +
                        " practices " + sportName + ".");
                    resetVisited(students.root);
                    return true;
                }
 
                
                if (!visitedSports.isTheElement(currentSport)) {
                    visitedSports.insert(currentSport);
 
                    DobleNode<Student> neighbor = currentSport.practicers.head;
                    while (neighbor != null) {
                        Student nextStudent = neighbor.value;
                        if (!nextStudent.visited) {
                            nextStudent.visited = true;
                            queue.enqueue(nextStudent);
                        }
                        neighbor = neighbor.next;
                    }
                }
 
                sportNode = sportNode.next;
            }
        }
 
        System.out.println("No connection found between " +
            source.name + " and anyone who practices " + sportName + ".");
        resetVisited(students.root);
        return false;
    }
 
    public void buildCommunities() {
        resetVisited(students.root);
 
        DinamicArray<Student> allStudents = new DinamicArray<>();
        students.inOrderToArray(students.root, allStudents);
 
        int communityIndex = 1;
 
        for (int i = 0; i < allStudents.getSize(); i++) {
            Student start = allStudents.access(i);
            if (!start.visited) {
                System.out.println("\n--- Community " + communityIndex++ + " ---");
                bfsComponent(start);
            }
        }
 
        resetVisited(students.root);
    }
 
    private void bfsComponent(Student start) {
        Queue<Student> queue        = new Queue<>();
        AVL<Sport>     visitedSports = new AVL<>();
 
        start.visited = true;
        queue.enqueue(start);
 
        while (!queue.isEmpty()) {
            Student current = queue.dequeue();
            System.out.println("  " + current);
 
            SingleNode<Sport> sportNode = current.practice.head;
            while (sportNode != null) {
                Sport s = sportNode.value;
                if (s != null && !visitedSports.isTheElement(s)) {
                    visitedSports.insert(s);
                    DobleNode<Student> neighbor = s.practicers.head;
                    while (neighbor != null) {
                        Student next = neighbor.value;
                        if (!next.visited) {
                            next.visited = true;
                            queue.enqueue(next);
                        }
                        neighbor = neighbor.next;
                    }
                }
                sportNode = sportNode.next;
            }
        }
    }
 
    
 
    private Student getStudent(int id) {
        return students.find(new Student(id));
    }
 
    private Sport getSport(String name) {
        return sports.find(new Sport(name));
    }
 
    private void showSports() {
        if (sports.root == null) {
            System.out.println("  (no sports registered yet)");
        } else {
            sports.print();
        }
    }
 
    
    private void resetVisited(AVL<Student>.Node node) {
        if (node == null) return;
        node.value.visited = false;
        resetVisited(node.left);
        resetVisited(node.right);
    }
}
