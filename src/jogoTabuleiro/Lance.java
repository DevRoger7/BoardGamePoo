package jogoTabuleiro;

/**
 * Resultado de um lançamento de dois dados.
 */
public record Lance(int dado1, int dado2) {

    public int soma() {
        return dado1 + dado2;
    }

    public boolean isDuplo() {
        return dado1 == dado2;
    }
}
