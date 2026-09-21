package jogoTabuleiro;

/**
 * Casa sem nenhum efeito especial (todas as casas não listadas na tabela de regras).
 */
public class CasaNormal extends Casa {

    public CasaNormal(int numero) {
        super(numero);
    }

    @Override
    public void aplicarEfeito(Jogador jogador, Jogo jogo) {
        // sem efeito especial
    }
}
