package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class GPS {
    private int x;
    private int y;


    public static double criaCaminho(List<GPS> mapa,GPS source, GPS destination, List<GPS> caminhoFinal){
        double melhor = 99;

        double total = 0;

        List<GPS> tentativa = new ArrayList<>();
        List<GPS> caminho = new ArrayList<>();

        if(source.getY() == destination.getY()){
            caminho.add(destination.clone());
            melhor = dist2Pontos(source,destination);
            //System.out.println("caminho encontrado com valor " + total);
        }
        else{

            Boolean valid = false;
            for(GPS temp : mapa){
                //System.out.println("#############");
                GPS back = source.clone();
                tentativa.add(source.clone());
                if(temp.getY() == back.getY()) {
                    //System.out.println(back.toString() + " -> " + temp.toString());
                    total += dist2Pontos(back, temp);
                    tentativa.add(temp.clone());
                    back = temp.clone();
                    for(GPS temp2 : mapa){
                        if(temp2.getX() == back.getX() && temp2.getY() == destination.getY()){
                            //System.out.println(back.toString() + " -> " + temp2.toString());
                            total += dist2Pontos(back,temp2);
                            tentativa.add(temp2.clone());
                            back = temp2.clone();
                            total += dist2Pontos(back,destination);
                            tentativa.add(destination.clone());
                            //System.out.println(back.toString() + " -> " + destination.toString());
                            //System.out.println(tentativa + " com total " + total);
                            valid = true;
                        }
                    }
                }
                if(!valid) {
                    total = 0;
                    tentativa = new ArrayList<>();
                }
                else {
                    //System.out.println("1 caminho encontrado com valor " + total);
                    if(total < melhor){
                        //System.out.println("foi melhor");
                        melhor = total;
                        //System.out.println(melhor);
                        caminho = new ArrayList<>(tentativa);
                        //System.out.println(tentativa);
                        //System.out.println(caminho);
                    }
                    tentativa = new ArrayList<>();
                    total=0;
                    valid = false;
                }
            }
        }
        System.out.println("melhor caminho encontrado com valor " + melhor + " e caminho :" + caminho);
        caminhoFinal.addAll(caminho);
        return melhor;
    }

    public static double dist2Pontos(GPS a, GPS b){
        return sqrt(pow((a.getX()-b.getX()),2) + pow((a.getY()-b.getY()),2));
    }

    public static List<GPS> criaMapa(){
        List<GPS> mapa = new ArrayList<>();
        mapa.add(new GPS(4,0));
        mapa.add(new GPS(26,0));
        mapa.add(new GPS(4,5));
        mapa.add(new GPS(26,5));
        mapa.add(new GPS(26,3));
        return mapa;
    }

    public GPS(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public GPS(){
        setX(0);
        setY(0);
    }
    public GPS(GPS g){
        setX(g.getX());
        setY(g.getY());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public GPS clone(){
        return new GPS(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GPS gps = (GPS) o;
        return x == gps.x && y == gps.y;
    }

    @Override
    public String toString() {
        return "Modelo.GPS{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
