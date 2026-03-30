import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class FloodFillBase {
    private static final int COR_VERMELHA = 0xFFFF0000;
    private static final int MAX_FRAMES = 120;

    protected BufferedImage imagem;
    private final String pastaFrames;
    private final int intervaloFrame;
    private int indiceFrame;

    public FloodFillBase(String caminhoImagem, String pastaFrames) {
        this.pastaFrames = pastaFrames;
        this.imagem = carregarImagem(caminhoImagem);
        this.intervaloFrame = calcularIntervaloFrame();
        this.indiceFrame = 0;
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

        boolean[][] visitado = new boolean[altura][largura];
        limparEstrutura();
        adicionar(new Pixel(xInicial, yInicial));
        salvarFrame();

        int pixelsPreenchidos = 0;

        while (!estaVazia()) {
            Pixel atual = remover();
            if (atual == null) {
                continue;
            }

            int x = atual.getX();
            int y = atual.getY();

            if (!pixelExiste(x, y, largura, altura) || visitado[y][x]) {
                continue;
            }

            visitado[y][x] = true;

            if (imagem.getRGB(x, y) != corInicial) {
                continue;
            }

            imagem.setRGB(x, y, COR_VERMELHA);
            pixelsPreenchidos++;

            if (pixelsPreenchidos % intervaloFrame == 0) {
                salvarFrame();
            }

            adicionar(new Pixel(x, y - 1));
            adicionar(new Pixel(x + 1, y));
            adicionar(new Pixel(x, y + 1));
            adicionar(new Pixel(x - 1, y));
        }

        salvarFrame();
    }

    public void salvarResultado(String caminhoSaida) {
        try {
            ImageIO.write(imagem, "png", new File(caminhoSaida));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar resultado: " + caminhoSaida, e);
        }
    }

    protected abstract void adicionar(Pixel pixel);

    protected abstract Pixel remover();

    protected abstract boolean estaVazia();

    protected abstract void limparEstrutura();

    private BufferedImage carregarImagem(String caminhoImagem) {
        try {
            BufferedImage original = ImageIO.read(new File(caminhoImagem));

            if (original == null) {
                throw new RuntimeException("Nao foi possivel carregar a imagem: " + caminhoImagem);
            }

            BufferedImage copia = new BufferedImage(
                    original.getWidth(),
                    original.getHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );

            Graphics2D g2d = copia.createGraphics();
            g2d.drawImage(original, 0, 0, null);
            g2d.dispose();

            return copia;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar imagem: " + caminhoImagem, e);
        }
    }

    private int calcularIntervaloFrame() {
        int totalPixels = imagem.getWidth() * imagem.getHeight();
        return Math.max(1, totalPixels / MAX_FRAMES);
    }

    private void salvarFrame() {
        File pasta = new File(pastaFrames);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        String caminho = String.format("%s/frame_%05d.png", pastaFrames, indiceFrame++);

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
