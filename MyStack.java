import java.util.EmptyStackException;

public class MyStack<T>  implements MyStackInterface<T>{

    private int size = 0;
    private Node<T> head;
    private Node<T> last;

    private static class Node<T> {
        private final T value;
        Node<T> next;
        Node<T> prev;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void push(T value) { if (head == null) {
        head = new Node<>(value, null, null);
        last = head;
    } else {
        Node<T> newNode = new Node<>(value, last, null);
        last.next = newNode;
        last = newNode;
    }
        size++;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = findNodeByIndex(index);

        T nodeToRemoveValue = nodeToRemove.value;

        if (size == 1) {
            clear();

            return nodeToRemoveValue;
        }
        Node<T> prevNodeOfRemoved = nodeToRemove.prev;
        Node<T> nextNodeOfRemoved = nodeToRemove.next;

        if (prevNodeOfRemoved == null) {
            nextNodeOfRemoved.prev = null;
            head = nextNodeOfRemoved;
        } else if (nextNodeOfRemoved == null) {
            prevNodeOfRemoved.next = null;
            last = prevNodeOfRemoved;
        } else {
            prevNodeOfRemoved.next = nextNodeOfRemoved;
            nextNodeOfRemoved.prev = prevNodeOfRemoved;
        }
        size--;
        nodeToRemove.next = nodeToRemove.prev = null;

        return nodeToRemoveValue;
    }

    private Node<T> findNodeByIndex(int index) throws IndexOutOfBoundsException {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> result;

        if (index > size / 2) {
            result = last;

            for (int i = 0; i < size - index - 1; i++) {
                result = result.prev;
            }
        } else {
            result = head;

            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        }
        return result;
    }

    @Override
    public void clear() {
        Node<T> node = head;

        while (node != null) {
            Node<T> next = node.next;
            node.prev = node.next = null;

            node = next;
        }

        head = last = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T peek() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return last.value;
    }

    @Override
    public T pop() {
        T result = peek();
        remove(size - 1);
        return result;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }else {
            String result = "[" + head.value;
            Node current = head.next;
            while (current != null) {
                result += ", " + current.value;
                current = current.next;
            }
            result += "]";
            return result;
        }
    }
}