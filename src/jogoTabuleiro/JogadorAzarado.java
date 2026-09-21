package jogoTabuleiro;

/**
 * Jogador azarado: a soma dos dois dados é sempre <= 6.
 */
public class JogadorAzarado extends Jogador {

    public JogadorAzarado(String nome, String cor) {
        super(nome, cor);
    }

    @Override
    public Lance rolarDados(Dado dado) {
        // TODO: garantir soma <= 6
        return null;
    }
}
