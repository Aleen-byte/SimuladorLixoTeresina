package src.main.java.com.simulador.model;

import src.main.java.com.simulador.structures.Fila;
import src.main.java.com.simulador.util.Configuracao;

import java.util.Random;

public class Simulador {
    private Fila<Evento> eventos;
    private Zona[] zonas;
    private CaminhaoPequeno[] caminhoesPequenos;
    private CaminhaoGrande[] caminhoesGrandes;
    private EstacaoTransferencia[] estacoes;
    private long tempoAtual;
    private boolean executando;
    private boolean pausado;
    private int tempoViagem;
    private int tempoViagemAterro;
    private int tempoMaxEspera;
    private StringBuilder log;

    public Simulador() {
        resetar();
    }

    public void resetar() {
        this.eventos = new Fila<>();
        this.zonas = new Zona[5];
        this.caminhoesPequenos = new CaminhaoPequeno[10];
        this.estacoes = new EstacaoTransferencia[2];
        this.log = new StringBuilder();
        this.executando = false;
        this.pausado = false;

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Inicialização das zonas
        zonas[0] = new Zona("Sul", 5, 15);
        zonas[1] = new Zona("Norte", 4, 12);
        zonas[2] = new Zona("Centro", 8, 20);
        zonas[3] = new Zona("Leste", 6, 18);
        zonas[4] = new Zona("Sudeste", 5, 16);

        // Inicialização dos caminhões pequenos
        Random rand = new Random();
        for (int i = 0; i < caminhoesPequenos.length; i++) {
            double capacidade = switch (rand.nextInt(4)) {
                case 0 -> 2; case 1 -> 4; case 2 -> 8; default -> 10;
            };
            caminhoesPequenos[i] = new CaminhaoPequeno(i+1, capacidade, 5);
        }

        // Inicialização das estações
        estacoes[0] = new EstacaoTransferencia("Estação Norte");
        estacoes[1] = new EstacaoTransferencia("Estação Sul");

        for (int i = 0; i < 2; i++) {
            CaminhaoGrande caminhao = new CaminhaoGrande(i+1, tempoMaxEspera);
            estacoes[i % 2].adicionarCaminhaoGrande(caminhao);
        }

        // Limpa caminhões existentes (se houver)
        for (EstacaoTransferencia estacao : estacoes) {
                estacao.limparCaminhoesGrandes();
        }

        // Distribui os caminhões grandes entre as estações
        for (int i = 0; i < Configuracao.NUM_CAMINHOES_GRANDES; i++) {
            CaminhaoGrande caminhao = new CaminhaoGrande(
                    i + 1,                      // ID único
                    Configuracao.TEMPO_MAX_ESPERA // Tempo máximo de espera configurável
            );

            // Distribui alternadamente entre as estações
            EstacaoTransferencia estacao = estacoes[i % estacoes.length];
            estacao.adicionarCaminhaoGrande(caminhao);

            logEvento(String.format("Caminhão grande %d alocado na %s",
                    caminhao.getId(), estacao.getNome()));
        }

    }

    public void iniciarSimulacao() {
        if (executando) return;

        resetar();
        executando = true;
        pausado = false;
        tempoAtual = 0;
        logEvento("Simulação iniciada");

        for (Zona zona : zonas) {
            zona.gerarLixoDiario();
        }

        for (CaminhaoPequeno caminhao : caminhoesPequenos) {
            Zona zonaInicial = getZonaAleatoria();
            agendarEvento(new EventoColeta(1, this, zonaInicial, caminhao));
        }
    }

    public void processarEventos() {
        while (executando && !eventos.estaVazia() && !pausado) {
            Evento evento = eventos.desenfileirar();
            tempoAtual = evento.getTempoAgendado();
            evento.executar();
        }

        if (eventos.estaVazia() && executando) {
            logEvento("Simulação concluída");
            executando = false;
        }
    }

    // ... (outros métodos mantidos conforme versão anterior)


    public void pararSimulacao() {
        executando = false;
        logEvento("Simulação parada");
    }

    public void agendarEvento(Evento evento) {
        eventos.enfileirar(evento);
    }

    public void logEvento(String mensagem) {
        String logEntry = String.format("[Tempo %d] %s\n", tempoAtual, mensagem);
        log.append(logEntry);
        System.out.print(logEntry); // Também imprime no console para depuração
    }

    public Zona getZonaAleatoria() {
        Random rand = new Random();
        return zonas[rand.nextInt(zonas.length)];
    }

    public Zona getProximaZona(Zona zonaAtual) {
        for (int i = 0; i < zonas.length; i++) {
            if (zonas[i] == zonaAtual) {
                return zonas[(i + 1) % zonas.length];
            }
        }
        return getZonaAleatoria();
    }

    public EstacaoTransferencia getEstacaoMaisProxima() {
        // Simplesmente alterna entre as estações para distribuir o tráfego
        Random rand = new Random();
        return estacoes[rand.nextInt(estacoes.length)];
    }

    // Getters e Setters
    public Zona[] getZonas() { return zonas; }
    public CaminhaoPequeno[] getCaminhoesPequenos() { return caminhoesPequenos; }
    public EstacaoTransferencia[] getEstacoes() { return estacoes; }
    public long getTempoAtual() { return tempoAtual; }
    public boolean isExecutando() { return executando; }
    public String getLog() { return log.toString(); }
    public int getTempoViagem() { return tempoViagem; }
    public void setTempoViagem(int tempoViagem) { this.tempoViagem = tempoViagem; }
    public int getTempoViagemAterro() { return tempoViagemAterro; }
    public void setTempoViagemAterro(int tempoViagemAterro) { this.tempoViagemAterro = tempoViagemAterro; }
    public int getTempoMaxEspera() { return tempoMaxEspera; }
    public void setTempoMaxEspera(int tempoMaxEspera) { this.tempoMaxEspera = tempoMaxEspera; }


    public CaminhaoGrande[] getCaminhoesGrandes() {
        return caminhoesGrandes;
    }

    public void pausarSimulacao() {

    }
}

