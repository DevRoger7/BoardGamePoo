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
        Carta carta = Carta.sortear();
        TipoJogador novoTipo = carta.getTipo();
        Jogador novoJogador = Jogador.criarComNovoTipo(jogador, novoTipo);
        jogo.substituirJogador(jogador, novoJogador);
        return jogador.getNome() + " caiu na casa surpresa e virou jogador " + novoTipo + "!";
    }
}
