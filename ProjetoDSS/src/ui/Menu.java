package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static Scanner is = new Scanner(System.in);
    private List<String> opcoes;
    private int op;

    public Menu(String[] opcoes) {
        this.opcoes = Arrays.asList(opcoes);
        this.op = 0;
    }

    public Menu(List<String> opcoes) {
        this.opcoes = new ArrayList<>();
        this.opcoes.addAll(opcoes);
    }

    public void executa() {
        do {
            showMenu();
            this.op = lerOpcao();
        } while (this.op == -1);
    }

    private void showMenu() {
        System.out.println("\n <<< Menu >>>");
        for (int i = 0; i < this.opcoes.size(); i++) {
            System.out.println(i + 1 + " - " + this.opcoes.get(i));
        }
        System.out.println("0 - Sair");
    }

    private int lerOpcao() {
        int op;

        System.out.print("Opção: ");
        try {
            op = Integer.parseInt(is.nextLine());
            if (op < 0 || op > this.opcoes.size()) {
                System.out.println("Opção Inválida!");
                op = -1;
            }
        } catch (NumberFormatException e) { // Não foi inscrito um int
            op = -1;
            System.out.println("Opção Inválida!");
        }
        return op;
    }

    public int getOpcao() {
        return this.op;
    }
}
