public interface Deque<Type> {
    void addFirst(Type item);

    void addLast(Type item);

    boolean isEmpty();

    int size();

    void printDeque();

    Type removeFirst();

    Type removeLast();

    Type get(int index);
}
