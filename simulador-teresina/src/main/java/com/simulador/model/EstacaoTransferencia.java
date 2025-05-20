package src.main.java.com.simulador.model;

import src.main.java.com.simulador.structures.Fila;

public class EstacaoTransferencia {
    private final String nome;
    private double lixoArmazenado;
    private final Fila<CaminhaoGrande> caminhoesGrandes;
    private final Fila<CaminhaoPequeno> caminhoesPequenos;
    private int totalCaminhoesGrandesUsados;

    public EstacaoTransferencia(String nome) {
        this.nome = nome;
        this.lixoArmazenado = 0;
        this.caminhoesGrandes = new Fila<>();
        this.caminhoesPequenos = new Fila<>();
        this.totalCaminhoesGrandesUsados = 0;
    }

    public void receberLixo(double quantidade) {
        lixoArmazenado += quantidade;
    }

    public void adicionarCaminhaoGrande(CaminhaoGrande caminhao) {
        caminhao.setEstacaoAtual(this);
        caminhao.setTempoChegada(System.currentTimeMillis());
        caminhoesGrandes.enfileirar(caminhao);
        totalCaminhoesGrandesUsados++;
    }

    public void adicionarCaminhaoPequeno(CaminhaoPequeno caminhao) {
        caminhoesPequenos.enfileirar(caminhao);
    }

    public boolean transferirLixo() {
        if (caminhoesGrandes.estaVazia() || lixoArmazenado <= 0) {
            return false;
        }

        CaminhaoGrande caminhao = caminhoesGrandes.primeiro();
        double capacidadeDisponivel = caminhao.getCapacidade() - caminhao.getCargaAtual();
        double quantidade = Math.min(lixoArmazenado, capacidadeDisponivel);

        if (quantidade > 0 && caminhao.carregar(quantidade)) {
            lixoArmazenado -= quantidade;
            return true;
        }
        return false;
    }

    /**
     * Remove todos os caminhões grandes da estação
     * (Usado para resetar a simulação)
     */
    public void limparCaminhoesGrandes() {
        while (!caminhoesGrandes.estaVazia()) {
            caminhoesGrandes.desenfileirar();
        }
        totalCaminhoesGrandesUsados = 0;
    }

    // Getters
    public String getNome() { return nome; }
    public double getLixoArmazenado() { return lixoArmazenado; }
    public int getTotalCaminhoesGrandesUsados() { return totalCaminhoesGrandesUsados; }
    public boolean temCaminhoesGrandes() { return !caminhoesGrandes.estaVazia(); }


}
