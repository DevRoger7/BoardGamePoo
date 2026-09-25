package jogoTabuleiro;

/**
 * Paleta fixa de cores disponíveis para os jogadores. Fonte única para o
 * menu (que lista as opções numeradas) e para {@link Tabuleiro} (que resolve
 * o RGB de cada jogador na hora de desenhar) — evita duplicar a lista de
 * cores em dois lugares e permite validar a escolha do jogador contra um
 * conjunto fechado, em vez de aceitar qualquer texto digitado.
 */
public enum CorJogador {

    AZUL("Azul", 59, 130, 246),
    VERDE("Verde", 34, 197, 94),
    AMARELO("Amarelo", 234, 179, 8),
    ROXO("Roxo", 168, 85, 247),
    VERMELHO("Vermelho", 239, 68, 68),
    LARANJA("Laranja", 249, 115, 22);

    private final String nomeExibicao;
    private final int r;
    private final int g;
    private final int b;

    CorJogador(String nomeExibicao, int r, int g, int b) {
        this.nomeExibicao = nomeExibicao;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }

    public String codigoAnsi() {
        return ConsoleUI.cor(r, g, b);
    }

    /**
     * Identificador estável salvo em {@link Jogador#getCor()} (usado tanto para
     * exibição textual quanto para resolver a cor de volta via {@link #fromNome}).
     */
    public String getNome() {
        return nomeExibicao.toLowerCase();
    }

    /**
     * Resolve uma cor a partir do identificador salvo no jogador. Retorna
     * {@code null} se não houver correspondência, em vez de lançar exceção —
     * quem chama decide o que fazer com uma cor desconhecida (ex.: usar branco
     * como fallback de exibição).
     */
    public static CorJogador fromNome(String nome) {
        if (nome == null) {
            return null;
        }
        String normalizado = nome.trim().toLowerCase();
        for (CorJogador cor : values()) {
            if (cor.getNome().equals(normalizado)) {
                return cor;
            }
        }
        return null;
    }
}
