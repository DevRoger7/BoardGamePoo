package jogoTabuleiro;

/**
 * Jogador sortudo: a soma dos dois dados é sempre >= 7.
 */
public class JogadorSortudo extends Jogador {

    public JogadorSortudo(String nome, String cor) {
        super(nome, cor);
    }

    @Override
    public Lance rolarDados(Dado dado) {
        while(Lance.soma() < 7){
            return Lan
        }
    }
}
