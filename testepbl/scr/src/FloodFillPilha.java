public class FloodFillPilha extends FloodFillBase {
    private Stack<Pixel> pilha;

    public FloodFillPilha(String caminhoImagem) {
        super(caminhoImagem, "frames_pilha");
        pilha = new Stack<>();
    }

    @Override
    protected void adicionar(Pixel pixel) {
        pilha.push(pixel);
    }

    @Override
    protected Pixel remover() {
        return pilha.pop();
    }

    @Override
    protected boolean estaVazia() {
        return pilha.isEmpty();
    }

    @Override
    protected void limparEstrutura() {
        pilha = new Stack<>();
    }
}
