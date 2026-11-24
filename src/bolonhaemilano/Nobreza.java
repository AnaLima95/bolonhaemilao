package bolonhaemilano;

/** Calcula o prestigio e decide quando o governante sobe de titulo. */
public class Nobreza {
    private static final int PESO_CONSTRUCAO = 10;
    private static final int PESO_SOLDADO = 2;
    private static final int PESO_SERVO = 1;
    private static final int HECTARES_POR_PONTO = 100;
    private static final int[] PRESTIGIO_MINIMO = {0, 400, 550, 700, 850, 1000};

    /** construcoes x 10 + soldados x 2 + servos + terras / 100. Florins nao contam. */
    public int calcularPrestigio(Cidade cidade) {
        return cidade.getConstrucoes() * PESO_CONSTRUCAO
                + cidade.getSoldados() * PESO_SOLDADO
                + cidade.getServos() * PESO_SERVO
                + cidade.getTerras() / HECTARES_POR_PONTO;
    }

    /** Prestigio minimo do proximo titulo, ou -1 se o jogador ja e Rei/Rainha. */
    public int prestigioParaProximoTitulo(Jogador jogador) {
        if (jogador.ehMonarca()) {
            return -1;
        }
        return PRESTIGIO_MINIMO[jogador.getNivelTitulo() + 1];
    }

    /** Promove o governante se o prestigio alcancar o proximo titulo. No maximo 1 titulo por chamada. */
    public boolean verificarPromocao(Cidade cidade) {
        Jogador dono = cidade.getDono();
        int necessario = prestigioParaProximoTitulo(dono);
        if (necessario == -1) {
            return false;
        }
        if (calcularPrestigio(cidade) < necessario) {
            return false;
        }
        return dono.promover();
    }
}
