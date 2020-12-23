package ui;

import Exceptions.*;
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
        try {
            System.out.println(modelo.comunicaOrdemDeTransporte());

        } catch (NoPaletesOnWaitingListException | NoPrateleirasAvailableException | NoRobotAvailableException e) {
            System.out.println(e.getMessage());
        }
    }

    public void notificarRecolhaDePaletes() {
        try {
            System.out.println(modelo.notificaRecolhaDePaletes());
        } catch (NoPaletesToCollectException | NoRobotAvailableException e) {
            System.out.println(e.getMessage());
        }
    }

    public void notificarEntregaDePaletes() {
        try {
            System.out.println(modelo.notificaEntregaDePaletes());
        } catch (NoPaletesToDeliverException e) {
            System.out.println(e.getMessage());
        }
    }

    public void consultarListagem() {
        try {
            modelo.consultaListagem();
        } catch (NoExistingPaletesException e) {
            System.out.println(e.getMessage());
        }
    }

}
