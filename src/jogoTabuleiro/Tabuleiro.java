package jogoTabuleiro;

import java.util.List;

/**
 * Representa o tabuleiro do jogo: um conjunto fixo de {@link Casa}s,
 * numeradas de 0 a {@code totalCasas}, cada uma com seu próprio efeito.
 */
public class Tabuleiro {

    private final int totalCasas;
    private final List<Casa> casas;

    public Tabuleiro(int totalCasas) {
        this.totalCasas = totalCasas;
        this.casas = montarCasas();
    }

    /**
     * Instancia todas as casas do tabuleiro, posicionando as casas especiais
     * definidas pelas regras e preenchendo as demais com {@link CasaNormal}.
     */
    private List<Casa> montarCasas() {
        // TODO: instanciar as 40 casas, posicionando as casas especiais
        // (10/25/38, 13, 5/15/30, 17/27, 20/35) e CasaNormal nas demais
        return null;
    }

    /**
     * Retorna a casa correspondente ao número informado (consulta, sem efeito colateral).
     */
    public Casa getCasa(int numero) {
        // TODO: retornar a casa correspondente ao número (query, sem efeito colateral)
        return null;
    }

    public int getTotalCasas() {
        return totalCasas;
    }
}
