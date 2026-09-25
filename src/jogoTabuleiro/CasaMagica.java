package jogoTabuleiro;

/**
 * Casa mágica (20, 35): troca de posição com o jogador mais atrás.
 * Se o próprio jogador já for o último, não faz nada.
 */
public class CasaMagica extends Casa {

    public CasaMagica(int numero) {
        super(numero);
    }

    @Override
    public String aplicarEfeito(Jogador jogador, Jogo jogo) {
        Jogador maisAtras = null;
        for (Jogador outro : jogo.getJogadores()) {
            if (outro != jogador
                    && (maisAtras == null || outro.getPosicao() < maisAtras.getPosicao())) {
                maisAtras = outro;
            }
        }

        // já é o último (ou está empatado na última posição): não sai do lugar
        if (maisAtras == null || jogador.getPosicao() <= maisAtras.getPosicao()) {
            return jogador.getNome() + " caiu na casa mágica " + getNumero()
                    + ", mas já é o último e não sai do lugar.";
        }

        int posicaoAntiga = jogador.getPosicao();
        jogador.setPosicao(maisAtras.getPosicao());
        maisAtras.setPosicao(posicaoAntiga);
        System.out.println(jogador.getNome() + " caiu na casa mágica " + getNumero()
                + " e trocou de lugar com " + maisAtras.getNome() + "!");
        return "";
    }
}
