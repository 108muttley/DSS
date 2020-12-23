package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Percurso {
    private List<GPS> recolha;
    private int distancia;


    public Percurso(List<GPS> recolha, int distancia) {
        this.recolha = recolha;
        this.distancia = distancia;
    }

    public Percurso(Percurso p) {
        this.recolha = new ArrayList<>();
        for (GPS g : p.getRecolha())
            this.recolha.add(g.clone());
        this.distancia = p.getDistancia();
    }

    public List<GPS> getRecolha() {
        List<GPS> novo = new ArrayList<>();
        for (GPS gps : recolha) {
            novo.add(gps.clone());
        }
        return recolha;
    }

    public int getDistancia() {
        return this.distancia;
    }

    public Percurso clone() {
        return new Percurso(this);
    }

    public String toString() {
        return this.recolha.toString() + " com distancia: " + this.distancia;
    }
}
