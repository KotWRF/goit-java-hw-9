import java.util.Arrays;

public class MyArrayList <O> {
    private O[] Objects;
    private int size = 0;
    private int index = 0;

    public MyArrayList () {
        Objects = (O[]) new Object[5];
    }

    public void add (O Object) {
        if (size == Objects.length) {
            Objects = Arrays.copyOf(Objects, Objects.length + 5);
        }
        Objects[size++] = Object;
    }

    public O remove(int index) {
        O removedValue = (O) Objects[index];
        if (index < (Objects.length-1)) {
            System.arraycopy(Objects, index+1, Objects, index, Objects.length-1);
        }
        return removedValue;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            Objects[i] = null;
        }
        size = 0;
    }

    public O get(int index) {
        return (O) Objects[index];
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder answ = new StringBuilder();
        if (Objects == null) {
            return "empty";
        } else {
            for (int i = 0; i < size; i++) {
                answ.append(Objects[i].toString());
                if (i != size-1) {
                    answ.append(", ");
                }
            }
        }
        return "[" + answ.toString() + "]";
    }

}