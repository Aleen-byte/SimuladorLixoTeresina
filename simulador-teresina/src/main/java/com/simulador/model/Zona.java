package src.main.java.com.simulador.model;

public class Zona {
    private final String nome;
    private double lixoGerado;
    private final double minLixoDiario;
    private final double maxLixoDiario;

    public Zona(String nome, double minLixoDiario, double maxLixoDiario) {
        this.nome = nome;
        this.minLixoDiario = minLixoDiario;
        this.maxLixoDiario = maxLixoDiario;
        this.lixoGerado = 0;
    }

    public void gerarLixoDiario() {
        this.lixoGerado = minLixoDiario + Math.random() * (maxLixoDiario - minLixoDiario);
    }

    public void reduzirLixo(double quantidade) {
        if (quantidade <= lixoGerado) {
            lixoGerado -= quantidade;
        } else {
            lixoGerado = 0;
        }
    }

    // Getters
    public String getNome() { return nome; }
    public double getLixoGerado() { return lixoGerado; }
}
