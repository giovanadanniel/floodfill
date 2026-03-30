public class FloodFillFila extends FloodFillBase {
    private Queue<Pixel> fila;

    public FloodFillFila(String caminhoImagem) {
        super(caminhoImagem, "frames_fila");
        fila = new Queue<>();
    }

    @Override
    protected void adicionar(Pixel pixel) {
        fila.enqueue(pixel);
    }

    @Override
    protected Pixel remover() {
        return fila.dequeue();
    }

    @Override
    protected boolean estaVazia() {
        return fila.isEmpty();
    }

    @Override
    protected void limparEstrutura() {
        fila = new Queue<>();
    }
}
