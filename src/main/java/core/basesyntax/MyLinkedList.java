package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    private class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);

        if (index == size) {
            add(value);
            return;
        }

        Node<T> current = first;

        for (int step = 0; step < index; step++) {
            current = current.next;
        }

        Node<T> previous = current.prev;
        Node<T> newNode = new Node<>(previous, value, current);

        if (previous == null) {
            first = newNode;
        } else {
            previous.next = newNode;
        }

        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);

        Node<T> current = first;
        for (int step = 0; step < index; step++) {
            current = current.next;
        }

        return current.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);

        Node<T> current = first;
        for (int step = 0; step < index; step++) {
            current = current.next;
        }
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        Node<T> current = first;
        for (int step = 0; step < index; step++) {
            current = current.next;
        }

        T oldValue = current.item;

        if (current.next == null && current.prev == null) {
            first = null;
            last = null;
        } else if (current.prev == null) {
            current.next.prev = null;
            first = current.next;
        } else if (current.next == null) {
            current.prev.next = null;
            last = current.prev;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;

        while (current != null) {
            if ((current.item == null && object == null)
                    || (current.item != null && current.item.equals(object))) {
                if (current.next == null && current.prev == null) {
                    first = null;
                    last = null;
                } else if (current.prev == null) {
                    current.next.prev = null;
                    first = current.next;
                } else if (current.next == null) {
                    current.prev.next = null;
                    last = current.prev;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }

                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index out of bounds: " + index);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException(
                    "Index out of bounds: " + index);
        }
    }
}
