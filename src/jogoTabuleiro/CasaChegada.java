package jogoTabuleiro;

/**
 * Casa de chegada (40 / totalCasas): fim do jogo.
 */
public class CasaChegada extends Casa {

    public CasaChegada(int numero) {
        super(numero);
    }

    @Override
    public String aplicarEfeito(Jogador jogador, Jogo jogo) {
        // TODO: verificar condição de vitória
        return "";
    }
}