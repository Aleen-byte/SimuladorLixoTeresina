package src.main.java.com.simulador.controller;

import src.main.java.com.simulador.model.*;
import src.main.java.com.simulador.view.components.MapaVisual;
import javafx.animation.AnimationTimer;

public class MapaController {
    private final Simulador simulador;
    private MapaVisual mapaVisual;
    private AnimationTimer animationTimer;

    public MapaController(Simulador simulador) {
        this.simulador = simulador;
    }

    public void setMapaVisual(MapaVisual mapaVisual) {
        this.mapaVisual = mapaVisual;
    }

    public void iniciarAnimacao() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                atualizarMapa();
            }
        };
        animationTimer.start();
    }

    public void pararAnimacao() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }

    private void atualizarMapa() {
        if (mapaVisual != null) {
            mapaVisual.atualizarPosicoes(
                    simulador.getCaminhoesPequenos(),
                    simulador.getCaminhoesGrandes(),
                    simulador.getZonas(),
                    simulador.getEstacoes()
            );
        }
    }
}
