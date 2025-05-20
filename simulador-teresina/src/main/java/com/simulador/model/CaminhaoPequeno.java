package src.main.java.com.simulador.model;

public class CaminhaoPequeno {
    private final int id;
    private final double capacidade;
    private double cargaAtual;
    private Zona zonaAtual;
    private EstacaoTransferencia destino;
    private int viagensRealizadas;
    private final int maxViagens;
    private boolean emTransito;

    public CaminhaoPequeno(int id, double capacidade, int maxViagens) {
        this.id = id;
        this.capacidade = capacidade;
        this.maxViagens = maxViagens;
        this.cargaAtual = 0;
        this.viagensRealizadas = 0;
        this.emTransito = false;
    }

    public boolean coletarLixo(Zona zona) {
        if (emTransito || cargaAtual >= capacidade || viagensRealizadas >= maxViagens) {
            return false;
        }

        double lixoDisponivel = zona.getLixoGerado();
        double capacidadeRestante = capacidade - cargaAtual;
        double lixoColetado = Math.min(lixoDisponivel, capacidadeRestante);

        if (lixoColetado > 0) {
            zona.reduzirLixo(lixoColetado);
            cargaAtual += lixoColetado;
            zonaAtual = zona;
            return true;
        }
        return false;
    }

    public void descarregar(EstacaoTransferencia estacao) {
        if (destino != null && !emTransito) {
            destino.receberLixo(cargaAtual);
            cargaAtual = 0;
            viagensRealizadas++;
            emTransito = true;
        }
    }

    // Getters e Setters
    public double getCargaAtual() { return cargaAtual; }
    public int getId() { return id; }
    public Zona getZonaAtual() { return zonaAtual; }
    public EstacaoTransferencia getDestino() { return destino; }
    public boolean podeTrabalhar() { return viagensRealizadas < maxViagens; }
    public boolean estaEmTransito() { return emTransito; }
    public void setDestino(EstacaoTransferencia destino) { this.destino = destino; }
    public void setEmTransito(boolean emTransito) { this.emTransito = emTransito; }

    public boolean estaCheio() {
        if (cargaAtual >= capacidade) {
            return true;
        } else {
            return false;
        }
    }
}
