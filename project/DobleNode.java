public class DobleNode<T> {
    T value;
    DobleNode<T> next;
    DobleNode<T> prev;
 
    DobleNode(T value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }
}
