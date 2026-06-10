/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectdatastructure;

public class SportsSystem {

    HashMap<Integer, Student> students;
    HashMap<String, Sport>    sports;

    SinglyLinkedList<String> practicedSportsBuffer = new SinglyLinkedList<>();
    SinglyLinkedList<String> interestSportsBuffer  = new SinglyLinkedList<>();

    SportsSystem() {
        students = new HashMap<>();
        sports   = new HashMap<>();
    }

    public void createStudent(String name, int id) {
        if (getStudent(id) != null) return;

        Student newStudent = new Student(name, id);

        SingleNode<String> currentPracticed = practicedSportsBuffer.head;
        while (currentPracticed != null) {
            Sport found = getSport(currentPracticed.value);
            if (found != null) {
                DobleNode<Student> ref = found.addPracticer(newStudent);
                newStudent.addPractice(found, ref);
            }
            currentPracticed = currentPracticed.next;
        }

        SingleNode<String> currentInterest = interestSportsBuffer.head;
        while (currentInterest != null) {
            Sport found = getSport(currentInterest.value);
            if (found != null) {
                newStudent.addInterest(found);
            }
            currentInterest = currentInterest.next;
        }

        students.put(id, newStudent);
    }

    public void removeStudent(int id) {
        Student stu = students.get(id);
        if (stu == null) return;

        SingleNode<SportEntry> current = stu.practice.head;
        while (current != null) {
            Sport sport             = current.value.sport;
            DobleNode<Student> ref  = current.value.studentRef;
            sport.removePracticerByRef(ref);
            current = current.next;
        }

        students.remove(id);
    }

    public void addSport(String sportName) {
        if (getSport(sportName) != null) return;
        sports.put(sportName, new Sport(sportName));
    }

    public void removeSport(String sportName) {
        Sport target = getSport(sportName);
        if (target == null) return;

        DobleNode<Student> curr = target.practicers.head;
        while (curr != null) {
            DobleNode<Student> next = curr.next;
            curr.value.removePractice(target);
            curr = next;
        }

        DinamicArray<Student> all = students.getAll();
        for (int i = 0; i < all.getSize(); i++) {
            all.access(i).removeInterest(target);
        }

        sports.remove(sportName);
    }

    public void addSportToStudent(int id, String sportName) {
        Student stu   = getStudent(id);
        Sport   sport = getSport(sportName);
        if (stu == null || sport == null) return;

        if (findSportEntry(stu, sport) != null) return;

        DobleNode<Student> ref = sport.addPracticer(stu);
        stu.addPractice(sport, ref);
    }

    public void removeSportFromStudent(int id, String sportName) {
        Student stu   = getStudent(id);
        Sport   sport = getSport(sportName);
        if (stu == null || sport == null) return;

        SportEntry entry = findSportEntry(stu, sport);
        if (entry == null) return;

        sport.removePracticerByRef(entry.studentRef);
        stu.removePractice(sport);
    }

    
    public void printSportsByCount() {
        DinamicArray<Sport> arr = sports.getAll();
 
        int n = arr.getSize();
        for (int i = 1; i < n; i++) {
            Sport key = arr.access(i);
            int j = i - 1;
            while (j >= 0 && arr.access(j).amountStu < key.amountStu) {
                arr.set(j + 1, arr.access(j));
                j--;
            }
            arr.set(j + 1, key);
        }
    }
    public DinamicArray<Sport> getSportsSortedByCount() {
        DinamicArray<Sport> arr = sports.getAll();
        mergeSort(arr, 0, arr.getSize() - 1);
        return arr;
    }

    private void mergeSort(DinamicArray<Sport> arr, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private void merge(DinamicArray<Sport> arr, int left, int mid, int right) {
        int leftSize  = mid - left + 1;
        int rightSize = right - mid;

        DinamicArray<Sport> leftArr  = new DinamicArray<>();
        DinamicArray<Sport> rightArr = new DinamicArray<>();

        for (int i = 0; i < leftSize;  i++) leftArr.insert(arr.access(left + i));
        for (int i = 0; i < rightSize; i++) rightArr.insert(arr.access(mid + 1 + i));

        int i = 0, j = 0, k = left;
        while (i < leftSize && j < rightSize) {
            if (leftArr.access(i).amountStu >= rightArr.access(j).amountStu) {
                arr.set(k++, leftArr.access(i++));
            } else {
                arr.set(k++, rightArr.access(j++));
            }
        }
        while (i < leftSize)  arr.set(k++, leftArr.access(i++));
        while (j < rightSize) arr.set(k++, rightArr.access(j++));
    }

    public boolean isConnected(int studentId, String sportName) {
        Student source = getStudent(studentId);
        Sport   target = getSport(sportName);

        if (source == null || target == null) return false;

        if (findSportEntry(source, target) != null) return true;

        resetVisited();
        resetVisitedSports();

        Queue<Student> queue = new Queue<>();
        source.visited = true;
        queue.enqueue(source);

        while (!queue.isEmpty()) {
            Student current = queue.dequeue();

            SingleNode<SportEntry> entryNode = current.practice.head;
            while (entryNode != null) {
                SportEntry currentEntry = entryNode.value;

                if (currentEntry == null || currentEntry.sport == null) {
                    entryNode = entryNode.next;
                    continue;
                }

                Sport currentSport = currentEntry.sport;

                if (currentSport.compareTo(target) == 0) {
                    resetVisited();
                    resetVisitedSports();
                    return true;
                }

                if (!currentSport.visited) {
                    currentSport.visited = true;
                    DobleNode<Student> neighbor = currentSport.practicers.head;
                    while (neighbor != null) {
                        Student next = neighbor.value;
                        if (!next.visited) {
                            next.visited = true;
                            queue.enqueue(next);
                        }
                        neighbor = neighbor.next;
                    }
                }

                entryNode = entryNode.next;
            }
        }

        resetVisited();
        resetVisitedSports();
        return false;
    }

    public DinamicArray<DinamicArray<Student>> buildCommunities() {
        resetVisited();
        resetVisitedSports();

        DinamicArray<Student> allStudents = students.getAll();
        DinamicArray<DinamicArray<Student>> communities = new DinamicArray<>();

        for (int i = 0; i < allStudents.getSize(); i++) {
            Student start = allStudents.access(i);
            if (!start.visited) {
                communities.insert(bfsComponent(start));
            }
        }

        resetVisited();
        resetVisitedSports();
        return communities;
    }

    private DinamicArray<Student> bfsComponent(Student start) {
        DinamicArray<Student> community = new DinamicArray<>();
        Queue<Student> queue = new Queue<>();
        start.visited = true;
        queue.enqueue(start);

        while (!queue.isEmpty()) {
            Student current = queue.dequeue();
            community.insert(current);
            SingleNode<SportEntry> entryNode = current.practice.head;
            while (entryNode != null) {
                SportEntry entry = entryNode.value;
                if (entry != null && entry.sport != null) {
                    Sport s = entry.sport;
                    if (!s.visited) {
                        s.visited = true;
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
                }
                entryNode = entryNode.next;
            }
        }
        return community;
    }

    Student getStudent(int id) {
        return students.get(id);
    }

    Sport getSport(String name) {
        return sports.get(name);
    }

    private SportEntry findSportEntry(Student stu, Sport sport) {
        SingleNode<SportEntry> current = stu.practice.head;
        while (current != null) {
            if (current.value != null && current.value.sport == sport) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    private void resetVisited() {
        DinamicArray<Student> all = students.getAll();
        for (int i = 0; i < all.getSize(); i++) {
            all.access(i).visited = false;
        }
    }

    private void resetVisitedSports() {
        DinamicArray<Sport> all = sports.getAll();
        for (int i = 0; i < all.getSize(); i++) {
            all.access(i).visited = false;
        }
    }

    public void clearBuffers() {
        this.practicedSportsBuffer = new SinglyLinkedList<>();
        this.interestSportsBuffer  = new SinglyLinkedList<>();
    }
}
