package src.main.java.com.simulador.util;

public class Configuracao {
    public static final int TEMPO_VIAGEM_PADRAO = 15;
    public static final int TEMPO_VIAGEM_ATERRO = 45;
    public static final int TEMPO_MAX_ESPERA = 60;
    public static final int NUM_CAMINHOES_PEQUENOS = 10;
    public static final int NUM_CAMINHOES_GRANDES = 2;

    public static final double[] GERACAO_LIXO_ZONAS = {5, 15, 4, 12, 8, 20, 6, 18, 5, 16};

    public static int getTempoViagem(boolean horarioPico) {
        return horarioPico ? TEMPO_VIAGEM_PADRAO * 2 : TEMPO_VIAGEM_PADRAO;
    }
}
