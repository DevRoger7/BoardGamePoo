package jogoTabuleiro;

/**
 * Casa de espera (10, 25, 38): o jogador que para aqui perde a próxima rodada.
 */
public class CasaEspera extends Casa {

    public CasaEspera(int numero) {
        super(numero);
    }

    @Override
    public String aplicarEfeito(Jogador jogador, Jogo jogo) {
        jogador.setPerdeProximaRodada(true);
        return jogador.getNome() + " parou na casa " + getNumero() + " e perde a próxima rodada!";
    }
}
