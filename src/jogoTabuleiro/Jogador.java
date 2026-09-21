package jogoTabuleiro;

/**
 * Representa um jogador da partida.
 * Cada subclasse (sortudo, azarado, normal) define sua própria restrição
 * sobre a soma dos dados em {@link #rolarDados}.
 */
public abstract class Jogador {

    private final String nome;
    private final String cor;
    private int posicao;
    private int quantidadeJogadas;
    private boolean perdeProximaRodada;

    protected Jogador(String nome, String cor) {
        this.nome = nome;
        this.cor = cor;
        this.posicao = 0;
        this.quantidadeJogadas = 0;
        this.perdeProximaRodada = false;
    }

    /**
     * Realiza o lançamento dos dados respeitando a restrição de soma
     * própria do tipo de jogador (sortudo, azarado ou normal).
     */
    public abstract Lance rolarDados(Dado dado);

    public String getNome() {
        return nome;
    }

    public String getCor() {
        return cor;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public int getQuantidadeJogadas() {
        return quantidadeJogadas;
    }

    public void registrarJogada() {
        this.quantidadeJogadas++;
    }

    public boolean isPerdeProximaRodada() {
        return perdeProximaRodada;
    }

    public void setPerdeProximaRodada(boolean perdeProximaRodada) {
        this.perdeProximaRodada = perdeProximaRodada;
    }
}
