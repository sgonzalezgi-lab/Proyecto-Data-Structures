public class MatchMaker {
    class CustomQueue {
        Estudiante[] elements;
        int head, tail, size;

        CustomQueue() {
            elements = new Estudiante[10]; // Tamaño inicial
            head = 0;
            tail = 0;
            size = 0;
        }

        void enqueue(Estudiante e) {
            if (size == elements.length) {
                Estudiante[] newElements = new Estudiante[elements.length * 2];
                for (int i = 0; i < size; i++) {
                    newElements[i] = elements[(head + i) % elements.length];
                }
                elements = newElements;
                head = 0;
                tail = size;
            }
            elements[tail] = e;
            tail = (tail + 1) % elements.length;
            size++;
        }

        Estudiante dequeue() {
            if (size == 0)
                return null; // O lanzar excepción
            Estudiante e = elements[head];
            head = (head + 1) % elements.length;
            size--;
            return e;
        }

        boolean isEmpty() {
            return size == 0;
        }
    }

    public class VisitedList {
        int[] ids;
        int count;

        VisitedList() {
            ids = new int[10]; // Tamaño inicial
            count = 0;
        }

        void add(int id) {
            if (count == ids.length) {
                int[] newIds = new int[ids.length * 2];
                for (int i = 0; i < count; i++) {
                    newIds[i] = ids[i];
                    ids = newIds;
                }
                ids[count++] = id;
            }
            ids[count++] = id;
        }

        boolean contains(int id) {
            for (int i = 0; i < count; i++) {
                if (ids[i] == id)
                    return true;
            }
            return false;
        }

        public void BuscarConexion(AVLtree db, Estudiante startStudent, String TargetSport) {
            CustomQueue queue = new CustomQueue();
            VisitedList visited = new VisitedList();
            queue.enqueue(startStudent);
            visited.add(startStudent.ID);
            while (!queue.isEmpty()) {
                Estudiante current = queue.dequeue();
                if (current.ID != startStudent.ID && deportesPracticados(current, TargetSport)) {
                    System.out.println("Conexión encontrada: " + current.name + " practica " + TargetSport);
                    return;
                }
                LinkedList<Referente>.Node currentRefNode = current.conexiones.head;
                while (currentRefNode != null) {
                    Referente ref = currentRefNode.value;
                    if (!visited.contains(ref.ID)) {
                        visited.add(ref.ID);
                        Estudiante nextStudent = db.find(ref.ID);
                        if (nextStudent != null) {
                            queue.enqueue(nextStudent);
                            }
                        }
                        currentRefNode = currentRefNode.next;
                    }
                }
                System.out.println("No se encontró una conexión para " + TargetSport);
            }
        }
        public boolean deportesPracticados(Estudiante student, String sport) {
        LinkedList<Deporte>.Node currentNode = student.deportesPracticados.head;
        
        while (currentNode != null) {
            Deporte d = currentNode.value;
            // Comparamos el nombre del deporte (d.name) con el deporte buscado
            if (d.name.equalsIgnoreCase(sport)) return true;
            currentNode = currentNode.next;
        }
        return false;
    }
}

