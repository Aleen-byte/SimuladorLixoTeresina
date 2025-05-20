package src.main.java.com.simulador.view.components;

import src.main.java.com.simulador.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class MapaVisual extends Pane {
    private static final int TAMANHO_ZONA = 80;
    private static final int TAMANHO_ESTACAO = 60;
    private static final int TAMANHO_CAMINHAO = 15;

    private final Canvas canvas;
    private final Color[] coresZonas = {
            Color.rgb(255, 100, 100),  // Sul - Vermelho
            Color.rgb(100, 100, 255),  // Norte - Azul
            Color.rgb(100, 255, 100),  // Centro - Verde
            Color.rgb(255, 255, 100), // Leste - Amarelo
            Color.rgb(200, 100, 255)   // Sudeste - Roxo
    };

    public MapaVisual() {
        this.canvas = new Canvas(800, 600);
        this.getChildren().add(canvas);
        desenharBase();
    }

    private void desenharBase() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Desenhar fundo
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void atualizarPosicoes(CaminhaoPequeno[] caminhoesP, CaminhaoGrande[] caminhoesG,
                                  Zona[] zonas, EstacaoTransferencia[] estacoes) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        desenharBase();

        // Desenhar zonas
        for (int i = 0; i < zonas.length; i++) {
            double x = 100 + (i % 3) * 200;
            double y = 100 + (i / 3) * 150;

            gc.setFill(coresZonas[i]);
            gc.fillRect(x, y, TAMANHO_ZONA, TAMANHO_ZONA);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, TAMANHO_ZONA, TAMANHO_ZONA);

            gc.setFill(Color.BLACK);
            gc.fillText(zonas[i].getNome(), x + 10, y + 20);
            gc.fillText(String.format("%.1f ton", zonas[i].getLixoGerado()), x + 10, y + 35);
        }

        // Desenhar estações
        for (int i = 0; i < estacoes.length; i++) {
            double x = 400 + (i * 200);
            double y = 400;

            gc.setFill(Color.rgb(150, 150, 150));
            gc.fillRect(x, y, TAMANHO_ESTACAO, TAMANHO_ESTACAO);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, TAMANHO_ESTACAO, TAMANHO_ESTACAO);

            gc.setFill(Color.BLACK);
            gc.fillText(estacoes[i].getNome(), x + 10, y + 20);
            gc.fillText(String.format("%.1f ton", estacoes[i].getLixoArmazenado()), x + 10, y + 35);
        }

        // Desenhar caminhões pequenos
        for (CaminhaoPequeno caminhao : caminhoesP) {
            if (caminhao.getZonaAtual() != null) {
                int indexZona = -1;
                for (int i = 0; i < zonas.length; i++) {
                    if (zonas[i].getNome().equals(caminhao.getZonaAtual().getNome())) {
                        indexZona = i;
                        break;
                    }
                }

                if (indexZona >= 0) {
                    double x = 100 + (indexZona % 3) * 200 + 20 + (caminhao.getId() % 3) * 15;
                    double y = 100 + (indexZona / 3) * 150 + 50;

                    gc.setFill(Color.rgb(0, 0, 200));
                    gc.fillRect(x, y, TAMANHO_CAMINHAO, TAMANHO_CAMINHAO);
                    gc.setFill(Color.WHITE);
                    gc.fillText(String.valueOf(caminhao.getId()), x + 3, y + 12);
                }
            }
        }
    }
}
