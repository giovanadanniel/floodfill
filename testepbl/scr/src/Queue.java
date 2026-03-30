public class Queue<T> {
    private Node<T> first;
    private Node<T> last;

    public Queue() {
        first = null;
        last = null;
    }

    public void enqueue(T value) {
        Node<T> novo = new Node<>(value);

        if (isEmpty()) {
            first = novo;
            last = novo;
        } else {
            last.setNext(novo);
            last = novo;
        }
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T valor = first.getValue();
        first = first.getNext();

        if (first == null) {
            last = null;
        }

        return valor;
    }

    public boolean isEmpty() {
        return first == null;
    }
}