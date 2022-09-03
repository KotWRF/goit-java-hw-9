import java.util.Arrays;
import java.util.HashMap;

public class MyHashMap<K, V> implements MyHashMapInterface<K, V> {
    private static final int DEFAULT_SIZE = 16;

    private Node<K, V>[] table;
    private int size = 0;

    public MyHashMap() {
        initializeEmptyTable();
    }

    private void initializeEmptyTable() {
        @SuppressWarnings({"unchecked"})
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[DEFAULT_SIZE];
        table = newTab;
    }

    @Override
    public void put(K key, V value) {
        int hash = hash(key);
        int index = getIndexFromHash(hash);

        Node<K, V> newNode = new Node<>(hash, key, value, null);

        if (table[index] == null) {
            table[index] = newNode;
            size++;
        } else {
            Node<K, V> existingNode = table[index];

            while (true) {
                if (existingNode.hash == hash) {
                    existingNode.value = value;
                    break;
                }

                if (existingNode.next == null) {
                    existingNode.next = newNode;
                    size++;
                    break;
                }

                existingNode = existingNode.next;
            }
        }

        resizeTableIfNeed();
    }

    private int hash(K key) {
        int h;

        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private int getIndexFromHash(int hash) {
        return (table.length - 1) & hash;
    }

    private void resizeTableIfNeed() {
        if (size == table.length << 1) {
            int newSize = table.length << 1;

            @SuppressWarnings({"unchecked"})
            Node<K, V>[] newTab = (Node<K, V>[]) new Node[newSize];
            Node<K, V>[] oldTable = table;
            table = newTab;
            size = 0;

            for (Node<K, V> node : oldTable) {
                if (node != null) {
                    do {
                        put(node.key, node.value);
                        node = node.next;
                    } while (node != null);
                }
            }
        }
    }

    @Override
    public void remove(K key) {
        int hash = hash(key);
        int index = getIndexFromHash(hash);

        Node<K, V> node = table[index];

        if (node != null) {
            Node<K, V> prevNode = null;

            do {
                if (node.hash == hash) {
                    if (prevNode == null) {
                        if (node.next != null) {
                            table[index] = node.next;
                            node.next = null;
                        } else {
                            table[index] = null;
                        }
                    } else {
                        prevNode.next = node.next;
                        node.next = null;
                    }
                    size--;

                    break;
                }

                prevNode = node;
                node = node.next;
            } while (node != null);
        }
    }

    @Override
    public void clear() {
        if (size > 1) {
            for (Node<K, V> node : table) {
                while (node != null) {
                    Node<K, V> nextNode = node.next;
                    node.next = null;
                    node = nextNode;
                }
            }
        }

        initializeEmptyTable();
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V get(K key) {
        int hash = hash(key);
        int index = getIndexFromHash(hash);

        Node<K, V> node = table[index];

        if (node != null) {
            do {
                if (node.hash == hash) {
                    return node.value;
                }

                node = node.next;
            } while (node != null);
        }

        return null;
    }

    private static class Node<K, V> {
        int hash;
        K key;
        V value;
        Node<K, V> next;

        public Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public String toString() {
        if (table == null) {
            return "{}";
        }
        for (int i = 0; i < size; i++) {
            if (table[i] != null) {
                Node<K,V> node = table[i];
                while (node != null) {
                    System.out.println("{" + node.key + " = " + node.value + "}" + " ");
                    node = node.next;
                }
            }
        }
        return "";
    }

}