package ui;

import Modelo.Sistema;

import java.util.Scanner;

public class TextUI {

    private Sistema modelo;

    private Menu menu;

    private Scanner sc;

    private Boolean logged;

    public TextUI() {
        this.logged = false;
        String[] opcoes = {
                "Comunicar código QR", // 1
                "Sistema: Comunicar Ordem de Transporte", // 2
                "Notificar Recolha de Paletes", // 3
                "Notificar Entrega de Paletes", // 4
                "Gestor: Consultar listagem de localizações" //5
        };
        this.menu = new Menu(opcoes);
        this.modelo = new Sistema();
        this.sc = new Scanner(System.in);
    }

    public void run() {
        do {
            menu.executa();
            switch (menu.getOpcao()) {
                case 1:
                    comunicarCodigoQR();
                    break;
                case 2:
                    comunicarOrdemDeTransporte();
                    break;
                case 3:
                    notificarRecolhaDePaletes();
                    break;
                case 4:
                    notificarEntregaDePaletes();
                    break;
                case 5:
                    consultarListagem();
                    break;
            }
        } while (menu.getOpcao() != 0); // A opção 0 é usada para sair do menu.
        System.out.println("Saindo ...");
    }

    // to do
    public void comunicarCodigoQR() {
        System.out.print("Insira o nome do Produto: ");
        String prod = sc.nextLine();
        if (!modelo.comunicaCodigoQR(prod)) {
            System.out.println("Something went wrong");
        }
    }

    public void comunicarOrdemDeTransporte() {
        if (!modelo.comunicaOrdemDeTransporte()) {
            System.out.println("Something went wrong");
        }
    }

    public void notificarRecolhaDePaletes(){}

    public void notificarEntregaDePaletes(){}

    public void consultarListagem(){
        if (!modelo.consultaListagem()) {
            System.out.println("Something went wrong");
        }
    }
}
