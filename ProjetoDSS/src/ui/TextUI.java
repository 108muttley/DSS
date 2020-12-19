package ui;

import Modelo.Sistema;

import java.util.Scanner;

public class TextUI {

    private Sistema modelo;

    private Menu menu;

    private Scanner sc;

    public TextUI() {
        String[] opcoes = {
                ""
        };
    }

    public void run() {
        do {
            menu.executa();
            switch (menu.getOpcao()) {
                case 1:
                    break;
            }
        } while (menu.getOpcao() != 0); // A opção 0 é usada para sair do menu.
        System.out.println("Até breve!...");
    }
}
