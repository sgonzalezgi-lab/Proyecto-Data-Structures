public class DoublyLinkedList<T> {
 
    DobleNode<T> head;
 
    DoublyLinkedList() {
        this.head = null;
    }
 
    public boolean isEmpty() {
        return head == null;
    }
 
    
    public DobleNode<T> pushFront(T val) {
        DobleNode<T> newNode = new DobleNode<>(val);
        if (head != null) {
            head.prev = newNode;
        }
        newNode.next = head;
        head = newNode;
        return newNode;
    }
 
    
    public DobleNode<T> find(T val) {
        DobleNode<T> temp = head;
        while (temp != null) {
            if (temp.value == val) { 
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
 
    public void remove(T val) {
        DobleNode<T> temp = find(val);
        if (temp != null) {
            removeByReference(temp);
        }
    }
 
    
    public void removeByReference(DobleNode<T> node) {
        if (node == null) return;
        if (node == head) {
            head = node.next;
            if (node.next != null) {
                node.next.prev = null;
            }
        } else {
            node.prev.next = node.next;
            if (node.next != null) {
                node.next.prev = node.prev;
            }
        }
        
        node.next = null;
        node.prev = null;
    }
 
    public void print() {
        DobleNode<T> temp = head;
        int count = 1;
        while (temp != null) {
            System.out.println("  " + count + ": " + temp.value);
            count++;
            temp = temp.next;
        }
    }
}
