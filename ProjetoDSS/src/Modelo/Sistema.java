package Modelo;

import database.PaleteDAO;
import database.PrateleiraDAO;
import database.RobotDAO;
import ui.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Sistema {
    private Map<String, Palete> paletes;
    private Map<String, Prateleira> prateleiras;
    private Map<String, Robot> robots;


    private List<String> paletesWaitingForDelivering;
    private LeitorQrCode leitor;
    private List<GPS> mapa;
    //private Map<GPS, List<GPS>> mapa;


    public Sistema() {
        this.prateleiras = PrateleiraDAO.getInstance();
        this.paletes = PaleteDAO.getInstance();
        this.robots = RobotDAO.getInstance();
        // if(this.prateleiras.isEmpty())
        PrateleiraDAO.povoa();
        RobotDAO.povoa();


        this.paletesWaitingForDelivering = new ArrayList<>();
        if (this.paletes.size() > 0)
            for (String s : this.paletes.keySet()) {
                if (this.paletes.get(s).getLoc().equals("0-0")) { // se estiver na receçao, adiciona 0-0 - receção
                    this.paletesWaitingForDelivering.add(s);
                }
            }
        else System.out.println("Não há paletes");
        this.leitor = new LeitorQrCode(this);
        this.mapa = GPS.criaMapa();
        //this.mapa = GPS.criaMapa();
    }

    public boolean comunicaCodigoQR(String produto) {
        String cod;
        try {
            do {
                cod = geraCodigoQR();
            } while (this.paletes.get(cod) != null);

            this.paletes.put(cod, new Palete(cod, "0-0", produto));
            this.paletesWaitingForDelivering.add(cod);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean comunicaOrdemDeTransporte() {
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
                return false;
            }

            String produtoATransportar = this.paletesWaitingForDelivering.get(i - 1);
            String prateleira = getPrateleiraLivre();
            Prateleira p = this.prateleiras.get(prateleira);
            p.setDisponibilidade(false);
            this.prateleiras.put(p.getCodPrateleira(), p); // atualizar prateleira (disponibilidade - false)
            return comunicaRobotMaisProximo(produtoATransportar, prateleira);
        }
        return false;
    }


    // ver qual o robot mais proximo
    // enviar-lhe o percurso de ir buscar a palete + o de entregar
    // robot comunicar que iniciou a entrega
    public boolean comunicaRobotMaisProximo(String codigoPalete, String prateleiraDestino) { // robot -> localizacao da palete -> destino
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
        System.out.println(robotEscolhido);
        if (robotEscolhido.isEmpty()) {
            System.out.println("Não há robot disponível");
            Prateleira p = this.prateleiras.get(prateleiraDestino);
            p.setDisponibilidade(true);
            this.prateleiras.put(prateleiraDestino, p); // voltar a atualizar prateleira
            return false;
        }
        Robot escolhido = this.robots.get(robotEscolhido);
        escolhido.setLivre(false);
        this.robots.put(robotEscolhido, escolhido); // atualizar robot (disponibilidade - false)
        this.paletesWaitingForDelivering.remove(codigoPalete); // remover palete da lista à espera de ser entregue

        return true;
    }

    public String getPrateleiraLivre() { // busca uma prateleira livre
        for (String s : this.prateleiras.keySet()) {
            if (this.prateleiras.get(s).isAvailable() && !s.equals("0-0") && !s.equals("e-e")) return s;
        }
        return null;
    }

    public String getPrateleiraParaArmazenamento() {
        for (String s : this.prateleiras.keySet()) {
            if (!this.prateleiras.get(s).isAvailable() && this.prateleiras.get(s).getCodPalete().equals("null") && !s.equals("0-0") && !s.equals("e-e"))
                return s;
        }
        return null;
    }

    public boolean consultaListagem() {
        for (String s : this.paletes.keySet()) {
            Palete p = this.paletes.get(s);
            GPS coordenadas;
            if (p.getLoc().startsWith("R"))
                coordenadas = this.robots.get(p.getLoc()).getLocalizacao().clone();
            else
                coordenadas = this.prateleiras.get(p.getLoc()).getLocalizacao().clone();
            System.out.println("Palete { código: " + s +
                    ", material: " + p.getM() +
                    ", localização: " + p.getLoc() +
                    ", GPS: " + coordenadas.toString() +
                    " }");
        }
        return true;
    }

    public String getRobotMaisProximoComDisponibilidade(GPS destino, boolean disponibilidade) {
        String robotEscolhido = "";
        int maximo = 99;
        int atual;
        // Escolher o Robot mais próximo com uma certa disponibilidade
        for (String s : this.robots.keySet()) {
            if (this.robots.get(s).isAvailable() == disponibilidade) {
                GPS inicio = this.robots.get(s).getLocalizacao();
                if ((atual = GPS.criaCaminho(this.mapa, inicio, destino).getDistancia()) < maximo) { // encontrou novo robot mais proximo
                    robotEscolhido = s;
                    maximo = atual;

                }
            }
        }
        return robotEscolhido;
    }

    public String getRobotMaisProximoComDisponibilidade(GPS destino, boolean disponibilidade, String codPalete) {
        String robotEscolhido = "";
        int maximo = 99;
        int atual;
        // Escolher o Robot mais próximo com uma certa disponibilidade
        for (String s : this.robots.keySet()) {
            if (this.robots.get(s).isAvailable() == disponibilidade && ((codPalete == null && this.robots.get(s).getCodPalete().equals("null")) || this.robots.get(s).getCodPalete().equals(codPalete))) {
                GPS inicio = this.robots.get(s).getLocalizacao();
                if ((atual = GPS.criaCaminho(this.mapa, inicio, destino).getDistancia()) < maximo) { // encontrou novo robot mais proximo
                    robotEscolhido = s;
                    maximo = atual;

                }
            }
        }
        return robotEscolhido;
    }

    public boolean notificaRecolhaDePaletes() {
        // verificar se existe alguma palete para recolher
        // condição : tem de estar na base de dados, com localização 0 0, e não pode estar na lista
        // boolean condicao = false;
        // String palete_a_recolher = "";
        List<String> paletesARecolher = new ArrayList<>();
        for (String s : this.paletes.keySet()) {
            if (this.paletes.get(s).getLoc().equals("0-0") && !this.paletesWaitingForDelivering.contains(s)) {
                //palete_a_recolher = s;
                paletesARecolher.add(s + ": " + this.paletes.get(s).getM());
                //condicao = true;
                //break;
            }
        }
        if (paletesARecolher.isEmpty()) {
            System.out.println("Não há paletes para recolher");
            return false; // verifica se existe alguma palete à espera de transporte
        }
        Menu menuDePaletes = new Menu(paletesARecolher);
        menuDePaletes.executa();
        int escolha = menuDePaletes.getOpcao();

        if (escolha <= 0)
            return false;
        String aux = paletesARecolher.get(escolha - 1);
        String palete_a_recolher = aux.substring(0, aux.indexOf(":"));


        GPS rececao = new GPS(0, 0);
        Robot r = this.robots.get(getRobotMaisProximoComDisponibilidade(rececao, false, null));
        if (!r.isAvailable() && (r.getCodPalete() == null || r.getCodPalete().equals("null"))) {
            System.out.println("deu bem");


            Palete p = this.paletes.get(palete_a_recolher);
            r.doRecolha(GPS.criaCaminho(this.mapa, r.getLocalizacao(), new GPS(0, 0)), p);
            p.setLoc(r.getCod()); // localização da palete passa a ser o robot
            this.paletes.put(palete_a_recolher, p); // atualizar palete (localização)

            //r.setPalete(p);
            //r.setLocalizacao(rececao);
            this.robots.put(r.getCod(), r); // atualizar robot (localização + palete)

            return true;
        }

        return false;
    }

    public boolean notificaEntregaDePaletes() {
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
            System.out.println("Não há paletes para entregar");
            return false;
        }
        Menu menuDePaletes = new Menu(paletesAEntregar);
        menuDePaletes.executa();
        int escolha = menuDePaletes.getOpcao();

        if (escolha <= 0)
            return false;
        String aux = paletesAEntregar.get(escolha - 1);
        String codPalete = aux.substring(aux.indexOf(" ") + 1, aux.indexOf(","));

        String codprateleira = getPrateleiraParaArmazenamento();
        Prateleira prateleira = this.prateleiras.get(codprateleira);
        Robot robot = this.robots.get(this.paletes.get(codPalete).getLoc());

        robot.doDelivering(GPS.criaCaminho(this.mapa, robot.getLocalizacao().clone(), prateleira.getLocalizacao().clone()));
        Palete palete = this.paletes.get(codPalete);
        palete.setLoc(codprateleira);

        prateleira.setCodPalete(codPalete);
        this.paletes.put(codPalete, palete); // atualizar palete (localização)
        this.robots.put(robot.getCod(), robot); // atualizar robot (palete, localizaçao, disponibilidade)
        this.prateleiras.put(codprateleira, prateleira); // atualizar prateleira (palete)
        return true;
    }


    public void run() {
        this.leitor.run();
    }

    public void teste() {
        System.out.println(GPS.criaCaminho(mapa, new GPS(0, 0), new GPS(15, 5)).toString());

        System.out.println(GPS.criaCaminho(mapa, new GPS(0, 0), new GPS(4, 0)).toString());


    }

    public String geraCodigoQR() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
        return (generatedString);
    }

}
