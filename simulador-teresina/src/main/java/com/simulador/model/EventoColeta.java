package src.main.java.com.simulador.model;

public class EventoColeta extends Evento {
    private final Zona zona;
    private final CaminhaoPequeno caminhao;

    public EventoColeta(long tempoAgendado, Simulador simulador, Zona zona, CaminhaoPequeno caminhao) {
        super(tempoAgendado, simulador);
        this.zona = zona;
        this.caminhao = caminhao;
    }

    @Override
    public void executar() {
        if (caminhao.coletarLixo(zona)) {
            simulador.logEvento(String.format("Caminhão %d coletou %.2f ton na zona %s",
                    caminhao.getId(), caminhao.getCargaAtual(), zona.getNome()));

            if (caminhao.estaCheio()) {
                EstacaoTransferencia estacao = simulador.getEstacaoMaisProxima();
                caminhao.setDestino(estacao);
                simulador.agendarEvento(new EventoTransferencia(
                        tempoAgendado + simulador.getTempoViagem(),
                        simulador, caminhao, estacao));
            } else {
                simulador.agendarEvento(new EventoColeta(
                        tempoAgendado + 1, simulador, zona, caminhao));
            }
        } else if (caminhao.podeTrabalhar()) {
            Zona novaZona = simulador.getProximaZona(zona);
            simulador.agendarEvento(new EventoColeta(
                    tempoAgendado + 1, simulador, novaZona, caminhao));
        }
    }
}