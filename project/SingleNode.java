public class SingleNode<T> {
    T value;
    SingleNode<T> next;
 
    SingleNode(T value) {
        this.value = value;
        this.next = null;
    }
}
