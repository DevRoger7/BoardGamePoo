package jogoTabuleiro;

/**
 * Tipo de jogador sorteado pela casa surpresa. Funciona como o contrato
 * compartilhado entre {@link Carta} (que sorteia um tipo) e
 * {@link Jogador#criarComNovoTipo} (que decide qual subclasse concreta
 * instanciar) — nenhum dos dois lados precisa conhecer a lista de
 * subclasses do outro.
 */
public enum TipoJogador {
    SORTUDO,
    AZARADO,
    NORMAL
}
