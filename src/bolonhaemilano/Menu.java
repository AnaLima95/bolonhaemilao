package bolonhaemilano;

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;

/** Interface de texto: exibe o menu, le a entrada e delega para as outras classes. */
public class Menu {
    private static final String[] NOMES_CIDADES = {"Bolonha", "Milano", "Florenca", "Veneza"};

    private Scanner scanner = new Scanner(System.in);
    private boolean emExecucao = true;
    private Partida partida;
    private Cidade cidade;
    private Mercado mercado = new Mercado();
    private Agricultura agricultura = new Agricultura();
    private Governo governo = new Governo();
    private Construcoes construcoes = new Construcoes();
    private Nobreza nobreza = new Nobreza();

    public void executar() {
        while (emExecucao) {
            exibirOpcoes();
            int opcao = lerOpcao();
            processarOpcao(opcao);
        }
        scanner.close();
    }

    private void exibirOpcoes() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println(" BOLONHA E MILANO - CRONICAS DA ITALIA");
        System.out.println(" Uma simulacao de administracao de cidades medievais");
        System.out.println("==========================================");
        if (partida != null) {
            System.out.println("Ano " + partida.getAnoAtual() + " | Vez de " + cidade.getDono().getNome()
                    + " (" + cidade.getNome() + ")");
        }
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Mercado");
        System.out.println("4 - Agricultura");
        System.out.println("5 - Governo");
        System.out.println("6 - Construcoes");
        System.out.println("7 - Encerrar turno");
        System.out.println("8 - Ver classificacao");
        System.out.println("9 - Sobre o jogo");
        System.out.println("0 - Sair");
    }

    private int lerOpcao() {
        System.out.print("Escolha uma opcao: ");
        return lerInteiro();
    }

    private int lerQuantidade() {
        System.out.print("Digite o valor: ");
        return lerInteiro();
    }

    /** Le uma linha e converte para int. Devolve -1 se nao for numero e 0 se a entrada acabou. */
    private int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    private String lerTexto() {
        try {
            return scanner.nextLine().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    /** Se nao ha partida, avisa e devolve false. Cada submenu faz: if (!exigir...) return; */
    private boolean exigirJogoEmAndamento() {
        if (partida == null) {
            System.out.println("Nenhum jogo em andamento. Escolha 'Novo jogo' primeiro.");
            return false;
        }
        return true;
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                iniciarNovoJogo();
                break;
            case 2:
                exibirStatus();
                break;
            case 3:
                abrirMercado();
                break;
            case 4:
                abrirAgricultura();
                break;
            case 5:
                abrirGoverno();
                break;
            case 6:
                abrirConstrucoes();
                break;
            case 7:
                encerrarTurno();
                break;
            case 8:
                exibirClassificacao();
                break;
            case 9:
                exibirSobre();
                break;
            case 0:
                emExecucao = false;
                System.out.println("Ate a proxima!");
                break;
            default:
                System.out.println("Opcao invalida. Tente novamente.");
        }
    }

    private void iniciarNovoJogo() {
        System.out.print("Quantos jogadores (1 a 4)? ");
        int quantos = lerInteiro();
        if (quantos < 1 || quantos > Partida.MAXIMO_JOGADORES) {
            System.out.println("Valor invalido: jogando com 2 jogadores.");
            quantos = 2;
        }
        partida = new Partida();
        for (int i = 0; i < quantos; i++) {
            System.out.println();
            System.out.println("--- Jogador " + (i + 1) + " ---");
            System.out.print("Nome do jogador: ");
            String nome = lerTexto();
            if (nome.isEmpty()) {
                nome = "Jogador " + (i + 1);
            }
            System.out.print("Nome da cidade [" + NOMES_CIDADES[i] + "]: ");
            String nomeCidade = lerTexto();
            if (nomeCidade.isEmpty()) {
                nomeCidade = NOMES_CIDADES[i];
            }
            partida.adicionarCidade(new Cidade(nomeCidade, new Jogador(nome)));
        }
        cidade = partida.getCidadeAtual();
        System.out.println();
        System.out.println("A partida comeca no ano " + partida.getAnoAtual()
                + " e termina no ano " + partida.getAnoFinal() + " (ou quando alguem virar Rei/Rainha).");
        System.out.println("Vez de " + cidade.getDono().getNome() + ".");
        cidade.exibirStatus();
    }

    private void exibirStatus() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        cidade.exibirStatus();
    }

    private void abrirMercado() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        boolean noMercado = true;
        while (noMercado) {
            System.out.println();
            System.out.println("--- MERCADO ---");
            System.out.println("Florins: " + cidade.getFlorins() + " | Graos: " + cidade.getGraos() + " | Terras: " + cidade.getTerras());
            System.out.println("Preco do grao: " + mercado.getPrecoGrao() + " | Preco do hectare: " + mercado.getPrecoTerra());
            System.out.println("1 - Comprar graos");
            System.out.println("2 - Vender graos");
            System.out.println("3 - Comprar terras");
            System.out.println("4 - Vender terras");
            System.out.println("0 - Voltar");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    comprarGraos();
                    break;
                case 2:
                    venderGraos();
                    break;
                case 3:
                    comprarTerras();
                    break;
                case 4:
                    venderTerras();
                    break;
                case 0:
                    noMercado = false;
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }
    }

    private void comprarGraos() {
        int qtd = lerQuantidade();
        if (mercado.comprarGraos(cidade, qtd)) {
            System.out.println("Compra realizada: " + qtd + " graos.");
        } else {
            System.out.println("Compra recusada (quantidade invalida ou florins insuficientes).");
        }
    }

    private void venderGraos() {
        int qtd = lerQuantidade();
        if (mercado.venderGraos(cidade, qtd)) {
            System.out.println("Venda realizada: " + qtd + " graos.");
        } else {
            System.out.println("Venda recusada (quantidade invalida ou graos insuficientes).");
        }
    }

    private void comprarTerras() {
        int qtd = lerQuantidade();
        if (mercado.comprarTerras(cidade, qtd)) {
            System.out.println("Compra realizada: " + qtd + " hectares.");
        } else {
            System.out.println("Compra recusada (quantidade invalida ou florins insuficientes).");
        }
    }

    private void venderTerras() {
        int qtd = lerQuantidade();
        if (mercado.venderTerras(cidade, qtd)) {
            System.out.println("Venda realizada: " + qtd + " hectares.");
        } else {
            System.out.println("Venda recusada (quantidade invalida ou terras insuficientes).");
        }
    }

    private void abrirAgricultura() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        boolean noAgricultura = true;
        while (noAgricultura) {
            System.out.println();
            System.out.println("--- AGRICULTURA ---");
            System.out.println("Terras: " + cidade.getTerras() + " | Servos: " + cidade.getServos() + " | Graos: " + cidade.getGraos());
            System.out.println("1 - Colher");
            System.out.println("2 - Distribuir alimentos");
            System.out.println("0 - Voltar");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    colher();
                    break;
                case 2:
                    distribuirAlimentos();
                    break;
                case 0:
                    noAgricultura = false;
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }
    }

    private void colher() {
        int graosAntes = cidade.getGraos();
        int hectares = agricultura.processarColheita(cidade);
        if (hectares == -1) {
            System.out.println("Voce ja colheu neste ano. Espere o proximo ano.");
            return;
        }
        System.out.println("Colheita: " + hectares + " hectares cultivados.");
        System.out.println("Graos: " + graosAntes + " -> " + cidade.getGraos());
    }

    private void distribuirAlimentos() {
        System.out.println("Percentual do estoque de graos a distribuir (20 a 80):");
        int percentual = lerQuantidade();
        int servosAntes = cidade.getServos();
        if (agricultura.distribuirAlimentos(cidade, percentual)) {
            System.out.println("Alimentos distribuidos. Servos: " + servosAntes + " -> " + cidade.getServos());
        } else {
            System.out.println("Percentual invalido: use um valor de 20 a 80.");
        }
    }

    private void abrirGoverno() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        boolean noGoverno = true;
        while (noGoverno) {
            System.out.println();
            System.out.println("--- GOVERNO ---");
            System.out.println("Florins: " + cidade.getFlorins() + " | Taxa: " + cidade.getTaxaImposto() + "% | Justica: " + cidade.getNivelJustica());
            System.out.println("1 - Arrecadar impostos");
            System.out.println("2 - Definir taxa de imposto");
            System.out.println("3 - Definir nivel de justica");
            System.out.println("0 - Voltar");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    arrecadarImpostos();
                    break;
                case 2:
                    definirTaxaImposto();
                    break;
                case 3:
                    definirJustica();
                    break;
                case 0:
                    noGoverno = false;
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }
    }

    private void arrecadarImpostos() {
        int arrecadado = governo.arrecadarImpostos(cidade);
        if (arrecadado == -1) {
            System.out.println("Os impostos deste ano ja foram arrecadados.");
            return;
        }
        System.out.println("Impostos arrecadados: " + arrecadado + " florins.");
    }

    private void definirTaxaImposto() {
        System.out.println("Nova taxa de imposto (0 a 50):");
        int taxa = lerQuantidade();
        if (governo.definirTaxaImposto(cidade, taxa)) {
            System.out.println("Taxa definida: " + taxa + "%.");
        } else {
            System.out.println("Taxa invalida: use um valor de 0 a 50.");
        }
    }

    private void definirJustica() {
        System.out.println("1 - Justa | 2 - Moderada | 3 - Severa");
        int opcao = lerOpcao();
        String nivel = null;
        if (opcao == 1) {
            nivel = "Justa";
        } else if (opcao == 2) {
            nivel = "Moderada";
        } else if (opcao == 3) {
            nivel = "Severa";
        }
        if (governo.definirJustica(cidade, nivel)) {
            System.out.println("Nivel de justica definido: " + nivel + ".");
        } else {
            System.out.println("Nivel invalido.");
        }
    }

    private void abrirConstrucoes() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        boolean noConstrucoes = true;
        while (noConstrucoes) {
            System.out.println();
            System.out.println("--- CONSTRUCOES ---");
            System.out.println("Florins: " + cidade.getFlorins() + " | Servos: " + cidade.getServos() + " | Soldados: " + cidade.getSoldados() + " | Construcoes: " + cidade.getConstrucoes());
            System.out.println("Soldado: " + construcoes.getPrecoSoldado() + " florins + 1 servo | Construcao: " + construcoes.getPrecoConstrucao() + " florins");
            System.out.println("1 - Recrutar soldados");
            System.out.println("2 - Construir");
            System.out.println("0 - Voltar");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    recrutarSoldados();
                    break;
                case 2:
                    construir();
                    break;
                case 0:
                    noConstrucoes = false;
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        }
    }

    private void recrutarSoldados() {
        int qtd = lerQuantidade();
        if (construcoes.recrutarSoldados(cidade, qtd)) {
            System.out.println(qtd + " soldados recrutados.");
        } else {
            System.out.println("Recrutamento recusado (quantidade invalida, florins ou servos insuficientes).");
        }
    }

    private void construir() {
        int qtd = lerQuantidade();
        if (construcoes.construir(cidade, qtd)) {
            System.out.println(qtd + " construcoes erguidas.");
        } else {
            System.out.println("Construcao recusada (quantidade invalida ou florins insuficientes).");
        }
    }

    private void encerrarTurno() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        boolean fimDoAno = partida.passarTurno();
        if (fimDoAno) {
            List<Cidade> promovidas = partida.encerrarAno(nobreza);
            System.out.println();
            System.out.println("=== FIM DO ANO " + (partida.getAnoAtual() - 1) + " ===");
            for (Cidade c : promovidas) {
                System.out.println(c.getDono().getNome() + " foi promovido a " + c.getDono().getTitulo() + "!");
            }
            exibirClassificacao();
            if (partida.terminou()) {
                encerrarPartida();
                return;
            }
        }
        cidade = partida.getCidadeAtual();
        System.out.println();
        System.out.println("Agora e a vez de " + cidade.getDono().getNome() + " (" + cidade.getNome()
                + "), ano " + cidade.getAnoAtual() + ".");
    }

    private void exibirClassificacao() {
        if (!exigirJogoEmAndamento()) {
            return;
        }
        List<Cidade> ordem = new ArrayList<>(partida.getCidades());
        ordem.sort((a, b) -> {
            int porTitulo = Integer.compare(b.getDono().getNivelTitulo(), a.getDono().getNivelTitulo());
            if (porTitulo != 0) {
                return porTitulo;
            }
            return Integer.compare(nobreza.calcularPrestigio(b), nobreza.calcularPrestigio(a));
        });
        System.out.println();
        System.out.println("--- CLASSIFICACAO ---");
        int posicao = 1;
        for (Cidade c : ordem) {
            Jogador dono = c.getDono();
            int prestigio = nobreza.calcularPrestigio(c);
            int proximo = nobreza.prestigioParaProximoTitulo(dono);
            String falta = (proximo == -1) ? "titulo maximo" : (prestigio >= proximo ? "sobe de titulo no fim do ano" : "faltam " + (proximo - prestigio) + " para o proximo titulo");
            System.out.println(posicao + ". " + c.getNome() + " - " + dono.getNome() + " (" + dono.getTitulo()
                    + ") | prestigio " + prestigio + " | " + falta);
            posicao++;
        }
    }

    private void encerrarPartida() {
        Cidade vencedor = partida.determinarVencedor(nobreza);
        System.out.println();
        System.out.println("##########  FIM DE JOGO  ##########");
        System.out.println("Vencedor: " + vencedor.getDono().getNome() + ", " + vencedor.getDono().getTitulo()
                + " de " + vencedor.getNome() + "!");
        partida = null;
        cidade = null;
    }

    private void exibirSobre() {
        System.out.println();
        System.out.println("--- SOBRE O JOGO ---");
        System.out.println("Bolonha e Milano e uma simulacao de administracao de cidades italianas no seculo XV.");
        System.out.println("Voce governa uma cidade: compra e vende graos e terras, planta, cobra impostos,");
        System.out.println("recruta soldados e ergue construcoes.");
        System.out.println("Cada jogador joga seu turno; quando o ultimo encerra, o ano fecha.");
        System.out.println("Vence quem virar Rei/Rainha primeiro ou o melhor colocado ao fim de 1410.");
    }
}
