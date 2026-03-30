public class Lista<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public Lista() {
        first = null;
        last = null;
        size = 0;
    }

    public void addFirst(T value) {
        Node<T> novo = new Node<>(value);

        if (isEmpty()) {
            first = novo;
            last = novo;
        } else {
            novo.setNext(first);
            first = novo;
        }

        size++;
    }

    public void addLast(T value) {
        Node<T> novo = new Node<>(value);

        if (isEmpty()) {
            first = novo;
            last = novo;
        } else {
            last.setNext(novo);
            last = novo;
        }

        size++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T valor = first.getValue();
        first = first.getNext();
        size--;

        if (first == null) {
            last = null;
        }

        return valor;
    }

    public T getFirst() {
        if (isEmpty()) {
            return null;
        }
        return first.getValue();
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node<T> atual = first;
        int i = 0;

        while (i < index) {
            atual = atual.getNext();
            i++;
        }

        return atual.getValue();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    public Object[] toArray() {
        Object[] array = new Object[size];
        Node<T> atual = first;
        int i = 0;

        while (atual != null) {
            array[i] = atual.getValue();
            atual = atual.getNext();
            i++;
        }

        return array;
    }

    @Override
    public String toString() {
        String texto = "[";
        Node<T> atual = first;

        while (atual != null) {
            texto += atual.getValue();

            if (atual.getNext() != null) {
                texto += ", ";
            }

            atual = atual.getNext();
        }

        texto += "]";
        return texto;
    }
}