package bolonhaemilano;

/** Processa a colheita do ano e a distribuicao de alimentos. Nao guarda nenhuma Cidade. */
public class Agricultura {
    private static final int HECTARES_POR_SERVO = 5;
    private static final int HECTARES_POR_SEMENTE = 2;
    private static final int RENDIMENTO_POR_HECTARE = 2;
    private static final int SERVOS_POR_GRAO = 5;
    private static final int CRESCIMENTO_PERCENTUAL = 5;
    private static final int PERCENTUAL_MINIMO = 20;
    private static final int PERCENTUAL_MAXIMO = 80;

    /**
     * hectares = min(terras, servos x 5); sementes = hectares / 2; producao = hectares x 2.
     * Devolve os hectares cultivados (ou -1 se a cidade ja colheu neste ano).
     */
    public int processarColheita(Cidade cidade) {
        if (cidade.isColheitaFeita()) {
            return -1;
        }
        int hectares = Math.min(cidade.getTerras(), cidade.getServos() * HECTARES_POR_SERVO);
        int sementes = hectares / HECTARES_POR_SEMENTE;
        if (sementes > cidade.getGraos()) {
            sementes = cidade.getGraos();
            hectares = sementes * HECTARES_POR_SEMENTE;
        }
        cidade.removerGraos(sementes);
        cidade.adicionarGraos(hectares * RENDIMENTO_POR_HECTARE);
        cidade.marcarColheitaFeita();
        return hectares;
    }

    /** Distribui um percentual (20 a 80) do estoque: a populacao cresce ou passa fome. */
    public boolean distribuirAlimentos(Cidade cidade, int percentual) {
        if (percentual < PERCENTUAL_MINIMO || percentual > PERCENTUAL_MAXIMO) {
            return false;
        }
        int alimentoDistribuido = (cidade.getGraos() * percentual) / 100;
        cidade.removerGraos(alimentoDistribuido);

        int necessidade = cidade.getServos() / SERVOS_POR_GRAO;
        if (alimentoDistribuido >= necessidade) {
            int crescimento = cidade.getServos() * CRESCIMENTO_PERCENTUAL / 100;
            cidade.adicionarServos(crescimento);
        } else {
            int deficit = necessidade - alimentoDistribuido;
            cidade.removerServos(Math.min(deficit, cidade.getServos()));
        }
        return true;
    }
}
