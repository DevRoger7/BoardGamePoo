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

    protected Jogador(String nome, String cor, int posicao, int quantidadeJogadas, boolean perdeProximaRodada) {
        this.nome = nome;
        this.cor = cor;
        this.posicao = posicao;
        this.quantidadeJogadas = quantidadeJogadas;
        this.perdeProximaRodada = perdeProximaRodada;
    }

    /**
     * Realiza o lançamento dos dados respeitando a restrição de soma
     * própria do tipo de jogador (sortudo, azarado ou normal).
     */
    public abstract Lance rolarDados(Dado dado);

    /**
     * Indica se este jogador aproveita o efeito da casa da sorte.
     * Por padrão todo jogador aproveita; {@link JogadorAzarado} sobrescreve
     * para {@code false}, evitando checagem de tipo concreto em {@link CasaSorte}.
     */
    public boolean podeAproveitarSorte() {
        return true;
    }

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

    /**
     * Cria uma nova instância de Jogador do tipo indicado, preservando nome,
     * cor, posição, quantidade de jogadas e status de perdeProximaRodada do
     * jogador original. Usado pela casa surpresa (13) quando o jogador muda
     * de tipo — a lista de jogadores em {@link Jogo} é atualizada via
     * {@link Jogo#substituirJogador}; o objeto antigo é descartado.
     */
    public static Jogador criarComNovoTipo(Jogador antigo, TipoJogador novoTipo) {
        return switch (novoTipo) {
            case SORTUDO -> new JogadorSortudo(antigo.getNome(), antigo.getCor(), antigo.getPosicao(), antigo.getQuantidadeJogadas(), antigo.isPerdeProximaRodada());
            case AZARADO -> new JogadorAzarado(antigo.getNome(), antigo.getCor(), antigo.getPosicao(), antigo.getQuantidadeJogadas(), antigo.isPerdeProximaRodada());
            case NORMAL -> new JogadorNormal(antigo.getNome(), antigo.getCor(), antigo.getPosicao(), antigo.getQuantidadeJogadas(), antigo.isPerdeProximaRodada());
        };
    }
}
