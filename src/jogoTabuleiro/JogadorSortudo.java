package jogoTabuleiro;

/**
 * Jogador sortudo: a soma dos dois dados é sempre >= 7.
 */
public class JogadorSortudo extends Jogador {

    public JogadorSortudo(String nome, String cor) {
        super(nome, cor);
    }

    public JogadorSortudo(String nome, String cor, int posicao, int quantidadeJogadas, boolean perdeProximaRodada) {
        super(nome, cor, posicao, quantidadeJogadas, perdeProximaRodada);
    }

    @Override
    public Lance rolarDados(Dado dado) {
        Lance lance;
        do {
            lance = new Lance(dado.rolar(), dado.rolar());
        } while (lance.soma() < 7);
        return lance;
    }
}
