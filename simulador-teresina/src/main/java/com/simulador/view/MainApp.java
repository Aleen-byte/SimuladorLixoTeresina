package src.main.java.com.simulador.view;

import javafx.geometry.Insets;
import src.main.java.com.simulador.controller.SimuladorController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {
    private SimuladorController controller;

    @Override
    public void start(Stage primaryStage) {
        controller = new SimuladorController();

        primaryStage.setTitle("Simulador de Coleta de Lixo - Teresina");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Painel de controle
        VBox controlPanel = criarPainelControle();
        root.setLeft(controlPanel);

        // Área central (mapa e log)
        TabPane centro = new TabPane();

        Tab tabMapa = new Tab("Mapa", new Label("Mapa será implementado aqui"));
        Tab tabLog = new Tab("Log", criarAreaLog());

        centro.getTabs().addAll(tabMapa, tabLog);
        root.setCenter(centro);

        // Painel de estatísticas
        HBox statsPanel = criarPainelEstatisticas();
        root.setBottom(statsPanel);

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> controller.pararSimulacao());
        primaryStage.show();
    }

    private VBox criarPainelControle() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px;");

        Label lblTitle = new Label("Configurações da Simulação");
        lblTitle.setStyle("-fx-font-weight: bold;");

        // Botões de controle
        Button btnIniciar = new Button("Iniciar Simulação");
        btnIniciar.setOnAction(e -> controller.iniciarSimulacao());

        Button btnParar = new Button("Parar Simulação");
        btnParar.setOnAction(e -> controller.pararSimulacao());

        Button btnPausar = new Button("Pausar");
        btnPausar.setOnAction(e -> controller.pausarSimulacao());

        panel.getChildren().addAll(lblTitle, new Separator(), btnIniciar, btnParar, btnPausar);
        return panel;
    }

    private ScrollPane criarAreaLog() {
        TextArea logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setWrapText(true);
        controller.setLogArea(logArea);
        return new ScrollPane(logArea);
    }

    private HBox criarPainelEstatisticas() {
        HBox panel = new HBox(20);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px 0 0 0;");

        Label lblStatus = new Label("Status: Não iniciado");
        Label lblLixo = new Label("Lixo coletado: 0 ton");
        Label lblCaminhoes = new Label("Caminhões grandes: 0");

        controller.setLabelsEstatisticas(new Label[]{lblStatus, lblLixo, lblCaminhoes});

        panel.getChildren().addAll(lblStatus, lblLixo, lblCaminhoes);
        return panel;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
