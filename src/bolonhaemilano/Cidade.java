package bolonhaemilano;

/** O que e governado: ano, recursos, configuracoes de governo e defesa. */
public class Cidade {
    private String nome;
    private int anoAtual;
    private Jogador dono;
    private int florins;
    private int terras;
    private int graos;
    private int servos;
    private int taxaImposto;
    private String nivelJustica;
    private int soldados;
    private int construcoes;
    private boolean colheitaFeita;
    private boolean impostosArrecadados;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
        this.anoAtual = 1400;
        this.florins = 1000;
        this.terras = 10000;
        this.graos = 500;
        this.servos = 200;
        this.taxaImposto = 10;
        this.nivelJustica = "Moderada";
        this.soldados = 0;
        this.construcoes = 0;
        this.colheitaFeita = false;
        this.impostosArrecadados = false;
    }

    public String getNome() {
        return nome;
    }

    public int getAnoAtual() {
        return anoAtual;
    }

    public Jogador getDono() {
        return dono;
    }

    /** Soma 1 ao ano e libera de novo a colheita e a arrecadacao. */
    public void avancarAno() {
        anoAtual++;
        colheitaFeita = false;
        impostosArrecadados = false;
    }

    public boolean isColheitaFeita() {
        return colheitaFeita;
    }

    public void marcarColheitaFeita() {
        colheitaFeita = true;
    }

    public boolean isImpostosArrecadados() {
        return impostosArrecadados;
    }

    public void marcarImpostosArrecadados() {
        impostosArrecadados = true;
    }

    public int getFlorins() {
        return florins;
    }

    public void adicionarFlorins(int qtd) {
        if (qtd > 0) {
            florins += qtd;
        }
    }

    /** Devolve false (sem alterar nada) se nao houver quantidade suficiente. */
    public boolean removerFlorins(int qtd) {
        if (qtd < 0 || qtd > florins) {
            return false;
        }
        florins -= qtd;
        return true;
    }

    public int getTerras() {
        return terras;
    }

    public void adicionarTerras(int qtd) {
        if (qtd > 0) {
            terras += qtd;
        }
    }

    /** Devolve false (sem alterar nada) se nao houver quantidade suficiente. */
    public boolean removerTerras(int qtd) {
        if (qtd < 0 || qtd > terras) {
            return false;
        }
        terras -= qtd;
        return true;
    }

    public int getGraos() {
        return graos;
    }

    public void adicionarGraos(int qtd) {
        if (qtd > 0) {
            graos += qtd;
        }
    }

    /** Devolve false (sem alterar nada) se nao houver quantidade suficiente. */
    public boolean removerGraos(int qtd) {
        if (qtd < 0 || qtd > graos) {
            return false;
        }
        graos -= qtd;
        return true;
    }

    public int getServos() {
        return servos;
    }

    public void adicionarServos(int qtd) {
        if (qtd > 0) {
            servos += qtd;
        }
    }

    /** Devolve false (sem alterar nada) se nao houver quantidade suficiente. */
    public boolean removerServos(int qtd) {
        if (qtd < 0 || qtd > servos) {
            return false;
        }
        servos -= qtd;
        return true;
    }

    public int getTaxaImposto() {
        return taxaImposto;
    }

    public void setTaxaImposto(int taxa) {
        this.taxaImposto = taxa;
    }

    public String getNivelJustica() {
        return nivelJustica;
    }

    public void setNivelJustica(String nivel) {
        this.nivelJustica = nivel;
    }

    public int getSoldados() {
        return soldados;
    }

    public void adicionarSoldados(int qtd) {
        if (qtd > 0) {
            soldados += qtd;
        }
    }

    /** Devolve false (sem alterar nada) se nao houver quantidade suficiente. */
    public boolean removerSoldados(int qtd) {
        if (qtd < 0 || qtd > soldados) {
            return false;
        }
        soldados -= qtd;
        return true;
    }

    public int getConstrucoes() {
        return construcoes;
    }

    public void adicionarConstrucoes(int qtd) {
        if (qtd > 0) {
            construcoes += qtd;
        }
    }

    /** Devolve false (sem alterar nada) se nao houver quantidade suficiente. */
    public boolean removerConstrucoes(int qtd) {
        if (qtd < 0 || qtd > construcoes) {
            return false;
        }
        construcoes -= qtd;
        return true;
    }

    public void exibirStatus() {
        System.out.println();
        System.out.println("===== CIDADE: " + nome + " =====");
        System.out.println("Governante: " + dono.getTitulo() + " " + dono.getNome());
        System.out.println("Ano: " + anoAtual);
        System.out.println("Florins: " + florins);
        System.out.println("Terras: " + terras + " hectares");
        System.out.println("Graos: " + graos);
        System.out.println("Servos: " + servos);
        System.out.println("Taxa de imposto: " + taxaImposto + "%");
        System.out.println("Nivel de justica: " + nivelJustica);
        System.out.println("Soldados: " + soldados);
        System.out.println("Construcoes: " + construcoes);
        System.out.println("Colheita deste ano: " + (colheitaFeita ? "ja feita" : "pendente"));
        System.out.println("Impostos deste ano: " + (impostosArrecadados ? "ja arrecadados" : "pendentes"));
    }
}
