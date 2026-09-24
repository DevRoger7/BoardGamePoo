package jogoTabuleiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Menu principal do sistema, exibido a partir de {@link Main}.
 * Serve como ponto de conexão entre o console e o restante do jogo: a opção
 * "Iniciar Jogo" já monta {@link Tabuleiro}/{@link Jogo} corretamente e
 * passa a funcionar por completo assim que {@link Jogo#iniciar()} e o resto
 * da lógica forem implementados, sem precisar mexer aqui.
 */
public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    // Paleta do menu (Fliperama). A moldura em si usa ConsoleUI.COR_MOLDURA_PADRAO
    // (o mesmo ciano do tabuleiro) — aqui só as cores específicas de conteúdo.
    private static final String COR_TITULO = ConsoleUI.COR_MOLDURA_PADRAO; // ciano, igual a moldura
    private static final String COR_ATIVO  = ConsoleUI.cor(255, 225, 77);   // amarelo
    private static final String COR_DIM    = ConsoleUI.cor(95, 106, 107);   // cinza-azulado
    private static final String COR_BRANCO = ConsoleUI.cor(255, 255, 255);  // caixa de destaque do título

    public void exibir() {
        int opcao;
        do {
            desenharMenuPrincipal();
            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> iniciarJogo();
                case 0 -> System.out.println(COR_DIM + "Ate a proxima!" + ConsoleUI.RESET);
                default -> System.out.println(COR_DIM + "Opcao invalida." + ConsoleUI.RESET);
            }
        } while (opcao != 0);
    }

    /**
     * Cabeçalho padrão de toda tela do menu: título com o mesmo contorno
     * branco em todas elas (nenhuma moldura ao redor da tela inteira) — pra
     * manter o mesmo estilo em qualquer navegação (menu -> cadastro -> ...).
     */
    private static List<String> cabecalho(String titulo) {
        List<String> linhas = new ArrayList<>();
        linhas.add("");
        for (String linhaTitulo : ConsoleUI.construirCaixaTitulo(
                letraEspacada(titulo), COR_TITULO + ConsoleUI.NEGRITO, COR_BRANCO)) {
            linhas.add(" " + linhaTitulo);
        }
        linhas.add("");
        return linhas;
    }

    /** Insere um espaço entre cada letra, pra dar mais presença visual ao título. */
    private static String letraEspacada(String texto) {
        return String.join(" ", texto.split(""));
    }

    private void desenharMenuPrincipal() {
        List<String> linhas = cabecalho("JOGO DE TABULEIRO");
        linhas.add("   " + COR_ATIVO + "▸ 1   Iniciar Jogo" + ConsoleUI.RESET);
        linhas.add("   " + COR_DIM + "▸ 0   Sair" + ConsoleUI.RESET);
        linhas.add("");
        ConsoleUI.imprimirTela(linhas);
        System.out.print(" Escolha uma opcao: ");
    }

    private void iniciarJogo() {
        List<Jogador> jogadores = cadastrarJogadores();
        if (jogadores.isEmpty()) {
            System.out.println(COR_DIM + "Nenhum jogador cadastrado." + ConsoleUI.RESET);
            return;
        }
        Tabuleiro tabuleiro = new Tabuleiro(Jogo.getCasaFinal());
        boolean modoDebug = confirmar("Ativar modo debug?");
        new Jogo(tabuleiro, jogadores, scanner, modoDebug).iniciar();
    }

    private record NomeCor(String nome, String cor) {
    }

    private List<Jogador> cadastrarJogadores() {
        ConsoleUI.imprimirTela(cabecalho("CADASTRO DE JOGADORES"));
        System.out.print(" Quantos jogadores? ");
        int quantidade = lerInteiro();

        List<NomeCor> dados = new ArrayList<>();
        for (int i = 1; i <= quantidade; i++) {
            List<String> linhas = cabecalho("CADASTRO - JOGADOR " + i + "/" + quantidade);
            linhas.add("   " + barraProgresso(i - 1, quantidade) + " progresso do cadastro");
            linhas.add("");
            ConsoleUI.imprimirTela(linhas);

            System.out.print(" Nome: ");
            String nome = scanner.nextLine();
            System.out.print(" Cor : ");
            String cor = scanner.nextLine();
            dados.add(new NomeCor(nome, cor));
        }

        List<Jogador> jogadores = sortearJogadores(dados);
        List<String> resultado = cabecalho("TIPOS SORTEADOS");
        for (Jogador jogador : jogadores) {
            resultado.add("   " + COR_DIM + "-" + ConsoleUI.RESET + " " + jogador.getNome()
                    + " " + COR_DIM + "->" + ConsoleUI.RESET + " "
                    + COR_ATIVO + jogador.getClass().getSimpleName() + ConsoleUI.RESET);
        }
        resultado.add("");
        ConsoleUI.imprimirTela(resultado);
        return jogadores;
    }

    /** Barra de progresso em blocos ASCII (■ concluído / □ restante), ex.: "[■ ■ □ □]". */
    private String barraProgresso(int concluidos, int total) {
        StringBuilder blocos = new StringBuilder();
        for (int i = 0; i < total; i++) {
            blocos.append(i < concluidos ? "■" : "□");
            if (i < total - 1) {
                blocos.append(' ');
            }
        }
        return "[" + COR_ATIVO + blocos + ConsoleUI.RESET + "]";
    }

    /**
     * Sorteia o tipo de cada jogador aleatoriamente, repetindo o sorteio até
     * garantir pelo menos 2 tipos diferentes na partida (regra do enunciado).
     */
    private List<Jogador> sortearJogadores(List<NomeCor> dados) {
        List<Jogador> jogadores;
        do {
            jogadores = new ArrayList<>();
            for (NomeCor dado : dados) {
                jogadores.add(sortearTipo(dado.nome(), dado.cor()));
            }
        } while (dados.size() > 1 && tiposDistintos(jogadores) < 2);
        return jogadores;
    }

    private Jogador sortearTipo(String nome, String cor) {
        return switch (random.nextInt(3)) {
            case 1 -> new JogadorAzarado(nome, cor);
            case 2 -> new JogadorSortudo(nome, cor);
            default -> new JogadorNormal(nome, cor);
        };
    }

    private long tiposDistintos(List<Jogador> jogadores) {
        Set<Class<?>> tipos = jogadores.stream().map(Jogador::getClass).collect(Collectors.toSet());
        return tipos.size();
    }

    private boolean confirmar(String pergunta) {
        System.out.print(" " + pergunta + " " + COR_ATIVO + "(s/n)" + ConsoleUI.RESET + ": ");
        return scanner.nextLine().trim().toLowerCase().startsWith("s");
    }

    private int lerInteiro() {
        while (!scanner.hasNextInt()) {
            System.out.print(COR_DIM + "Digite um numero valido: " + ConsoleUI.RESET);
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}
