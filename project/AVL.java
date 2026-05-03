public class AVL<T extends Comparable<T>> {
 
    class Node {
        T value;
        Node right, left, parent;
        int height;
 
        Node(T value) {
            this.value = value;
            right = null;
            left = null;
            parent = null;
            height = 1;
        }
    }
 
    Node root;
 
    AVL() {
        root = null;
    }
 
 
    private int heightOf(Node node) {
        if(node==null){
            return 0;
        }
        return node.height;
    }
 
    private void adjHeight(Node node) {
        if (node == null) {
            return;
        }
        node.height = 1 + Math.max(heightOf(node.left), heightOf(node.right));
    }
 
    private int balanceFactor(Node node) {
        if(node==null){
            return 0;
        }
        return heightOf(node.left) - heightOf(node.right);
    }
 
 
    private void rightRotation(Node node) {
        if(node==null || node.left==null){
            return;
        }
 
        Node Y = node.left;
        Node B = Y.right;
 
        
        if (node.parent == null) {
            root = Y;
            Y.parent = null;
        } else {
            if(node.parent.left==node){
                node.parent.left=Y;
            }else{
                node.parent.right = Y;
            }
            Y.parent = node.parent;
        }
 
        Y.right = node;
        node.parent = Y;
        node.left = B;
        if (B != null){
            B.parent = node;
        }
 
        adjHeight(node);
        adjHeight(Y);
    }
 
    private void leftRotation(Node node) {
        if (node == null || node.right == null){
            return;
        }
 
        Node X = node.right;
        Node B = X.left;
 
        if (node.parent == null) {
            root = X;
            X.parent = null;
        } else {
            if (node.parent.left == node){
                node.parent.left = X;
                
            }else{
                node.parent.right = X;
            }
            X.parent = node.parent;
        }
 
        X.left = node;
        node.parent = X;
        node.right = B;
        if (B != null){
            B.parent = node;
        }
 
        adjHeight(node);
        adjHeight(X);
    }
 
    private void rebalance(Node node) {
        if (node == null){
            return;
        }
        adjHeight(node);
        int bd = balanceFactor(node);
 
        if (bd > 1) {
            if (balanceFactor(node.left) < 0) {
                leftRotation(node.left);
            }
            rightRotation(node);
        } else if (bd < -1) {
            
            if (balanceFactor(node.right) > 0) {
                rightRotation(node.right); 
            }
            leftRotation(node);
        }
    }
    
    private Node insertRec(T val,Node node){
        if(node==null){
            return new Node(val);
        }else if(node.value.compareTo(val)<0){
            node.right=insertRec(val,node.right);
            node.right.parent=node;
        }else if(node.value.compareTo(val)>0){
            node.left=insertRec(val,node.left);
            node.left.parent=node;
        }
        
        adjHeight(node);
        int BD= balanceFactor(node);
        rebalance(node);
        if(Math.abs(BD)>1){
            return node.parent;
        }
        return node;
    }
 
    public void insert(T val) {
        root = insertRec(val, root);
    }
 
 
    private Node findNode(T val, Node node) {
        if (node == null){
            return null;
        }
        int cmp = node.value.compareTo(val);
        if (cmp == 0){
            return node;
        }
        if (cmp > 0) {
            return findNode(val, node.left);
        }
        return findNode(val, node.right);
    }
 
    
    public T find(T val) {
        Node node = findNode(val, root);
        if(node!=null){
            return node.value;
        }else{
            return null;
        }
    }
 
    public boolean isTheElement(T val) {
        return findNode(val, root) != null;
    }
 
    
 
    private Node findMin(Node node) {
        while (node.left != null){
            node = node.left;
        }
        return node;
    }
 
    
    private Node deleteRec(T val, Node node) {
        if (node == null){
            return null;
        }
 
        int cmp = node.value.compareTo(val);
        if (cmp > 0) {
            node.left = deleteRec(val, node.left);
            if (node.left != null){
                node.left.parent = node;
            }
        } else if (cmp < 0) {
            node.right = deleteRec(val, node.right);
            if (node.right != null){
                node.right.parent = node;
            }
        } else {
            
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                node.right.parent = node.parent;
                return node.right;
            } else if (node.right == null) {
                node.left.parent = node.parent;
                return node.left;
            } else {
                
                Node next = findMin(node.right);
                node.value = next.value;
                node.right = deleteRec(next.value, node.right);
                if (node.right != null){
                    node.right.parent = node;
                }
            }
        }
 
        adjHeight(node);
        int BD=Math.abs(balanceFactor(node));
        rebalance(node);
        if(BD>1){
            return node.parent;
        }
        
        return node;
    }
 
    public void delete(T val) {
        root = deleteRec(val, root);
        if(root!=null){
            root.parent=null;
        }
    }
 
 
    private void inOrder(Node node) {
        if (node == null) return;
        inOrder(node.left);
        System.out.println("  " + node.value);
        inOrder(node.right);
    }
 
    public void print() {
        inOrder(root);
    }
 
 
    public void inOrderToArray(Node node, DinamicArray<T> arr) {
        if (node == null) return;
        inOrderToArray(node.left, arr);
        arr.insert(node.value);
        inOrderToArray(node.right, arr);
    }
}
