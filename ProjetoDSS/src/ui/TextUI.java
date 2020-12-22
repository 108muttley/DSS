package ui;

import Modelo.Sistema;
import Modelo.SistemaFacade;

import java.util.Scanner;

public class TextUI {

    private SistemaFacade modelo;

    private Menu menu;

    private Scanner sc;

    public TextUI() {
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

    public void comunicarCodigoQR() {
        System.out.print("Insira o nome do Produto: ");
        String prod = sc.nextLine();
        System.out.println(modelo.comunicaCodigoQR(prod));
    }

    public void comunicarOrdemDeTransporte() {
        System.out.println(modelo.comunicaOrdemDeTransporte());
    }

    public void notificarRecolhaDePaletes() {
        System.out.println(modelo.notificaRecolhaDePaletes());
    }

    public void notificarEntregaDePaletes() {
        System.out.println(modelo.notificaEntregaDePaletes());
    }

    public void consultarListagem() {
        String output;
        if ((output = modelo.consultaListagem()).isEmpty()) {
            System.out.println("Sistema:> Não existem Paletes no Sistema");
        } else
            System.out.println(output);
    }

}
