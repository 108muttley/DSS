package Modelo;

import database.PaleteDAO;
import database.PrateleiraDAO;
import database.RobotDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Sistema {
    private Map<String, Palete> paletes;
    private Map<String,Prateleira> prateleiras;
    private Map<String,Robot> robots;


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
        if(this.paletes.size() > 0)
            for(String s : this.paletes.keySet()){
                if(this.paletes.get(s).getLoc().equals("0-0")){ // se estiver na receçao, adiciona 0-0 - receção
                    this.paletesWaitingForDelivering.add(s);
                }
            }
        else System.out.println("Não há paletes");
        this.leitor = new LeitorQrCode(this);
        this.mapa = GPS.criaMapa();
        //this.mapa = GPS.criaMapa();
    }

    public Boolean comunicaCodigoQR(String produto) {
        String cod = geraCodigoQR();
        this.paletes.put(cod, new Palete(cod, "0-0", produto));
        this.paletesWaitingForDelivering.add(cod);
        return true;
    }

    public Boolean comunicaOrdemDeTransporte() {
        if (this.paletesWaitingForDelivering.size() > 0) {
            String produtoATransportar = this.paletesWaitingForDelivering.get(0);
            String prateleira = getPrateleiraLivre();
            return comunicaRobotMaisProximo(produtoATransportar, prateleira);
        }
        return false;
    }


    // ver qual o robot mais proximo
    // enviar-lhe o percurso de ir buscar a palete + o de entregar
    // robot comunicar que iniciou a entrega
    public Boolean comunicaRobotMaisProximo(String codigoPalete, String prateleiraDestino){ // robot -> localizacao da palete -> destino
        if(!this.prateleiras.get(prateleiraDestino).isAvailable()) return false;
        List<GPS> pathFinal = new ArrayList<>();
        List<GPS> testar = new ArrayList<>();
        GPS destino = this.prateleiras.get(prateleiraDestino).getLocalizacao();
        this.prateleiras.get(prateleiraDestino).setDisponibilidade(false);
        double maximo = 99;
        double atual;
        GPS meio = new GPS(0,0);
        String robotEscolhido = "";
        for(String s : this.robots.keySet()){
            if(this.robots.get(s).isAvailable()) {
                GPS inicio = this.robots.get(s).getLocalizacao();
                if ((atual = GPS.criaCaminho(this.mapa, inicio, meio, testar)) < maximo) { // encontrou novo robot mais proximo
                    robotEscolhido = s;
                    maximo = atual;
                    pathFinal = new ArrayList<>(testar);
                    testar = new ArrayList<>();
                }
            }
        }
        System.out.println(robotEscolhido);
        if(robotEscolhido.isEmpty()) return false;
        List<GPS> entrega = new ArrayList<>(); // parte da localização da palete -> prateleira
        GPS.criaCaminho(this.mapa,meio,destino,entrega);
        Percurso percurso = new Percurso(pathFinal,entrega);

        Palete palete = this.paletes.get(codigoPalete);
        //this.paletes.remove(codigoPalete);
        palete.setLoc(robotEscolhido); // Enquanto robot anda até à palete, a palete já está na sua "posse" <- dar fix nisto
        this.paletes.put(codigoPalete,palete);
        if(this.robots.get(robotEscolhido).doDelivering( palete , percurso)){
            palete = this.paletes.get(codigoPalete);
            palete.setLoc(prateleiraDestino); // alterar localização para prateleira
            //this.paletes.remove(codigoPalete);
            this.paletes.put(codigoPalete,palete);
            Prateleira prateleira = this.prateleiras.get(prateleiraDestino);
            //this.prateleiras.remove(prateleiraDestino);
            prateleira.setDisponibilidade(false);
            this.prateleiras.put(prateleiraDestino,prateleira); // alterar palete que a prateleira tem
        }

        return true;
    }

    public String getPrateleiraLivre(){ // busca uma prateleira livre
        for(String s : this.prateleiras.keySet()){
            if(this.prateleiras.get(s).isAvailable()) return s;
        }
        return null;
    }

    public Boolean consultaListagem(){
        for(String s : this.paletes.keySet()){
            Palete p = this.paletes.get(s);
            GPS coordenadas;
            if(p.getLoc().startsWith("R"))
                coordenadas = this.robots.get(p.getLoc()).getLocalizacao().clone();
            else
                coordenadas = this.prateleiras.get(p.getLoc()).getLocalizacao().clone();
            System.out.println("Palete { código: " + s +
                    ", material: " + p.getM() +
                    ", localização: " + p.getLoc() +
                    ", GPS: " + coordenadas.toString());
        }
        return true;
    }


    public void run() {
        this.leitor.run();
    }

    public void teste() {
        List<GPS> caminho = new ArrayList<>();
        System.out.println(GPS.criaCaminho(mapa, new GPS(26, 5), new GPS(4, 0),caminho) + " e " + caminho);
        List<GPS> caminho2 = new ArrayList<>();
        System.out.println(GPS.criaCaminho(mapa, new GPS(26, 5), new GPS(4, 0),caminho2) + " e " + caminho2);
        List<GPS> caminho3 = new ArrayList<>();
        caminho3.addAll(caminho);
        System.out.println("#######");
        System.out.println(caminho3);
        caminho3.addAll(caminho2);
        System.out.println(caminho3);
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
