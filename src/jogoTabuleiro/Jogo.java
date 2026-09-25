package jogoTabuleiro;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Controla o fluxo da partida: rodadas, turnos, movimentação dos jogadores
 * e verificação de vitória.
 */
public class Jogo {

    private static final int CASA_FINAL = 40;

    private final Tabuleiro tabuleiro;
    private final List<Jogador> jogadores;
    private final Dado dado;
    private final Scanner scanner;
    private final boolean modoDebug;
    private int rodada;

    public Jogo(Tabuleiro tabuleiro, List<Jogador> jogadores, Scanner scanner, boolean modoDebug) {
        this.tabuleiro = tabuleiro;
        this.jogadores = jogadores;
        this.dado = new Dado();
        this.scanner = scanner;
        this.modoDebug = modoDebug;
    }

    /**
     * Loop principal de rodadas, executado até existir um vencedor.
     */
    public void iniciar() {
        rodada = 0;
        tabuleiro.imprimirTelaInicial(jogadores);
        pausarParaContinuar();
        while (!existeVencedor()) {
            rodada++;
            for (Jogador jogador : jogadores) {
                executarTurno(jogador);
                if (existeVencedor()) {
                    break;
                }
            }
        }
        mostrarResultadoFinal();
    }

    /**
     * Executa o turno de um jogador: pula se ele perdeu a rodada, exibe posições,
     * obtém o movimento (dados ou entrada manual em modo debug), move o jogador,
     * aplica o efeito da casa e trata o duplo (joga de novo).
     */
    private void executarTurno(Jogador jogador) {
        // a instância em `jogadores` pode ser substituída pela casa surpresa,
        // então o jogador é sempre relido da lista pelo índice
        int indice = jogadores.indexOf(jogador);
        boolean repetir;

        do {
            repetir = false;
            Jogador atual = jogadores.get(indice);

            if (atual.isPerdeProximaRodada()) {
                atual.setPerdeProximaRodada(false);
                tabuleiro.imprimirTela(jogadores,  rodada, atual, atual.getNome() + " perdeu a vez nesta rodada.");
                pausarParaContinuar();
                return;
            }

            mostrarPosicoes();

            if (modoDebug) {
                int destino = lerCasaDebug(atual);
                atual.setPosicao(destino);
                repetir = false;
            } else {
                Lance lance = atual.rolarDados(dado);
                moverJogador(atual, lance.soma());
                repetir = lance.isDuplo();
            }
            atual.registrarJogada();

            Casa casa = tabuleiro.getCasa(atual.getPosicao());
            String evento = casa.aplicarEfeito(atual, this);

            // a casa pode ter trocado o tipo do jogador: `atual` pode estar obsoleto
            atual = jogadores.get(indice);

            tabuleiro.imprimirTela(jogadores, rodada, atual, evento);
            pausarParaContinuar();

            if (existeVencedor()) {
                return;
            }
        } while (repetir);
    }

    /**
     * Le em modo debug a casa exata para a qual o jogador deve ir (em vez da
     * soma dos dados) — permite testar o efeito de qualquer casa diretamente.
     * Repete o prompt até um numero valido entre 1 e {@link #CASA_FINAL}
     * (a casa 0 não existe no tabuleiro — {@link Tabuleiro#getCasa} indexa a
     * partir de 1).
     */
    private int lerCasaDebug(Jogador jogador) {
        while (true) {
            System.out.print(" [DEBUG] " + jogador.getNome() + " esta na casa " + jogador.getPosicao()
                    + ". Ir para qual casa (1-" + CASA_FINAL + ")? ");
            String entrada = scanner.nextLine().trim();
            try {
                int casa = Integer.parseInt(entrada);
                if (casa >= 1 && casa <= CASA_FINAL) {
                    return casa;
                }
                System.out.println(" Casa invalida. Escolha um numero entre 1 e " + CASA_FINAL + ".");
            } catch (NumberFormatException e) {
                System.out.println(" Entrada invalida. Digite um numero entre 1 e " + CASA_FINAL + ".");
            }
        }
    }

    /**
     * Pausa a execução até o jogador pressionar Enter — dá tempo de ler o
     * evento da rodada antes da tela ser limpa pelo próximo {@code imprimirTela}
     * (necessário porque o jogo é pass-and-play: vários jogadores compartilham
     * o mesmo console e cada tela é redesenhada do zero a cada turno).
     */
    private void pausarParaContinuar() {
        System.out.print(" Pressione Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Atualiza a posição do jogador (command).
     */
    private void moverJogador(Jogador jogador, int casas) {
        int novaPosicao = Math.min(jogador.getPosicao() + casas, CASA_FINAL);
        jogador.setPosicao(novaPosicao);
    }

    /**
     * Imprime a cor e a posição de cada jogador.
     */
    private void mostrarPosicoes() {
        StringBuilder linha = new StringBuilder();
        for (Jogador jogador : jogadores) {
            if (linha.length() > 0) {
                linha.append(", ");
            }
            String cor = jogador.getCor();
            String corCapitalizada = cor.substring(0, 1).toUpperCase() + cor.substring(1);
            linha.append(corCapitalizada).append(" na casa ").append(jogador.getPosicao());
        }
        System.out.println(linha);
    }

    /**
     * Consulta se algum jogador alcançou ou ultrapassou {@link #CASA_FINAL}.
     */
    private boolean existeVencedor() {
        for (Jogador jogador : jogadores) {
            if (jogador.getPosicao() >= CASA_FINAL) {
                return true;
            }
        }
        return false;
    }

    /**
     * Exibe o resultado final: vencedor, jogadas de cada um e posição final de todos.
     */
    private void mostrarResultadoFinal() {
        List<Jogador> ordenados = new ArrayList<>(jogadores);
        ordenados.sort((a, b) -> Integer.compare(b.getPosicao(), a.getPosicao()));

        Jogador vencedor = ordenados.get(0);

        System.out.println("=== FIM DE JOGO ===");
        System.out.println("Vencedor: " + vencedor.getNome()
                + " (" + vencedor.getQuantidadeJogadas() + " jogadas)");
        System.out.println();
        System.out.println("Posicao final de todos:");
        for (Jogador jogador : ordenados) {
            System.out.println(jogador.getNome()
                    + " - casa " + jogador.getPosicao()
                    + " - " + jogador.getQuantidadeJogadas() + " jogadas");
        }
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    /**
     * Substitui um jogador na lista por outro, preservando a posição dele
     * na ordem de turnos. Usado pela casa surpresa quando o jogador muda de
     * tipo (o objeto antigo não pode ter seu tipo alterado in-place, já que
     * o tipo é definido pela subclasse concreta).
     */
    public void substituirJogador(Jogador antigo, Jogador novo) {
        int indice = jogadores.indexOf(antigo);
        if (indice != -1) {
            jogadores.set(indice, novo);
        }
    }

    public Scanner getScanner() {
        return scanner;
    }

    public static int getCasaFinal() {
        return CASA_FINAL;
    }
}
