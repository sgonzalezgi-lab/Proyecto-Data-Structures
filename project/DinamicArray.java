public class DinamicArray<T> {
 
    T[] values;
    private int size;
    int capacity;
 
    DinamicArray() {
        capacity = 2;
        values = (T[]) new Object[capacity];
        size = 0;
    }
 
    private void resize() {
        if (size >= capacity) {
            capacity *= 2;
            T[] newArray = (T[]) new Object[capacity];
            for (int i = 0; i < size; i++) {
                newArray[i] = values[i];
            }
            values = newArray;
        }
    }
 
    public void insert(T value) {
        resize();
        values[size++] = value;
    }
 
    public void delete(T value) {
        for (int i = 0; i < size; i++) {
            if (values[i] == value) {           
                for (int j = i; j < size - 1; j++) {
                    values[j] = values[j + 1];
                }
                size--;
                return;
            }
        }
    }
 
    public boolean isEmpty() {
        return size <= 0;
    }
 
    public T access(int i) {
        if (i >= 0 && i < size) {
            return values[i];
        }
        return null;
    }
 
    public int getSize() {
        return size;
    }
 
    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.println("  " + i + ") " + values[i]);
        }
    }
}
