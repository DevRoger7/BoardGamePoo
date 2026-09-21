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
        // TODO: soma livre, sem restrição
        return null;
    }
}
