package src.main.java.com.simulador.model;

public abstract class Evento {
    protected final long tempoAgendado;
    protected final Simulador simulador;

    public Evento(long tempoAgendado, Simulador simulador) {
        this.tempoAgendado = tempoAgendado;
        this.simulador = simulador;
    }

    public abstract void executar();

    public long getTempoAgendado() {
        return tempoAgendado;
    }
}
