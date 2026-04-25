public class AVLtree {
    Estudiante root;
    
    public AVLtree() {
        this.root = null;
    }
    private int height(Estudiante node) {
        if (node == null) return 0;
        return node.Height;
    }
    private int getBalance(Estudiante node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }
    private Estudiante rightRotate(Estudiante y) {
        Estudiante x = y.left;
        Estudiante T2 = x.right;
        
        // Realizar rotación
        x.right = y;
        y.left = T2;
        
        // Actualizar alturas
        y.Height = 1 + Math.max(height(y.left), height(y.right));
        x.Height = 1 + Math.max(height(x.left), height(x.right));
        
        // Retornar nueva raíz
        return x;
    }
    private Estudiante leftRotate(Estudiante x) {
        Estudiante y = x.right;
        Estudiante T2 = y.left;
        
        // Realizar rotación
        y.left = x;
        x.right = T2;
        
        // Actualizar alturas
        x.Height = 1 + Math.max(height(x.left), height(x.right));
        y.Height = 1 + Math.max(height(y.left), height(y.right));
        
        // Retornar nueva raíz
        return y;
    }

    public Estudiante insert(Estudiante node, Estudiante newStudent) {
        // Implementar inserción AVL
        if (node == null) return newStudent;
        
        if (newStudent.compareTo(node) < 0) {
            node.left = insert(node.left, newStudent);
        } else if (newStudent.compareTo(node) > 0) {
            node.right = insert(node.right, newStudent);
        } else {
            // Duplicados no permitidos
            return node;
        }
        
        // Actualizar altura del nodo ancestro
        node.Height = 1 + Math.max(height(node.left), height(node.right));
        
        // Obtener el factor de balanceo
        int balance = getBalance(node);
        
        // Si el nodo se vuelve desequilibrado, entonces hay 4 casos
        
        // Caso Left Left
        if (balance > 1 && newStudent.compareTo(node.left) < 0) {
            return rightRotate(node);
        }
        
        // Caso Right Right
        if (balance < -1 && newStudent.compareTo(node.right) > 0) {
            return leftRotate(node);
        }
        
        // Caso Left Right
        if (balance > 1 && newStudent.compareTo(node.left) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        
        // Caso Right Left
        if (balance < -1 && newStudent.compareTo(node.right) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        
        // Retornar el nodo sin cambios
        return node;
    }
    public Estudiante search(Estudiante node, int ID) {
        if (node == null || node.ID == ID) return node;
        
        if (ID < node.ID) return search(node.left, ID);
        
        return search(node.right, ID);
    }    
    public Estudiante find(int ID) {
        return search(root, ID);
    }
    // Implementar métodos de inserción, eliminación, rotaciones, etc.
}