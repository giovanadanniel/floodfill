import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            String caminhoImagem = localizarImagem("fotopbl.png");
            int xInicial = 600;
            int yInicial = 700;

            boolean executarPilha = true;
            boolean executarFila = true;

            limparPasta("frames_pilha");
            limparPasta("frames_fila");

            if (executarPilha) {
                FloodFillPilha floodFillPilha = new FloodFillPilha(caminhoImagem);
                floodFillPilha.executar(xInicial, yInicial);
                floodFillPilha.salvarResultado("saida_pilha.png");
                System.out.println("Pilha executada com sucesso.");
            }

            if (executarFila) {
                FloodFillFila floodFillFila = new FloodFillFila(caminhoImagem);
                floodFillFila.executar(xInicial, yInicial);
                floodFillFila.salvarResultado("saida_fila.png");
                System.out.println("Fila executada com sucesso.");
            }

            AnimacaoFrames animacao = new AnimacaoFrames();

            if (executarPilha) {
                animacao.exibir("frames_pilha", "Animacao - Flood Fill com Pilha");
            }

            if (executarFila) {
                animacao.exibir("frames_fila", "Animacao - Flood Fill com Fila");
            }
        } catch (Exception e) {
            System.out.println("Erro na execucao: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String localizarImagem(String nomeImagem) {
        String[] caminhos = {
                nomeImagem,
                "..\\" + nomeImagem,
                "testepbl\\" + nomeImagem,
                "testepbl\\scr\\src\\" + nomeImagem
        };

        for (String caminho : caminhos) {
            if (new File(caminho).exists()) {
                return caminho;
            }
        }

        throw new RuntimeException("Imagem nao encontrada: " + nomeImagem);
    }

    private static void limparPasta(String caminho) {
        File pasta = new File(caminho);

        if (!pasta.exists()) {
            pasta.mkdirs();
            return;
        }

        File[] arquivos = pasta.listFiles();
        if (arquivos == null) {
            return;
        }

        for (File arquivo : arquivos) {
            if (arquivo.isFile()) {
                arquivo.delete();
            }
        }
    }
}
