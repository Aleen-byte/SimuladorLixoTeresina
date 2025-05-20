package src.main.java.com.simulador.model;

public class CaminhaoGrande {
    private final int id;
    private final double capacidade;
    private double cargaAtual;
    private EstacaoTransferencia estacaoAtual;
    private long tempoChegada;
    private final long tempoMaxEspera;
    private boolean emTransito;

    public CaminhaoGrande(int id, long tempoMaxEspera) {
        this.id = id;
        this.capacidade = 20; // 20 toneladas
        this.tempoMaxEspera = tempoMaxEspera;
        this.cargaAtual = 0;
        this.emTransito = false;
    }

    public boolean carregar(double quantidade) {
        if (cargaAtual + quantidade > capacidade) {
            return false;
        }
        cargaAtual += quantidade;
        return true;
    }

    public boolean devePartir(long tempoAtual) {
        return (cargaAtual >= capacidade) ||
                (!emTransito && (tempoAtual - tempoChegada >= tempoMaxEspera));
    }

    public void partirParaAterro() {
        emTransito = true;
    }

    public void retornarDaViagem() {
        cargaAtual = 0;
        emTransito = false;
        tempoChegada = System.currentTimeMillis();
    }

    // Getters e Setters
    public double getCargaAtual() { return cargaAtual; }
    public int getId() { return id; }
    public boolean estaEmTransito() { return emTransito; }
    public void setEstacaoAtual(EstacaoTransferencia estacao) { this.estacaoAtual = estacao; }
    public void setTempoChegada(long tempo) { this.tempoChegada = tempo; }

    public double getCapacidade() {
        return capacidade;
    }
}