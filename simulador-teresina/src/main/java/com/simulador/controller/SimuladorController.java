package src.main.java.com.simulador.controller;

import src.main.java.com.simulador.model.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.*;

public class SimuladorController {
    private final Simulador simulador;
    private AnimationTimer timerAtualizacao;
    private TextArea logArea;
    private Label[] labelsEstatisticas;

    public SimuladorController() {
        this.simulador = new Simulador();
    }

    public void iniciarSimulacao() {
        if (simulador.isExecutando()) return;

        simulador.iniciarSimulacao();
        iniciarAtualizacaoVisual();
    }

    public void pararSimulacao() {
        simulador.pararSimulacao();
        pararAtualizacaoVisual();
    }

    public void pausarSimulacao() {
        simulador.pausarSimulacao();
    }

    private void iniciarAtualizacaoVisual() {
        timerAtualizacao = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Platform.runLater(() -> {
                    atualizarLog();
                    atualizarEstatisticas();
                });
            }
        };
        timerAtualizacao.start();
    }

    private void pararAtualizacaoVisual() {
        if (timerAtualizacao != null) {
            timerAtualizacao.stop();
        }
    }

    private void atualizarLog() {
        if (logArea != null) {
            logArea.setText(simulador.getLog());
            logArea.setScrollTop(Double.MAX_VALUE);
        }
    }

    private void atualizarEstatisticas() {
        if (labelsEstatisticas != null && labelsEstatisticas.length >= 4) {
            labelsEstatisticas[0].setText("Status: " + (simulador.isExecutando() ? "Em execução" : "Parado"));

            double totalLixo = 0;
            for (Zona zona : simulador.getZonas()) {
                totalLixo += zona.getLixoGerado();
            }
            labelsEstatisticas[1].setText(String.format("Lixo coletado: %.2f ton", totalLixo));

            int totalCaminhoesGrandes = 0;
            for (EstacaoTransferencia estacao : simulador.getEstacoes()) {
                totalCaminhoesGrandes += estacao.getTotalCaminhoesGrandesUsados();
            }
            labelsEstatisticas[2].setText(String.format("Caminhões grandes: %d", totalCaminhoesGrandes));
        }
    }

    // Getters e Setters para UI components
    public void setLogArea(TextArea logArea) { this.logArea = logArea; }
    public void setLabelsEstatisticas(Label[] labels) { this.labelsEstatisticas = labels; }
    public Simulador getSimulador() { return simulador; }
}
