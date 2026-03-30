import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FloodFillPilha {
    private BufferedImage imagem;
    private static final int COR_VERMELHA = 0xFFFF0000;
    private final int intervaloFrame = 1000;

    public FloodFillPilha(String caminhoImagem) {
        try {
            BufferedImage original = ImageIO.read(new File(caminhoImagem));

            if (original == null) {
                throw new RuntimeException("Não foi possível carregar a imagem: " + caminhoImagem);
            }

            imagem = new BufferedImage(
                    original.getWidth(),
                    original.getHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );

            Graphics2D g2d = imagem.createGraphics();
            g2d.drawImage(original, 0, 0, null);
            g2d.dispose();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar imagem: " + caminhoImagem, e);
        }
    }

    public void executar(int xInicial, int yInicial) {
        int largura = imagem.getWidth();
        int altura = imagem.getHeight();

        if (!pixelExiste(xInicial, yInicial, largura, altura)) {
            throw new IllegalArgumentException("Coordenada inicial fora da imagem.");
        }

        int corInicial = imagem.getRGB(xInicial, yInicial);

        if (corInicial == COR_VERMELHA) {
            return;
        }

        Stack<Pixel> pilha = new Stack<>();
        boolean[][] visitado = new boolean[altura][largura];

        pilha.push(new Pixel(xInicial, yInicial));

        int contador = 0;

        while (!pilha.isEmpty()) {
            Pixel atual = pilha.pop();
            if (atual == null) continue;

            int x = atual.getX();
            int y = atual.getY();

            if (!pixelExiste(x, y, largura, altura)) continue;
            if (visitado[y][x]) continue;

            visitado[y][x] = true;

            if (imagem.getRGB(x, y) != corInicial) continue;

            imagem.setRGB(x, y, COR_VERMELHA);

            contador++;
            if (contador % intervaloFrame == 0) {
                salvarFrame("frames_pilha/frame_" + contador + ".png");
            }

            pilha.push(new Pixel(x, y - 1));
            pilha.push(new Pixel(x + 1, y));
            pilha.push(new Pixel(x, y + 1));
            pilha.push(new Pixel(x - 1, y));
        }

        salvarFrame("frames_pilha/frame_final.png");
    }

    public void salvarResultado(String caminhoSaida) {
        try {
            ImageIO.write(imagem, "png", new File(caminhoSaida));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar resultado: " + caminhoSaida, e);
        }
    }

    private void salvarFrame(String caminho) {
        try {
            ImageIO.write(imagem, "png", new File(caminho));
        } catch (IOException e) {
            System.out.println("Erro ao salvar frame: " + caminho);
        }
    }

    private boolean pixelExiste(int x, int y, int largura, int altura) {
        return x >= 0 && x < largura && y >= 0 && y < altura;
    }
}