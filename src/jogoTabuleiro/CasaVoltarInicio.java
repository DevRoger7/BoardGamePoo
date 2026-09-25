package jogoTabuleiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Casa "volte ao início" (17, 27): o jogador escolhe um concorrente
 * para voltar à casa 0.
 */
public class CasaVoltarInicio extends Casa {

    public CasaVoltarInicio(int numero) {
        super(numero);
    }

    @Override
    public String aplicarEfeito(Jogador jogador, Jogo jogo) {
        List<Jogador> concorrentes = new ArrayList<>();
        for (Jogador outro : jogo.getJogadores()) {
            if (outro != jogador) {
                concorrentes.add(outro);
            }
        }
        if (concorrentes.isEmpty()) {
            return "";
        }

        System.out.println(jogador.getNome() + " caiu na casa " + getNumero()
                + "! Escolha um concorrente para voltar ao início:");
        for (int i = 0; i < concorrentes.size(); i++) {
            Jogador c = concorrentes.get(i);
            System.out.println("  " + (i + 1) + " - " + c.getNome()
                    + " (" + c.getCor() + ", casa " + c.getPosicao() + ")");
        }

        Jogador escolhido = lerEscolha(jogo.getScanner(), concorrentes);
        escolhido.setPosicao(0);
        return jogador.getNome() + " caiu na casa " + getNumero() + " e mandou "
                + escolhido.getNome() + " de volta para o início!";
    }

    /** Lê o número do concorrente, repetindo até receber uma opção válida. */
    private Jogador lerEscolha(Scanner scanner, List<Jogador> concorrentes) {
        while (true) {
            System.out.print("Opção (1-" + concorrentes.size() + "): ");
            String linha = scanner.nextLine().trim();
            try {
                int opcao = Integer.parseInt(linha);
                if (opcao >= 1 && opcao <= concorrentes.size()) {
                    return concorrentes.get(opcao - 1);
                }
            } catch (NumberFormatException e) {
                // cai na mensagem de opção inválida abaixo
            }
            System.out.println("Opção inválida.");
        }
    }
}
