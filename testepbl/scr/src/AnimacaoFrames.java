import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

public class AnimacaoFrames {
    public void exibir(String pastaFrames, String titulo) throws Exception {
        File pasta = new File(pastaFrames);
        File[] arquivos = pasta.listFiles((dir, nome) -> nome.toLowerCase().endsWith(".png"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum frame encontrado em " + pastaFrames);
            return;
        }

        ordenarArquivosPorNome(arquivos);

        Lista<File> listaFrames = new Lista<>();
        for (File arquivo : arquivos) {
            listaFrames.addLast(arquivo);
        }

        BufferedImage primeiraImagem = ImageIO.read(listaFrames.get(0));
        if (primeiraImagem == null) {
            throw new IllegalStateException("Nao foi possivel ler o primeiro frame.");
        }

        PainelAnimacao painel = new PainelAnimacao();
        painel.setPreferredSize(calcularTamanhoJanela(primeiraImagem));

        JFrame janela = new JFrame(titulo);
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.add(painel);
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);

        for (int i = 0; i < listaFrames.size(); i++) {
            BufferedImage frame = ImageIO.read(listaFrames.get(i));
            if (frame != null) {
                painel.setImagem(frame);
                Thread.sleep(90);
            }
        }
    }

    private void ordenarArquivosPorNome(File[] arquivos) {
        for (int i = 0; i < arquivos.length - 1; i++) {
            for (int j = 0; j < arquivos.length - 1 - i; j++) {
                if (arquivos[j].getName().compareTo(arquivos[j + 1].getName()) > 0) {
                    File temp = arquivos[j];
                    arquivos[j] = arquivos[j + 1];
                    arquivos[j + 1] = temp;
                }
            }
        }
    }

    private Dimension calcularTamanhoJanela(BufferedImage imagem) {
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int larguraMaxima = (int) (tela.width * 0.7);
        int alturaMaxima = (int) (tela.height * 0.7);

        double escala = Math.min(
                (double) larguraMaxima / imagem.getWidth(),
                (double) alturaMaxima / imagem.getHeight()
        );

        escala = Math.min(1.0, escala);

        int largura = (int) Math.round(imagem.getWidth() * escala);
        int altura = (int) Math.round(imagem.getHeight() * escala);
        return new Dimension(largura, altura);
    }

    private static class PainelAnimacao extends JPanel {
        private BufferedImage imagem;

        public void setImagem(BufferedImage imagem) {
            this.imagem = imagem;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (imagem != null) {
                g.drawImage(imagem, 0, 0, getWidth(), getHeight(), null);
            }
        }
    }
}
