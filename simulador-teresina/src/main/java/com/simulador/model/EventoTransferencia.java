package src.main.java.com.simulador.model;

public class EventoTransferencia extends Evento {
    private final CaminhaoPequeno caminhao;
    private final EstacaoTransferencia estacao;

    public EventoTransferencia(long tempoAgendado, Simulador simulador,
                               CaminhaoPequeno caminhao, EstacaoTransferencia estacao) {
        super(tempoAgendado, simulador);
        this.caminhao = caminhao;
        this.estacao = estacao;
    }

    @Override
    public void executar() {
        double lixoDescarregado = caminhao.getCargaAtual();
        caminhao.descarregar(estacao);

        simulador.logEvento(String.format("Caminhão %d descarregou %.2f ton na estação %s",
                caminhao.getId(), lixoDescarregado, estacao.getNome()));

        if (caminhao.podeTrabalhar()) {
            Zona zona = simulador.getZonaAleatoria();
            simulador.agendarEvento(new EventoColeta(
                    tempoAgendado + 1, simulador, zona, caminhao));
        }

        simulador.agendarEvento(new EventoTransferenciaGrande(
                tempoAgendado + 1, simulador, estacao));
    }
}
