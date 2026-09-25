package jogoTabuleiro;

/**
 * Representa uma casa do tabuleiro.
 * Cada tipo de casa (normal, espera, surpresa, sorte, voltar ao início,
 * mágica) define seu próprio efeito sobrescrevendo {@link #aplicarEfeito}.
 */
public abstract class Casa {

    private final int numero;

    protected Casa(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    /**
     * Aplica o efeito específico da casa quando um jogador para sobre ela.
     * Cada subclasse decide o que acontece — chamado sempre polimorficamente,
     * sem checagem de tipo concreto (instanceof/getClass) por parte de quem invoca.
     *
     * @param jogador jogador que parou nesta casa
     * @param jogo    contexto da partida (acesso a outros jogadores, scanner, etc.)
     * @return mensagem descrevendo o que aconteceu (exibida como último evento
     *         da rodada); {@code ""} quando a casa não tem efeito a relatar
     */
    public abstract String aplicarEfeito(Jogador jogador, Jogo jogo);
}
