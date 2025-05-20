package src.main.java.com.simulador.model;

public class EventoTransferenciaGrande extends Evento {
    private final EstacaoTransferencia estacao;

    public EventoTransferenciaGrande(long tempoAgendado, Simulador simulador,
                                     EstacaoTransferencia estacao) {
        super(tempoAgendado, simulador);
        this.estacao = estacao;
    }

    @Override
    public void executar() {
        // 1. Verifica se há lixo para transferir
        if (estacao.getLixoArmazenado() <= 0) {
            simulador.logEvento(String.format("Estação %s sem lixo para transferência",
                    estacao.getNome()));
            return;
        }

        // 2. Obtém o próximo caminhão grande disponível
        if (estacao.temCaminhoesGrandes().estaVazia()) {
            simulador.logEvento("[ALERTA] Nenhum caminhão grande disponível na " + estacao.getNome());
            return;
        }

        CaminhaoGrande caminhao = (CaminhaoGrande) estacao.temCaminhoesGrandes().primeiro();

        // 3. Verifica se o caminhão deve partir para o aterro
        if (caminhao.devePartir(simulador.getTempoAtual())) {
            // Calcula a carga total
            double cargaTotal = caminhao.getCargaAtual();

            // Agenda evento de partida para o aterro
            simulador.agendarEvento(new EventoPartidaAterro(
                    tempoAgendado + simulador.getTempoViagemAterro(),
                    simulador, caminhao, estacao, cargaTotal));

            simulador.logEvento(String.format(
                    "Caminhão grande %d partindo para aterro com %.2f ton",
                    caminhao.getId(), cargaTotal));
        }
        // 4. Se não deve partir, tenta transferir mais lixo
        else if (estacao.transferirLixo()) {
            simulador.logEvento(String.format(
                    "Transferido lixo para caminhão grande %d na estação %s",
                    caminhao.getId(), estacao.getNome()));

            // Agenda novo evento de transferência
            simulador.agendarEvento(new EventoTransferenciaGrande(
                    tempoAgendado + 1, simulador, estacao));
        }
    }
}
