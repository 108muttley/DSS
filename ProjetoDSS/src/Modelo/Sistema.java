package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Object> paletesWaitingForReading;
    private int lidos;
    private List<Palete> paletesWaitingForDelivering;
    private LeitorQrCode leitor;

    public Sistema(){
        this.paletesWaitingForReading = new ArrayList<>();
        this.paletesWaitingForDelivering = new ArrayList<>();
        this.leitor = new LeitorQrCode(this);
        this.lidos = 0;
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

}
