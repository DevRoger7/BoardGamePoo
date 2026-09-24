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
        // mover jogador; aplicar efeito da casa; tratar duplo (jogar de novo)
    }

    /**
     * Atualiza a posição do jogador (command).
     */
    private void moverJogador(Jogador jogador, int casas) {
        // TODO: atualizar posicao (command)
    }

    /**
     * Imprime a cor e a posição de cada jogador.
     */
    private void mostrarPosicoes() {
        // TODO: imprimir cor + posição de cada jogador
    }

    /**
     * Consulta se algum jogador alcançou ou ultrapassou {@link #CASA_FINAL}.
     */
    private boolean existeVencedor() {
        // TODO: query — algum jogador alcançou ou passou CASA_FINAL?
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

    public Scanner getScanner() {
        return scanner;
    }

    public static int getCasaFinal() {
        return CASA_FINAL;
    }
}
