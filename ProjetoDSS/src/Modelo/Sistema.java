package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sistema {
    private List<Object> paletesWaitingForReading;
    private List<Palete> paletesWaitingForDelivering;
    private LeitorQrCode leitor;


    private List<GPS> mapa;
    //private Map<GPS, List<GPS>> mapa;



    public Sistema(){
        this.paletesWaitingForReading = new ArrayList<>();
        this.paletesWaitingForDelivering = new ArrayList<>();
        this.leitor = new LeitorQrCode(this);
        this.mapa = GPS.criaMapa();
        //this.mapa = GPS.criaMapa();
    }

    public void addPaleteWaitingForReading(Object o){
        this.paletesWaitingForReading.add(o);
    }

    public void removePaleteAguardarLeitura(){
        this.paletesWaitingForReading.remove(0);
    }

    public Object getPaleteWaitingForReading(){
        if(this.paletesWaitingForReading.size() != 0)
            return this.paletesWaitingForReading.get(0);
        return null;
    }

    public void addNovaPalete(Palete p){
        this.paletesWaitingForDelivering.add(p);
    }

    public void comunicaSistema(String com, List<Object> args){
        switch (com){
            case "novaPalete":
                Palete p = (Palete) args.get(0);
                this.paletesWaitingForDelivering.add(p);
                removePaleteAguardarLeitura();
        }
    }
    public void run(){
        this.leitor.run();
    }

    public void teste(){
        GPS.criaCaminho(mapa,new GPS(26,5),new GPS(26,0));
    }

}
