public interface MyLinkedList_Interface<T> {

    void add(T value);

    T get(int index);

    T remove(int index);

    void clear();

    int size();
}