package jogoTabuleiro;

/**
 * Jogador normal: sem restrição sobre a soma dos dados.
 */
public class JogadorNormal extends Jogador {

    public JogadorNormal(String nome, String cor) {
        super(nome, cor);
    }

    @Override
    public Lance rolarDados(Dado dado) {
        return new Lance(dado.rolar(), dado.rolar());
    }
}
