package bolonhaemilano;

import java.util.List;

/** Testes automatizados das regras do jogo. Segundo main: nao faz parte do jogo. */
public class Testes {
    private static int aprovados = 0;
    private static int reprovados = 0;

    public static void main(String[] args) {
        testarCidade();
        testarMercado();
        testarAgricultura();
        testarGoverno();
        testarConstrucoes();
        testarJogadorENobreza();
        testarPartida();

        System.out.println();
        System.out.println("Aprovados: " + aprovados + " | Reprovados: " + reprovados);
        if (reprovados > 0) {
            System.exit(1);
        }
    }

    private static void verificar(String descricao, boolean condicao) {
        if (condicao) {
            aprovados++;
            System.out.println("[OK]    " + descricao);
        } else {
            reprovados++;
            System.out.println("[FALHA] " + descricao);
        }
    }

    private static Cidade novaCidade() {
        return new Cidade("Bolonha", new Jogador("Teste"));
    }

    private static void testarCidade() {
        Cidade cidade = novaCidade();
        verificar("cidade nova: 1000 florins, 10000 terras, 500 graos, 200 servos",
                cidade.getFlorins() == 1000 && cidade.getTerras() == 10000
                        && cidade.getGraos() == 500 && cidade.getServos() == 200);
        verificar("cidade nova comeca em 1400, sem soldados nem construcoes",
                cidade.getAnoAtual() == 1400 && cidade.getSoldados() == 0 && cidade.getConstrucoes() == 0);
        verificar("cidade nova: imposto 10% e justica Moderada",
                cidade.getTaxaImposto() == 10 && "Moderada".equals(cidade.getNivelJustica()));

        cidade.marcarColheitaFeita();
        cidade.marcarImpostosArrecadados();
        cidade.avancarAno();
        verificar("avancarAno soma 1 ao ano", cidade.getAnoAtual() == 1401);
        verificar("avancarAno libera colheita e arrecadacao",
                !cidade.isColheitaFeita() && !cidade.isImpostosArrecadados());

        boolean removeu = cidade.removerFlorins(5000);
        verificar("removerFlorins acima do saldo devolve false", !removeu);
        verificar("removerFlorins recusado nao altera o saldo", cidade.getFlorins() == 1000);
        verificar("removerFlorins dentro do saldo funciona", cidade.removerFlorins(300) && cidade.getFlorins() == 700);
    }

    private static void testarMercado() {
        Mercado mercado = new Mercado();
        Cidade cidade = novaCidade();
        boolean comprou = mercado.comprarGraos(cidade, 100);
        verificar("comprar 100 graos custa 200 florins e soma 100 graos",
                comprou && cidade.getFlorins() == 800 && cidade.getGraos() == 600);
        verificar("vender 100 graos devolve 200 florins",
                mercado.venderGraos(cidade, 100) && cidade.getFlorins() == 1000 && cidade.getGraos() == 500);
        verificar("comprar quantidade negativa e recusado", !mercado.comprarGraos(cidade, -5));
        verificar("comprar sem florins suficientes e recusado e nao altera nada",
                !mercado.comprarTerras(cidade, 1000) && cidade.getFlorins() == 1000 && cidade.getTerras() == 10000);
        verificar("vender mais graos do que tem e recusado",
                !mercado.venderGraos(cidade, 9999) && cidade.getGraos() == 500);
        verificar("comprar e vender terras funciona",
                mercado.comprarTerras(cidade, 10) && cidade.getTerras() == 10010
                        && mercado.venderTerras(cidade, 10) && cidade.getFlorins() == 1000);
    }

    private static void testarAgricultura() {
        Agricultura agricultura = new Agricultura();
        Cidade cidade = novaCidade();
        int hectares = agricultura.processarColheita(cidade);
        verificar("1a colheita cultiva 1000 hectares", hectares == 1000);
        verificar("colheita gasta 500 sementes e produz 2000 graos", cidade.getGraos() == 2000);
        verificar("2a colheita no mesmo ano devolve -1", agricultura.processarColheita(cidade) == -1);

        Cidade cidade2 = novaCidade();
        verificar("percentual 10 e recusado", !agricultura.distribuirAlimentos(cidade2, 10));
        verificar("percentual 90 e recusado", !agricultura.distribuirAlimentos(cidade2, 90));
        verificar("distribuir com comida suficiente faz a populacao crescer 5%",
                agricultura.distribuirAlimentos(cidade2, 50) && cidade2.getServos() == 210);

        Cidade cidade3 = novaCidade();
        cidade3.removerGraos(500);
        agricultura.distribuirAlimentos(cidade3, 50);
        verificar("sem comida, a populacao cai", cidade3.getServos() < 200);
    }

    private static void testarGoverno() {
        Governo governo = new Governo();
        Cidade cidade = novaCidade();
        verificar("justica Severa aceita", governo.definirJustica(cidade, "Severa"));
        verificar("justica invalida recusada", !governo.definirJustica(cidade, "Tirana"));
        verificar("Severa a 10% arrecada 26 florins",
                governo.arrecadarImpostos(cidade) == 26 && cidade.getFlorins() == 1026);
        verificar("2a arrecadacao no mesmo ano devolve -1", governo.arrecadarImpostos(cidade) == -1);
        verificar("taxa 60% e recusada e nao altera a taxa",
                !governo.definirTaxaImposto(cidade, 60) && cidade.getTaxaImposto() == 10);
        verificar("taxa 30% e aceita", governo.definirTaxaImposto(cidade, 30) && cidade.getTaxaImposto() == 30);
    }

    private static void testarConstrucoes() {
        Construcoes construcoes = new Construcoes();
        Cidade cidade = novaCidade();
        verificar("construir 10 custa 200 florins",
                construcoes.construir(cidade, 10) && cidade.getFlorins() == 800 && cidade.getConstrucoes() == 10);
        verificar("recrutar 20 soldados custa 100 florins e 20 servos",
                construcoes.recrutarSoldados(cidade, 20) && cidade.getFlorins() == 700
                        && cidade.getServos() == 180 && cidade.getSoldados() == 20);

        Cidade pobre = novaCidade();
        pobre.removerServos(150);
        boolean recrutou = construcoes.recrutarSoldados(pobre, 100);
        verificar("recrutar 100 soldados com so 50 servos e recusado", !recrutou);
        verificar("recrutamento recusado NAO debita florins pela metade", pobre.getFlorins() == 1000);
    }

    private static void testarJogadorENobreza() {
        Nobreza nobreza = new Nobreza();
        Cidade cidade = novaCidade();
        verificar("prestigio inicial e 300", nobreza.calcularPrestigio(cidade) == 300);
        verificar("prestigio 300 nao promove", !nobreza.verificarPromocao(cidade));

        cidade.adicionarConstrucoes(50);
        verificar("50 construcoes promovem a Barao", nobreza.verificarPromocao(cidade)
                && "Barao".equals(cidade.getDono().getTitulo()));
        verificar("so 1 titulo por chamada (continua Barao)", cidade.getDono().getNivelTitulo() == 1);

        Jogador rei = new Jogador("Rei");
        while (rei.promover()) {
            // sobe ate o topo
        }
        verificar("jogador chega a Rei/Rainha", rei.ehMonarca());
        verificar("Rei/Rainha nao e promovido de novo", !rei.promover());
    }

    private static void testarPartida() {
        Nobreza nobreza = new Nobreza();
        Partida partida = new Partida();
        Cidade a = new Cidade("Bolonha", new Jogador("Ana"));
        Cidade b = new Cidade("Milano", new Jogador("Beto"));
        partida.adicionarCidade(a);
        partida.adicionarCidade(b);
        verificar("o primeiro turno e da primeira cidade", partida.getCidadeAtual() == a);
        verificar("o 1o passarTurno nao fecha o ano", !partida.passarTurno());
        verificar("o 2o passarTurno vai para a segunda cidade", partida.getCidadeAtual() == b);
        verificar("o 3o passarTurno fecha o ano e volta ao primeiro", partida.passarTurno() && partida.getCidadeAtual() == a);

        List<Cidade> promovidas = partida.encerrarAno(nobreza);
        verificar("encerrarAno avanca todas as cidades", a.getAnoAtual() == 1401 && b.getAnoAtual() == 1401);
        verificar("encerrarAno sem prestigio nao promove ninguem", promovidas.isEmpty());
        verificar("partida nao terminou em 1401", !partida.terminou());

        b.adicionarConstrucoes(100);
        verificar("o vencedor parcial e quem tem mais prestigio", partida.determinarVencedor(nobreza) == b);

        Partida cheia = new Partida();
        for (int i = 0; i < 4; i++) {
            cheia.adicionarCidade(novaCidade());
        }
        verificar("a 5a cidade e recusada", !cheia.adicionarCidade(novaCidade()));
    }
}
