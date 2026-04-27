public class SinglyLinkedList<T> {
 
    SingleNode<T> head;
 
    SinglyLinkedList() {
        this.head = null;
    }
 
    public boolean isEmpty() {
        return head == null;
    }
 
    public void pushFront(T val) {
        SingleNode<T> newNode = new SingleNode<>(val);
        newNode.next = head;
        head = newNode;
    }
 
    public SingleNode<T> find(T val) {
        SingleNode<T> temp = head;
        while (temp != null) {
            if (temp.value == val) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }
 
    public void remove(T val) {
        SingleNode<T> temp = head;
        SingleNode<T> prev = null;
        while (temp != null) {
            if (temp.value == val) {
                if (temp == head) {
                    head = head.next;
                } else {
                    prev.next = temp.next;
                }
                return;
            }
            prev = temp;
            temp = temp.next;
        }
    }
 
    public void print() {
        SingleNode<T> temp = head;
        int count = 1;
        while (temp != null) {
            System.out.println("  " + count + ": " + temp.value);
            count++;
            temp = temp.next;
        }
    }
}
