package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Percurso {
    //private String codPalete;
    private List<GPS> recolha;
    private int distancia;
    //private List<GPS> entrega;


    public Percurso(List<GPS> recolha, int distancia) {
        this.recolha = recolha;
        this.distancia = distancia;
        //this.entrega = entrega;
    }

    public Percurso(Percurso p){
        this.recolha = new ArrayList<>();
        //this.entrega = new ArrayList<>();
        for(GPS g : p.getRecolha())
            this.recolha.add(g.clone());
        this.distancia = p.getDistancia();
        //for(GPS g : p.getEntrega())
        //    this.entrega.add(g.clone());
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

    public int getDistancia(){
        return this.distancia;
    }



    public Percurso clone(){
        return new Percurso(this);
    }

    public GPS getPontoDeEntrega(){
        return this.recolha.get(this.recolha.size()-1);
    }

    public String toString(){
        return this.recolha.toString() + " com distancia: " + this.distancia;
    }
}
