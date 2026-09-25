package jogoTabuleiro;

/**
 * Casa surpresa (13): sorteia uma carta que muda o tipo do jogador
 * (sortudo, azarado ou normal).
 */
public class CasaSurpresa extends Casa {

    public CasaSurpresa(int numero) {
        super(numero);
    }

    @Override
    public String aplicarEfeito(Jogador jogador, Jogo jogo) {
        // TODO: sortear uma Carta e trocar o tipo do jogador de acordo com ela
        return "";
    }
}
