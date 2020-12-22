package Modelo;

import database.PaleteDAO;
import database.PrateleiraDAO;
import database.RobotDAO;
import ui.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sistema implements SistemaFacade {
    private Map<String, Palete> paletes;
    private Map<String, Prateleira> prateleiras;
    private Map<String, Robot> robots;
    private List<String> paletesWaitingForDelivering;
    private List<GPS> mapa;


    public Sistema() {
        this.prateleiras = PrateleiraDAO.getInstance();
        this.paletes = PaleteDAO.getInstance();
        this.robots = RobotDAO.getInstance();
        PrateleiraDAO.povoa();
        RobotDAO.povoa();

        List<String> paletesReservadas = new ArrayList<>();
        for (String s : this.robots.keySet()) {
            if (this.robots.get(s).getCodPalete() != null)
                paletesReservadas.add(this.robots.get(s).getCodPalete());
        }

        this.paletesWaitingForDelivering = new ArrayList<>();
        if (this.paletes.size() > 0)
            for (String s : this.paletes.keySet()) {
                if (this.paletes.get(s).getLoc().equals("0-0") &&
                        !paletesReservadas.contains(s)) { // se estiver na receçao, adiciona 0-0 - receção
                    this.paletesWaitingForDelivering.add(s);
                }
            }
        else System.out.println("Não há paletes");
        this.mapa = GPS.criaMapa();
    }

    public String comunicaCodigoQR(String produto) {
        String cod;
        try {
            do {
                cod = LeitorQrCode.geraCodigoQR();
            } while (this.paletes.get(cod) != null);

            this.paletes.put(cod, new Palete(cod, "0-0", produto));
            this.paletesWaitingForDelivering.add(cod);

            return ("Sistema:> Código Gerado com Sucesso! : " + cod);
        } catch (Exception e) {
            e.printStackTrace();
            return "Não foi possível gerar o código :(";
        }
    }

    public String comunicaOrdemDeTransporte() {
        if (this.paletesWaitingForDelivering.size() > 0) {
            List<String> menuOptions = new ArrayList<>();
            for (String s : this.paletesWaitingForDelivering) {
                menuOptions.add(s + ": " + this.paletes.get(s).getM());
            }
            Menu menu = new Menu(menuOptions);
            int i;
            menu.executa();
            i = menu.getOpcao();
            if (i <= 0) {
                return "Opção Inválida!"; // nunca deverá entrar aqui
            }

            String produtoATransportar = this.paletesWaitingForDelivering.get(i - 1);
            String prateleira = getPrateleiraLivre();
            if (prateleira.isEmpty()) {
                return ("Sistema:> Não existem Prateleiras Disponíveis!");
            }
            Prateleira p = this.prateleiras.get(prateleira);
            p.setDisponibilidade(false);
            this.prateleiras.put(p.getCodPrateleira(), p); // atualizar prateleira (disponibilidade - false)
            return comunicaRobotMaisProximo(produtoATransportar, prateleira);
        }
        return ("Sistema:> Não existem Paletes para Transporte!");
    }


    // ver qual o robot mais proximo
    // enviar-lhe o percurso de ir buscar a palete + o de entregar
    // robot comunicar que iniciou a entrega
    public String comunicaRobotMaisProximo(String codigoPalete, String prateleiraDestino) { // robot -> localizacao da palete -> destino
        int minimo = 99;
        int atual;
        String aux = this.paletes.get(codigoPalete).getLoc(); // localização da palete
        GPS gpsPalete;
        if (aux.startsWith("R")) { // buscar a localização GPS da palete
            gpsPalete = this.robots.get(aux).getLocalizacao().clone();
            System.out.println("não devia entrar aqui");
        } else {
            gpsPalete = this.prateleiras.get(aux).getLocalizacao().clone();
        }

        String robotEscolhido = "";
        // Escolher o Robot mais próximo
        for (String s : this.robots.keySet()) {
            if (this.robots.get(s).isAvailable()) {
                GPS inicio = this.robots.get(s).getLocalizacao();
                if ((atual = GPS.criaCaminho(this.mapa, inicio, gpsPalete).getDistancia()) < minimo) { // encontrou novo robot mais proximo
                    robotEscolhido = s;
                    minimo = atual;
                }
            }
        }
        if (robotEscolhido.isEmpty()) {
            Prateleira p = this.prateleiras.get(prateleiraDestino);
            p.setDisponibilidade(true);
            this.prateleiras.put(prateleiraDestino, p); // voltar a atualizar prateleira
            return ("Sistema:> Não existe nenhum robot disponível!");
        }
        Robot escolhido = this.robots.get(robotEscolhido);
        escolhido.setLivre(false);
        escolhido.setCodPalete(codigoPalete); // robot fica "reservado"
        this.robots.put(robotEscolhido, escolhido); // atualizar robot (disponibilidade - false)
        this.paletesWaitingForDelivering.remove(codigoPalete); // remover palete da lista à espera de ser entregue

        return ("Sistema:> Robot Escolhido: " + robotEscolhido);
    }

    public String getPrateleiraLivre() { // busca uma prateleira livre
        String pEscolhida = "";
        int minimo = 99;
        int valor;
        for (String s : this.prateleiras.keySet()) {
            if (this.prateleiras.get(s).isAvailable() && !s.equals("0-0") && !s.equals("e-e")) {
                if ((valor = GPS.criaCaminho(this.mapa, new GPS(0, 0), this.prateleiras.get(s).getLocalizacao()).getDistancia()) < minimo) {
                    minimo = valor;
                    pEscolhida = s;
                }
            }
        }
        return pEscolhida;
    }

    public String getPrateleiraParaArmazenamento() {
        for (String s : this.prateleiras.keySet()) {
            if (!this.prateleiras.get(s).isAvailable() && this.prateleiras.get(s).getCodPalete() == null && !s.equals("0-0") && !s.equals("e-e"))
                return s;
        }
        return null;
    }

    public String consultaListagem() {
        StringBuilder output = new StringBuilder();
        for (String s : this.paletes.keySet()) {
            Palete p = this.paletes.get(s);
            GPS coordenadas;
            if (p.getLoc().startsWith("R"))
                coordenadas = this.robots.get(p.getLoc()).getLocalizacao().clone();
            else
                coordenadas = this.prateleiras.get(p.getLoc()).getLocalizacao().clone();
            output.append("Palete { código: ")
                    .append(s)
                    .append(", material: ")
                    .append(p.getM())
                    .append(", localização: ")
                    .append(p.getLoc()).append(", GPS: ")
                    .append(coordenadas.toString())
                    .append(" }\n");
        }
        return "Sistema:>\n" + output;
    }

    public String getRobotReservado(String codPalete) {
        String robotEscolhido = "";

        for (String s : this.robots.keySet()) {
            if (this.robots.get(s).getCodPalete().equals(codPalete)) return s;
        }
        return robotEscolhido;
    }

    public String notificaRecolhaDePaletes() {
        // verificar se existe alguma palete para recolher
        // condição : tem de estar na base de dados, com localização 0 0, e não pode estar na lista
        List<String> paletesARecolher = new ArrayList<>();
        for (String s : this.paletes.keySet()) {
            if (this.paletes.get(s).getLoc().equals("0-0") && !this.paletesWaitingForDelivering.contains(s)) {
                paletesARecolher.add(s + ": " + this.paletes.get(s).getM());
            }
        }
        if (paletesARecolher.isEmpty()) {
            return "Sistema:> Não há Paletes para Recolher!"; // verifica se existe alguma palete à espera de transporte
        }
        Menu menuDePaletes = new Menu(paletesARecolher);
        menuDePaletes.executa();
        int escolha = menuDePaletes.getOpcao();

        if (escolha <= 0)
            return "Opção Inválida"; // não deve entrar aqui
        String aux = paletesARecolher.get(escolha - 1);
        String palete_a_recolher = aux.substring(0, aux.indexOf(":"));

        Robot r = this.robots.get(getRobotReservado(palete_a_recolher));
        if (!r.isAvailable() && (r.getCodPalete().equals(palete_a_recolher))) {

            Palete p = this.paletes.get(palete_a_recolher);
            String output = r.doRecolha(GPS.criaCaminho(this.mapa, r.getLocalizacao(), new GPS(0, 0)), p);

            p.setLoc(r.getCod()); // localização da palete passa a ser o robot
            this.paletes.put(palete_a_recolher, p); // atualizar palete (localização)
            this.robots.put(r.getCod(), r); // atualizar robot (localização + palete)

            return output;
        }

        return "Sistema:> Não há robots disponíveis!";
    }

    public String notificaEntregaDePaletes() {
        List<String> paletesAEntregar = new ArrayList<>();
        for (String s : this.paletes.keySet()) {
            Palete p = this.paletes.get(s);
            if (p.getLoc().startsWith("R")) {
                paletesAEntregar.add(p.getLoc() +
                        ": " + p.getCodPalete() +
                        ", " + p.getM());
            }
        }
        if (paletesAEntregar.isEmpty()) {
            return ("Sistema:> Não há paletes para entregar");
        }
        Menu menuDePaletes = new Menu(paletesAEntregar);
        menuDePaletes.executa();
        int escolha = menuDePaletes.getOpcao();

        if (escolha <= 0)
            return "Opção Inválida!"; // não deverá entrar aqui

        String aux = paletesAEntregar.get(escolha - 1);
        String codPalete = aux.substring(aux.indexOf(" ") + 1, aux.indexOf(","));

        String codprateleira = getPrateleiraParaArmazenamento();
        Prateleira prateleira = this.prateleiras.get(codprateleira);
        Robot robot = this.robots.get(this.paletes.get(codPalete).getLoc());

        String output = robot.doDelivering(GPS.criaCaminho(this.mapa, robot.getLocalizacao().clone(), prateleira.getLocalizacao().clone()));
        Palete palete = this.paletes.get(codPalete);
        palete.setLoc(codprateleira);

        prateleira.setCodPalete(codPalete);
        this.paletes.put(codPalete, palete); // atualizar palete (localização)
        this.robots.put(robot.getCod(), robot); // atualizar robot (palete, localizaçao, disponibilidade)
        this.prateleiras.put(codprateleira, prateleira); // atualizar prateleira (palete)
        return output;
    }
}
