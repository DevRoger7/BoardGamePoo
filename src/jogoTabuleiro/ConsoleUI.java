package jogoTabuleiro;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utilitário de desenho de console compartilhado por Tabuleiro e Menu:
 * cores ANSI, moldura em box-drawing e a técnica de "limpar tela" por
 * empurrão de linhas. Fonte única para que as duas telas do jogo usem
 * exatamente a mesma moldura.
 */
public final class ConsoleUI {

    private ConsoleUI() {}

    public static final String RESET = "\u001B[0m";
    public static final String NEGRITO = "\u001B[1m";

    private static final Pattern ANSI = Pattern.compile("\u001B\\[[0-9;]*m");

    // Empurra o quadro anterior pra fora da área visível — funciona em
    // qualquer console (IDE, terminal real), diferente de \u001B[2J\u001B[H
    // (que a IntelliJ ignora). Empurrar só a altura exata do quadro anterior
    // não basta: se o painel do console for mais alto que isso (comum numa
    // IDE maximizada), o quadro antigo continua visível junto do novo. Por
    // isso o número de linhas empurradas é fixo e generoso — bem maior que
    // qualquer tela do jogo e que a altura visível de um console comum — pra
    // garantir que só uma tela apareça por vez. No primeiro quadro da
    // execução (nada pra esconder ainda) não empurra nada, pra já nascer no
    // topo em vez de com um vão em branco antes.
    private static boolean primeiroQuadro = true;
    private static final int RESPIRO_ENTRE_TELAS = 40;

    /** Cor de marca do jogo — usada na moldura do tabuleiro e do menu. */
    public static final String COR_MOLDURA_PADRAO = cor(53, 224, 255); // ciano #35E0FF

    public static String cor(int r, int g, int b) {
        return "\u001B[38;2;" + r + ";" + g + ";" + b + "m";
    }

    public static int tamanhoVisivel(String linha) {
        return ANSI.matcher(linha).replaceAll("").length();
    }

    /** Limpa a tela e desenha uma moldura ao redor das linhas, na cor informada. */
    public static void imprimirMoldura(List<String> linhas, String corMoldura) {
        int largura = 0;
        for (String linha : linhas) {
            largura = Math.max(largura, tamanhoVisivel(linha));
        }

        List<String> caixa = new ArrayList<>();
        caixa.add(corMoldura + "┌" + "─".repeat(largura + 2) + "┐" + RESET);
        for (String linha : linhas) {
            int espacos = largura - tamanhoVisivel(linha);
            caixa.add(corMoldura + "│ " + RESET + linha + " ".repeat(espacos) + corMoldura + " │" + RESET);
        }
        caixa.add(corMoldura + "└" + "─".repeat(largura + 2) + "┘" + RESET);

        imprimirTela(caixa);
    }

    /** Sobrecarga sem cor explícita: usa a cor de moldura padrão do jogo (ciano). */
    public static void imprimirMoldura(List<String> linhas) {
        imprimirMoldura(linhas, COR_MOLDURA_PADRAO);
    }

    /**
     * Limpa a tela e imprime as linhas informadas sem nenhuma moldura ao redor —
     * usado pelas telas do menu, que só destacam o título (ver
     * {@link #construirCaixaTitulo}) em vez de envolver a tela inteira numa caixa.
     */
    public static void imprimirTela(List<String> linhas) {
        StringBuilder saida = new StringBuilder();
        if (!primeiroQuadro) {
            saida.append("\n".repeat(RESPIRO_ENTRE_TELAS));
        }
        saida.append(String.join("\n", linhas));

        System.out.println(saida);
        primeiroQuadro = false;
    }

    /**
     * Monta (sem imprimir) uma caixinha de destaque de uma linha só ao redor
     * de {@code texto}, com barras duplas "‖" nas laterais — pra dar ênfase a
     * um título dentro de uma moldura maior. Retorna as 3 linhas prontas
     * (topo/conteúdo/base) pra serem inseridas na lista passada a
     * {@link #imprimirMoldura}; a largura é calculada pelo próprio texto, o
     * que evita o desalinhamento de bordas duplas feitas "no olho".
     */
    public static List<String> construirCaixaTitulo(String texto, String corTexto, String corCaixa) {
        String conteudo = corTexto + texto + RESET;
        int largura = tamanhoVisivel(conteudo);
        return List.of(
                corCaixa + "┌" + "═".repeat(largura + 2) + "┐" + RESET,
                corCaixa + "‖ " + RESET + conteudo + corCaixa + " ‖" + RESET,
                corCaixa + "└" + "═".repeat(largura + 2) + "┘" + RESET
        );
    }
}
