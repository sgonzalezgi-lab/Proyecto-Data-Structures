public class LinkedList<T>{
    public class Node{
        T value;
        Node next;
        
        Node(T item){
            value=item;
            next=null;
        }
    }
    
    public Node head;
    
    public LinkedList(){
        head=null;
    }
    
    public void pushBack(T item){
        Node newNode = new Node(item);
        newNode.next=head;
        head=newNode;
    }
    
    
    
}