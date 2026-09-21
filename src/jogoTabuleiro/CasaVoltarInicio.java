package jogoTabuleiro;

/**
 * Casa "volte ao início" (17, 27): o jogador escolhe um concorrente
 * para voltar à casa 0.
 */
public class CasaVoltarInicio extends Casa {

    public CasaVoltarInicio(int numero) {
        super(numero);
    }

    @Override
    public void aplicarEfeito(Jogador jogador, Jogo jogo) {
        // TODO: perguntar ao jogador qual concorrente deve voltar à casa 0
    }
}
