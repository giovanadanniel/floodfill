public class Stack<T> {
    private Node<T> top;

    public Stack() {
        top = null;
    }

    public void push(T value) {
        Node<T> novo = new Node<>(value);
        novo.setNext(top);
        top = novo;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }

        T valor = top.getValue();
        top = top.getNext();
        return valor;
    }

    public boolean isEmpty() {
        return top == null;
    }
}