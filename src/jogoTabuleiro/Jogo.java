package jogoTabuleiro;

import java.util.List;
import java.util.Scanner;

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
        // TODO: loop principal de rodadas até existir um vencedor
    }

    /**
     * Executa o turno de um jogador: pula se ele perdeu a rodada, exibe posições,
     * obtém o movimento (dados ou entrada manual em modo debug), move o jogador,
     * aplica o efeito da casa e trata o duplo (joga de novo).
     */
    private void executarTurno(Jogador jogador) {
        // TODO: pular turno se perdeProximaRodada; exibir posições;
        // obter movimento (rolarDados ou entrada manual em modoDebug);
        // mover jogador; aplicar efeito da casa (String evento = casa.aplicarEfeito(jogador, this));
        // tabuleiro.imprimirTela(jogadores, rodada, jogador, evento); pausarParaContinuar();
        // tratar duplo (jogar de novo)
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
        // TODO: vencedor, jogadas de cada um, posição final de todos
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
