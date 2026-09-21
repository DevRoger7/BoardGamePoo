package jogoTabuleiro;

/**
 * Casa de espera (10, 25, 38): o jogador que para aqui perde a próxima rodada.
 */
public class CasaEspera extends Casa {

    public CasaEspera(int numero) {
        super(numero);
    }

    @Override
    public void aplicarEfeito(Jogador jogador, Jogo jogo) {
        // TODO: marcar o jogador para perder a próxima rodada
    }
}
