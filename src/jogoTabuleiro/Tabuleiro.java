package jogoTabuleiro;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    // ==================================================================
    // Visualização em console: grade de referência (estática) + barras de
    // progresso por jogador (dinâmicas), dentro de uma única moldura que é
    // redesenhada a cada jogada. Ponto de conexão pronto — basta chamar
    // {@link #imprimirTela} de dentro de {@link Jogo#executarTurno} a cada
    // jogada para a visualização funcionar, sem precisar mexer aqui.
    // Classificação de tipo de casa aqui é só para exibição (número fixo
    // definido pelas regras) — nada disso decide efeito de jogo, então não
    // fere a regra de resolver Casa por polimorfismo em aplicarEfeito.
    // ==================================================================

    private static final int CASAS_POR_LINHA = 10;
    private static final int NOME_LARGURA = 10;
    private static final String MARCADOR_ESPECIAL = "│";

    private enum TipoCasaVisual {
        NORMAL(" ", 34, 48, 60),
        SORTE("*", 245, 158, 11),
        PERDE_VEZ("P", 239, 68, 68),
        SURPRESA("?", 236, 72, 153),
        VOLTA_INICIO("<", 249, 115, 22),
        MAGICA("%", 6, 182, 212),
        CHEGADA("F", 16, 185, 129);

        final String simbolo;
        final int r, g, b;

        TipoCasaVisual(String simbolo, int r, int g, int b) {
            this.simbolo = simbolo;
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }

    private static final int[] COR_NUMERO_RGB = {75, 90, 82};
    private static final int[] COR_VAZIA_RGB = {48, 54, 61};

    private static final String RESET = "[0m";
    private static final String NEGRITO = "[1m";
    // O console "Run" da IntelliJ nao interpreta o codigo ANSI de limpar tela
    // ([2J[H) — so as cores. Por isso, em vez de tentar limpar de
    // verdade, empurramos o quadro anterior pra fora da area visivel com
    // linhas em branco: funciona em qualquer console, IDE ou terminal real.
    private static final String LIMPAR_TELA = "\n".repeat(60);
    private static final Pattern ANSI = Pattern.compile("\\[[0-9;]*m");

    /** Limpa a tela e redesenha a grade de referência + status da rodada, tudo dentro de uma moldura só. */
    public void imprimirTela(List<Jogador> jogadores, int rodada, Jogador daVez, String ultimoEvento) {
        List<String> linhas = new ArrayList<>();
        linhas.add("SIMULACAO DO TABULEIRO (" + totalCasas + " casas)");
        linhas.add("");
        linhas.addAll(construirLinhasReferencia());
        linhas.add("");
        linhas.addAll(construirLinhasStatus(rodada, daVez, jogadores));
        linhas.add("");
        linhas.add(ultimoEvento);

        imprimirMoldura(linhas);
    }

    private void imprimirMoldura(List<String> linhas) {
        int largura = 0;
        for (String linha : linhas) {
            largura = Math.max(largura, tamanhoVisivel(linha));
        }

        StringBuilder saida = new StringBuilder();
        saida.append(LIMPAR_TELA);
        saida.append("┌").append("─".repeat(largura + 2)).append("┐\n");
        for (String linha : linhas) {
            int espacos = largura - tamanhoVisivel(linha);
            saida.append("│ ").append(linha).append(" ".repeat(espacos)).append(" │\n");
        }
        saida.append("└").append("─".repeat(largura + 2)).append("┘");

        System.out.println(saida);
    }

    private static int tamanhoVisivel(String linha) {
        return ANSI.matcher(linha).replaceAll("").length();
    }

    // ---- grade de referência (estática, sem jogadores) ----

    private List<String> construirLinhasReferencia() {
        List<String> linhas = new ArrayList<>();
        int[] ordem = gerarOrdemSerpentina();
        for (int inicio = 0; inicio < ordem.length; inicio += CASAS_POR_LINHA) {
            int fim = Math.min(inicio + CASAS_POR_LINHA, ordem.length);
            linhas.addAll(construirBlocoReferencia(ordem, inicio, fim));
        }
        linhas.add("");
        linhas.add(NEGRITO + "Legenda das casas:" + RESET);
        linhas.add(
                simboloLegenda(TipoCasaVisual.SORTE) + " Sorte    "
                        + simboloLegenda(TipoCasaVisual.PERDE_VEZ) + " Perde a vez    "
                        + simboloLegenda(TipoCasaVisual.SURPRESA) + " Surpresa    "
                        + simboloLegenda(TipoCasaVisual.VOLTA_INICIO) + " Volta ao inicio    "
                        + simboloLegenda(TipoCasaVisual.MAGICA) + " Magica    "
                        + simboloLegenda(TipoCasaVisual.CHEGADA) + " Chegada"
        );
        return linhas;
    }

    /** Ordem de impressão em serpentina (zig-zag), em blocos de {@link #CASAS_POR_LINHA}. */
    private int[] gerarOrdemSerpentina() {
        int[] ordem = new int[totalCasas];
        int indice = 0;
        int linha = 0;
        for (int base = 1; base <= totalCasas; base += CASAS_POR_LINHA, linha++) {
            int fimBloco = Math.min(base + CASAS_POR_LINHA - 1, totalCasas);
            if (linha % 2 == 0) {
                for (int n = base; n <= fimBloco; n++) {
                    ordem[indice++] = n;
                }
            } else {
                for (int n = fimBloco; n >= base; n--) {
                    ordem[indice++] = n;
                }
            }
        }
        return ordem;
    }

    private List<String> construirBlocoReferencia(int[] ordem, int inicio, int fim) {
        StringBuilder linhaTopo = new StringBuilder();
        StringBuilder linhaMeio = new StringBuilder();
        StringBuilder linhaBase = new StringBuilder();

        for (int i = inicio; i < fim; i++) {
            int numero = ordem[i];
            TipoCasaVisual tipo = tipoDaCasa(numero);
            String corBorda = cor(tipo.r, tipo.g, tipo.b);

            linhaTopo.append(corBorda).append("┌").append(RESET)
                    .append(cor(COR_NUMERO_RGB[0], COR_NUMERO_RGB[1], COR_NUMERO_RGB[2])).append(pad2(numero)).append(RESET)
                    .append(corBorda).append("───┐").append(RESET).append(" ");

            linhaMeio.append(corBorda).append("│  ").append(RESET);
            if (!tipo.simbolo.equals(" ")) {
                linhaMeio.append(corBorda).append(tipo.simbolo).append(RESET);
            } else {
                linhaMeio.append(" ");
            }
            linhaMeio.append("  ").append(corBorda).append("│").append(RESET).append(" ");

            linhaBase.append(corBorda).append("└─────┘").append(RESET).append(" ");
        }

        return List.of(linhaTopo.toString(), linhaMeio.toString(), linhaBase.toString());
    }

    private static String simboloLegenda(TipoCasaVisual tipo) {
        return cor(tipo.r, tipo.g, tipo.b) + tipo.simbolo + RESET;
    }

    // ---- status da rodada (dinâmico: barras + evento) ----

    private List<String> construirLinhasStatus(int rodada, Jogador daVez, List<Jogador> jogadores) {
        List<String> linhas = new ArrayList<>();
        String corDaVez = rgbJogador(daVez.getCor());
        linhas.add(NEGRITO + "=== RODADA " + rodada + " — vez de: " + RESET
                + corDaVez + daVez.getNome() + RESET + NEGRITO + " ===" + RESET);
        linhas.add("");

        StringBuilder legenda = new StringBuilder("Jogadores: ");
        for (Jogador jogador : jogadores) {
            legenda.append(rgbJogador(jogador.getCor())).append(jogador.getNome()).append(RESET).append("   ");
        }
        linhas.add(legenda.toString().stripTrailing());
        linhas.add("");

        linhas.add(construirRegua());
        for (Jogador jogador : jogadores) {
            linhas.add(construirBarra(jogador));
        }
        return linhas;
    }

    private String construirRegua() {
        StringBuilder base = new StringBuilder(" ".repeat(NOME_LARGURA + 1 + totalCasas + 2));
        for (int n = 0; n <= totalCasas; n += 10) {
            String texto = String.valueOf(n);
            int posicao = NOME_LARGURA + n;
            for (int i = 0; i < texto.length() && posicao + i < base.length(); i++) {
                base.setCharAt(posicao + i, texto.charAt(i));
            }
        }
        return base.toString();
    }

    private String construirBarra(Jogador jogador) {
        String corJogador = rgbJogador(jogador.getCor());
        StringBuilder linha = new StringBuilder();
        linha.append(corJogador).append(String.format("%-" + NOME_LARGURA + "s", jogador.getNome())).append(RESET).append("[");

        for (int casaNum = 1; casaNum <= totalCasas; casaNum++) {
            TipoCasaVisual tipo = tipoDaCasa(casaNum);
            if (tipo != TipoCasaVisual.NORMAL) {
                linha.append(cor(tipo.r, tipo.g, tipo.b)).append(MARCADOR_ESPECIAL).append(RESET);
            } else if (casaNum <= jogador.getPosicao()) {
                linha.append(corJogador).append("█").append(RESET);
            } else {
                linha.append(cor(COR_VAZIA_RGB[0], COR_VAZIA_RGB[1], COR_VAZIA_RGB[2])).append("░").append(RESET);
            }
        }

        linha.append("] ").append(jogador.getPosicao()).append("/").append(totalCasas);
        return linha.toString();
    }

    /** Classificação de exibição por número — mesmos números fixos usados em {@link #montarCasas}. */
    private TipoCasaVisual tipoDaCasa(int numero) {
        if (numero == totalCasas) {
            return TipoCasaVisual.CHEGADA;
        }
        if (numero == 5 || numero == 15 || numero == 30) {
            return TipoCasaVisual.SORTE;
        }
        if (numero == 13) {
            return TipoCasaVisual.SURPRESA;
        }
        if (numero == 10 || numero == 25 || numero == 38) {
            return TipoCasaVisual.PERDE_VEZ;
        }
        if (numero == 17 || numero == 27) {
            return TipoCasaVisual.VOLTA_INICIO;
        }
        if (numero == 20 || numero == 35) {
            return TipoCasaVisual.MAGICA;
        }
        return TipoCasaVisual.NORMAL;
    }

    private static String rgbJogador(String nomeCor) {
        return switch (nomeCor == null ? "" : nomeCor.trim().toLowerCase()) {
            case "azul" -> cor(59, 130, 246);
            case "verde" -> cor(34, 197, 94);
            case "amarelo" -> cor(234, 179, 8);
            case "roxo" -> cor(168, 85, 247);
            default -> cor(255, 255, 255);
        };
    }

    private static String pad2(int numero) {
        return String.format("%02d", numero);
    }

    private static String cor(int r, int g, int b) {
        return "[38;2;" + r + ";" + g + ";" + b + "m";
    }
}
