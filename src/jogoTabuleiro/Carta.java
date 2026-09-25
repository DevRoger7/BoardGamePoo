package jogoTabuleiro;

import java.util.Random;

/**
 * Carta sorteada na casa surpresa, usada para definir o novo tipo do jogador.
 */
public class Carta {

    private static final Random RANDOM = new Random();

    private final TipoJogador tipo;

    private Carta(TipoJogador tipo) {
        this.tipo = tipo;
    }

    /**
     * Sorteia uma carta aleatória entre os três tipos de jogador.
     */
    public static Carta sortear() {
        TipoJogador[] tipos = TipoJogador.values();
        return new Carta(tipos[RANDOM.nextInt(tipos.length)]);
    }

    public TipoJogador getTipo() {
        return tipo;
    }
}
