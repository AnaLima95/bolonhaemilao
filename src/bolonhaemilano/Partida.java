package bolonhaemilano;

import java.util.ArrayList;
import java.util.List;

/** Uma partida de 1 a 4 jogadores: turnos, anos, fim de jogo e vencedor. */
public class Partida {
    public static final int MAXIMO_JOGADORES = 4;
    private static final int ANO_FINAL = 1410;

    private List<Cidade> cidades = new ArrayList<>();
    private int indiceAtual = 0;

    /** Recusa a cidade (false) se a partida ja tem 4 jogadores. */
    public boolean adicionarCidade(Cidade cidade) {
        if (cidades.size() >= MAXIMO_JOGADORES) {
            return false;
        }
        cidades.add(cidade);
        return true;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public Cidade getCidadeAtual() {
        return cidades.get(indiceAtual);
    }

    public int getAnoAtual() {
        return cidades.isEmpty() ? 1400 : cidades.get(0).getAnoAtual();
    }

    public int getAnoFinal() {
        return ANO_FINAL;
    }

    /** Passa a vez. Devolve true quando o ultimo jogador encerrou (o ano acabou). */
    public boolean passarTurno() {
        indiceAtual++;
        if (indiceAtual >= cidades.size()) {
            indiceAtual = 0;
            return true;
        }
        return false;
    }

    /** Avanca o ano de todas as cidades e devolve as que foram promovidas. */
    public List<Cidade> encerrarAno(Nobreza nobreza) {
        List<Cidade> promovidas = new ArrayList<>();
        for (Cidade cidade : cidades) {
            cidade.avancarAno();
            if (nobreza.verificarPromocao(cidade)) {
                promovidas.add(cidade);
            }
        }
        return promovidas;
    }

    /** Acaba quando alguem vira Rei/Rainha ou quando o ano passa de 1410. */
    public boolean terminou() {
        for (Cidade cidade : cidades) {
            if (cidade.getDono().ehMonarca()) {
                return true;
            }
        }
        return getAnoAtual() > ANO_FINAL;
    }

    /** Vencedor: titulo mais alto; em caso de empate, maior prestigio. */
    public Cidade determinarVencedor(Nobreza nobreza) {
        Cidade melhor = null;
        for (Cidade cidade : cidades) {
            if (melhor == null) {
                melhor = cidade;
                continue;
            }
            int nivel = cidade.getDono().getNivelTitulo();
            int nivelMelhor = melhor.getDono().getNivelTitulo();
            if (nivel > nivelMelhor
                    || (nivel == nivelMelhor
                        && nobreza.calcularPrestigio(cidade) > nobreza.calcularPrestigio(melhor))) {
                melhor = cidade;
            }
        }
        return melhor;
    }
}
