package ru.otus;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DIYArrayList<T> implements List<T> {
    private Object[] array;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public DIYArrayList() {
        array = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i] != null && array[i].equals(o)) {
                return true;
            }
            if (o == null && array[i] == null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        int cursor = 0;
        int last = -1;

        @Override
        public boolean hasNext() {
            return size > cursor;
        }

        @Override
        public T next() {
            last = cursor;
            return get(cursor++);
        }

        @Override
        public void remove() {
            if (last < 0)
                throw new IllegalStateException();

            DIYArrayList.this.remove(last);
            cursor = last;
            last = -1;
        }
    }

    private class ListItr extends Itr implements ListIterator<T> {
        public ListItr(int index) {
            cursor = index;
        }

        public ListItr() {

        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public T previous() {
            last = --cursor;
            return get(cursor);
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(T t) {
            if (last < 0) throw new IllegalStateException();
            DIYArrayList.this.set(last, t);
        }

        @Override
        public void add(T t) {
            if (last < 0) throw new IllegalStateException();
            DIYArrayList.this.add(last, t);
            last = -1;
            cursor++;
        }
    }

    @Override
    public Object[] toArray() {
        Object[] localArray = new Object[size];
        for (int i = 0; i < size; i++) {
            localArray[i] = array[i];
        }
        return localArray;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return (T1[]) toArray();
        }
        for (int i = 0; i < size; i++) {
            a[i] = (T1) array[i];
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        increaseMemory();
        array[size] = t;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o == null && array[i] == null) {
                remove(i);
                return true;
            }
            if (o != null && o.equals(array[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c.size() == 0) return false;
        for (T o : c) {
            add(o);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        rangeCheck(index);
        if (c.size() == 0) return false;
        for (T t : c) {
            add(index++, t);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c.size() == 0) return false;
        for (Object o : c) {
            for (int i = 0; i < size; i++) {
                if (o == array[i]) {
                    remove(i--);
                }
                if (o != null && o.equals(array[i])) {
                    remove(i--);
                }
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c.size() == 0) return false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(array[i])) {
                remove(i--);
            }
        }
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        rangeCheck(index);
        return (T) array[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        rangeCheck(index);
        T removedElement = (T) array[index];
        array[index] = element;
        return removedElement;
    }

    @Override
    public void add(int index, T element) {
        rangeCheck(index);
        increaseMemory();
        add(null);
        for (int i = size - 1; i > index; i--) {
            array[i] = array[size - 2];
        }
        array[index] = element;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        rangeCheck(index);
        T oldValue = (T) array[index];
        for (int i = index; i < size; i++) {
            array[i] = array[i + 1];
        }
        size--;
        return oldValue;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o == array[i]) {
                return i;
            }
            if (o != null && o.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size; i > 0; i--) {
            if (o == array[i]) {
                return i;
            }
            if (o != null && o.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListItr();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> subList(int fromIndex, int toIndex) {
        rangeCheck(fromIndex);
        rangeCheck(toIndex);
        List<T> subList = new DIYArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add((T) array[i]);
        }
        return subList;
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            str.append(array[i].toString());
            if (i != size - 1) {
                str.append(", ");
            }
        }
        str.append("]");
        return str.toString();
    }

    private void increaseMemory() {
        if (size == array.length) {
            Object[] newArray = new Object[size + DEFAULT_CAPACITY];
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }
    }

}
