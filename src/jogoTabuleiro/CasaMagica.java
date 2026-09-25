package jogoTabuleiro;

/**
 * Casa mágica (20, 35): troca de posição com o jogador mais atrás.
 * Se o próprio jogador já for o último, não faz nada.
 */
public class CasaMagica extends Casa {

    public CasaMagica(int numero) {
        super(numero);
    }

    @Override
    public String aplicarEfeito(Jogador jogador, Jogo jogo) {
        // TODO: trocar de posição com o jogador mais atrás
        // (se o próprio jogador já for o último, não faz nada)
        return "";
    }
}
