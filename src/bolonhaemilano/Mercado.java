package bolonhaemilano;

/** Compra e vende graos e terras a precos fixos. Nao guarda nenhuma Cidade. */
public class Mercado {
    private static final int PRECO_GRAO = 2;
    private static final int PRECO_TERRA = 20;

    public int getPrecoGrao() {
        return PRECO_GRAO;
    }

    public int getPrecoTerra() {
        return PRECO_TERRA;
    }

    public boolean comprarGraos(Cidade cidade, int quantidade) {
        return comprar(cidade, quantidade, PRECO_GRAO, true);
    }

    public boolean venderGraos(Cidade cidade, int quantidade) {
        if (quantidade <= 0) {
            return false;
        }
        if (!cidade.removerGraos(quantidade)) {
            return false;
        }
        cidade.adicionarFlorins(quantidade * PRECO_GRAO);
        return true;
    }

    public boolean comprarTerras(Cidade cidade, int quantidade) {
        return comprar(cidade, quantidade, PRECO_TERRA, false);
    }

    public boolean venderTerras(Cidade cidade, int quantidade) {
        if (quantidade <= 0) {
            return false;
        }
        if (!cidade.removerTerras(quantidade)) {
            return false;
        }
        cidade.adicionarFlorins(quantidade * PRECO_TERRA);
        return true;
    }

    // Logica comum das duas compras. O custo e calculado em long para nao estourar o int.
    private boolean comprar(Cidade cidade, int quantidade, int preco, boolean graos) {
        if (quantidade <= 0) {
            return false;
        }
        long custo = (long) quantidade * preco;
        if (custo > cidade.getFlorins()) {
            return false;
        }
        cidade.removerFlorins((int) custo);
        if (graos) {
            cidade.adicionarGraos(quantidade);
        } else {
            cidade.adicionarTerras(quantidade);
        }
        return true;
    }
}
