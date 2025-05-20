package src.main.java.com.simulador.model;

public class EventoPartidaAterro extends Evento {
    private final CaminhaoGrande caminhao;
    private final EstacaoTransferencia estacao;
    private final double cargaTotal;

    public EventoPartidaAterro(long tempoAgendado, Simulador simulador,
                               CaminhaoGrande caminhao, EstacaoTransferencia estacao,
                               double cargaTotal) {
        super(tempoAgendado, simulador);
        this.caminhao = caminhao;
        this.estacao = estacao;
        this.cargaTotal = cargaTotal;
    }

    @Override
    public void executar() {
        // 1. Registra a descarga no aterro
        simulador.logEvento(String.format(
                "Caminhão grande %d descarregou %.2f ton no aterro sanitário",
                caminhao.getId(), cargaTotal));

        // 2. Retorna o caminhão para a estação
        caminhao.retornarDaViagem();
        estacao.adicionarCaminhaoGrande(caminhao);

        simulador.logEvento(String.format(
                "Caminhão grande %d retornou para %s",
                caminhao.getId(), estacao.getNome()));

        // 3. Agenda nova verificação de transferência
        simulador.agendarEvento(new EventoTransferenciaGrande(
                tempoAgendado + 1, simulador, estacao));
    }
}
