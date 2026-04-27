public class Queue<T> {
 
    T[] values;
    int size;
    int front;
    int capacity;
 
    Queue() {
        capacity = 2;
        values = (T[]) new Object[capacity];
        front = 0;
        size = 0;
    }
 
    private void resize() {
        if (size >= capacity) {
            T[] newArray = (T[]) new Object[capacity * 2];
            for (int i = 0; i < size; i++) {
                newArray[i] = values[(front + i) % capacity];
            }
            capacity *= 2;
            values = newArray;
            front = 0;
        }
    }
 
    public void enqueue(T value) {
        resize();
        values[(front + size) % capacity] = value;
        size++;
    }
 
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        T val = values[front];
        front = (front + 1) % capacity;
        size--;
        return val;
    }
 
    public boolean isEmpty() {
        return size <= 0;
    }
}
