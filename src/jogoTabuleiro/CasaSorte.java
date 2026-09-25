package jogoTabuleiro;

/**
 * Casa da sorte (5, 15, 30): avança {@value #CASAS_A_AVANCAR} casas,
 * exceto para o jogador azarado.
 */
public class CasaSorte extends Casa {

    private static final int CASAS_A_AVANCAR = 3;

    public CasaSorte(int numero) {
        super(numero);
    }

    @Override
    public String aplicarEfeito(Jogador jogador, Jogo jogo) {
        if (jogador.podeAproveitarSorte()) {
            jogador.setPosicao(jogador.getPosicao() + CASAS_A_AVANCAR);
            return jogador.getNome() + " caiu na casa da sorte e avançou "
                    + CASAS_A_AVANCAR + " casas!";
        }
        return jogador.getNome() + " caiu na casa da sorte, mas é azarado demais pra aproveitar.";
    }
}
