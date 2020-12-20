package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Percurso {
    private List<GPS> recolha;
    private List<GPS> entrega;


    public Percurso(List<GPS> recolha, List<GPS> entrega) {
        this.recolha = recolha;
        this.entrega = entrega;
    }

    public Percurso(Percurso p){
        this.recolha = new ArrayList<>();
        this.entrega = new ArrayList<>();
        for(GPS g : p.getRecolha())
            this.recolha.add(g.clone());
        for(GPS g : p.getEntrega())
            this.entrega.add(g.clone());
    }

    public List<GPS> getRecolha() {
        List<GPS> novo = new ArrayList<>();
        for (GPS gps : recolha ){
            novo.add(gps.clone());
        }
        return recolha;
    }

    public void setRecolha(List<GPS> recolha) {
        List<GPS> novo = new ArrayList<>();
        for (GPS gps : recolha ){
            novo.add(gps.clone());
        }
        this.recolha = recolha;
    }
    public List<GPS> getEntrega() {
        List<GPS> novo = new ArrayList<>();
        for (GPS gps : recolha ){
            novo.add(gps.clone());
        }
        return recolha;
    }

    public void setEntrega(List<GPS> recolha) {
        List<GPS> novo = new ArrayList<>();
        for (GPS gps : recolha ){
            novo.add(gps.clone());
        }
        this.recolha = recolha;
    }

    public Percurso clone(){
        return new Percurso(this);
    }

    public GPS getPontoDeEntrega(){
        return this.entrega.get(this.entrega.size()-1);
    }

}
