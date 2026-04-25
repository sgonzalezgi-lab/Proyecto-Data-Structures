public class Estudiante implements Comparable<Estudiante>{
    int ID;
    String name;
    LinkedList<Referente> conexiones; //Grafo explicito
    LinkedList<Deporte> deportesPracticados;
    LinkedList<Deporte> deportesDeInteres;
    int Height; //Altura del nodo en el AVL
    Estudiante left;
    Estudiante right;
    
    
    Estudiante(int ID,String name){
        this.ID = ID;
        this.name = name;
        this.conexiones = new LinkedList<Referente>();
        this.deportesPracticados = new LinkedList<Deporte>();
        this.deportesDeInteres = new LinkedList<Deporte>();
        this.Height = 1; //Altura inicial de los nuevos nodos
        this.left = null;
        this.right = null;
    }
    
    @Override
    
    public int compareTo(Estudiante other){
        
        if (other == null) throw new RuntimeException("Empty comparison");
        if (ID > other.ID) return 1;
        else if (other.ID > ID) return -1;
        else return 0;
        
        //return Integer.compare(this.ID, other.ID);
    }
}