package jogoTabuleiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Menu principal do sistema, exibido a partir de {@link Main}.
 * Serve como ponto de conexão entre o console e o restante do jogo: a opção
 * "Iniciar Jogo" já monta {@link Tabuleiro}/{@link Jogo} corretamente e
 * passa a funcionar por completo assim que {@link Jogo#iniciar()} e o resto
 * da lógica forem implementados, sem precisar mexer aqui.
 */
public class Menu {

    private final Scanner scanner = new Scanner(System.in);

    public void exibir() {
        int opcao;
        do {
            System.out.println();
            System.out.println("===== JOGO DE TABULEIRO =====");
            System.out.println("1 - Iniciar Jogo");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");
            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> iniciarJogo();
                case 0 -> System.out.println("Ate a proxima!");
                default -> System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private void iniciarJogo() {
        List<Jogador> jogadores = cadastrarJogadores();
        if (jogadores.isEmpty()) {
            System.out.println("Nenhum jogador cadastrado.");
            return;
        }
        Tabuleiro tabuleiro = new Tabuleiro(Jogo.getCasaFinal());
        boolean modoDebug = confirmar("Ativar modo debug?");
        new Jogo(tabuleiro, jogadores, scanner, modoDebug).iniciar();
    }

    private List<Jogador> cadastrarJogadores() {
        List<Jogador> jogadores = new ArrayList<>();
        System.out.print("Quantos jogadores? ");
        int quantidade = lerInteiro();

        for (int i = 1; i <= quantidade; i++) {
            System.out.println("--- Jogador " + i + " ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Cor: ");
            String cor = scanner.nextLine();
            jogadores.add(escolherTipo(nome, cor));
        }
        return jogadores;
    }

    private Jogador escolherTipo(String nome, String cor) {
        System.out.println("Tipo de jogador:");
        System.out.println("1 - Normal");
        System.out.println("2 - Azarado");
        System.out.println("3 - Sortudo");
        System.out.print("Escolha: ");
        int tipo = lerInteiro();
        return switch (tipo) {
            case 2 -> new JogadorAzarado(nome, cor);
            case 3 -> new JogadorSortudo(nome, cor);
            default -> new JogadorNormal(nome, cor);
        };
    }

    private boolean confirmar(String pergunta) {
        System.out.print(pergunta + " (s/n): ");
        return scanner.nextLine().trim().toLowerCase().startsWith("s");
    }

    private int lerInteiro() {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um numero valido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}
