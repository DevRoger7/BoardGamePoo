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
    public void aplicarEfeito(Jogador jogador, Jogo jogo) {
        // TODO: avançar CASAS_A_AVANCAR casas, exceto se o jogador for JogadorAzarado
    }
}
