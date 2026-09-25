package jogoTabuleiro;

/**
 * Jogador azarado: a soma dos dois dados é sempre <= 6.
 */
public class JogadorAzarado extends Jogador {

    public JogadorAzarado(String nome, String cor) {
        super(nome, cor);
    }

    public JogadorAzarado(String nome, String cor, int posicao, int quantidadeJogadas, boolean perdeProximaRodada) {
        super(nome, cor, posicao, quantidadeJogadas, perdeProximaRodada);
    }

    @Override
    public Lance rolarDados(Dado dado) {
        Lance lance;
        do{
            lance = new Lance(dado.rolar(), dado.rolar());
        }while(lance.soma()>6);
        return lance;
    }

    @Override
    public boolean podeAproveitarSorte() {
        return false;
    }
}