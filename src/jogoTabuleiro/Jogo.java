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
    // avisos de vez perdida ainda não exibidos: saem na tela da próxima jogada de verdade
    private final List<String> avisosPendentes = new ArrayList<>();

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
        avisosPendentes.clear();
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
        // sem esta pausa o menu é redesenhado na hora e empurra o resultado pra fora da tela
        pausar(" Pressione Enter para voltar ao menu...");
    }

    /**
     * Executa o turno de um jogador: se ele perdeu a rodada, só registra o aviso
     * (sem tela nem pausa); senão exibe posições, obtém o movimento (dados ou
     * entrada manual em modo debug), move o jogador (repetindo a jogada enquanto
     * tirar duplo), aplica o efeito da casa onde parou e mostra a tela da jogada.
     */
    private void executarTurno(Jogador jogador) {
        // a instância em `jogadores` pode ser substituída pela casa surpresa,
        // então o jogador é sempre relido da lista pelo índice
        int indice = jogadores.indexOf(jogador);
        Jogador atual = jogadores.get(indice);

        // a vez perdida não ganha tela própria: como nada se mexe no tabuleiro,
        // ela parecia um Enter que "não funcionou" seguido de outro jogador
        // jogando duas vezes seguidas. O aviso sai junto da próxima jogada.
        if (atual.isPerdeProximaRodada()) {
            atual.setPerdeProximaRodada(false);
            avisosPendentes.add(atual.getNome() + " perdeu a vez na rodada " + rodada + ".");
            return;
        }

        mostrarPosicoes();

        int posicaoInicial = atual.getPosicao();
        String movimento;
        if (modoDebug) {
            atual.setPosicao(lerCasaDebug(atual));
            movimento = "[DEBUG] " + atual.getNome() + " foi da casa " + posicaoInicial
                    + " para a casa " + atual.getPosicao() + ".";
        } else {
            List<String> lances = new ArrayList<>();
            Lance lance;
            do {
                lance = atual.rolarDados(dado);
                lances.add(lance.dado1() + "+" + lance.dado2() + (lance.isDuplo() ? " (duplo)" : ""));
                moverJogador(atual, lance.soma());
            } while (lance.isDuplo() && atual.getPosicao() < CASA_FINAL);
            movimento = atual.getNome() + " tirou " + String.join(", ", lances)
                    + " e foi da casa " + posicaoInicial + " para a casa " + atual.getPosicao() + ".";
        }
        atual.registrarJogada();

        // o efeito vale só para a casa onde o jogador parou
        Casa casa = tabuleiro.getCasa(atual.getPosicao());
        String evento = casa.aplicarEfeito(atual, this);

        // a casa pode ter trocado o tipo do jogador: `atual` pode estar obsoleto
        atual = jogadores.get(indice);

        List<String> eventos = new ArrayList<>(avisosPendentes);
        avisosPendentes.clear();
        eventos.add(movimento);
        if (!evento.isEmpty()) {
            eventos.add(evento);
        }
        tabuleiro.imprimirTela(jogadores, rodada, atual, eventos);
        pausarParaContinuar();
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
        pausar(" Pressione Enter para continuar...");
    }

    private void pausar(String mensagem) {
        System.out.print(mensagem);
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
