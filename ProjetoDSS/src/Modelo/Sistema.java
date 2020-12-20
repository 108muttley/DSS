package Modelo;

import database.PaleteDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Sistema {
    //    private List<Palete> paletesWaitingForReading;
    private Map<String, Palete> paletes;


    private List<String> paletesWaitingForDelivering;
    private LeitorQrCode leitor;
    private List<GPS> mapa;
    //private Map<GPS, List<GPS>> mapa;


    public Sistema() {
        this.paletes = PaleteDAO.getInstance();
        this.paletesWaitingForDelivering = new ArrayList<>();
        this.leitor = new LeitorQrCode(this);
        this.mapa = GPS.criaMapa();
        //this.mapa = GPS.criaMapa();
    }

    public Boolean comunicaCodigoQR(String produto) {
        String cod = geraCodigoQR();
        this.paletes.put(cod, new Palete(cod, "reception", produto));

        this.paletesWaitingForDelivering.add(produto);
        return true;
    }

    public Boolean comunicaOrdemDeTransporte() {
        return false;
    }


    public void run() {
        this.leitor.run();
    }

    public void teste() {
        GPS.criaCaminho(mapa, new GPS(26, 5), new GPS(4, 0));
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
