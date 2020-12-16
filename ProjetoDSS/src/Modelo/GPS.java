package Modelo;

import java.util.ArrayList;
import java.util.List;

public class GPS {
    private int x;
    private int y;


//   ----*------*------*--------------------
    public static List<GPS> criaCaminho(List<GPS> mapa,GPS source, GPS destination){ // a -> b
        List<Integer> extremidades = new ArrayList<>();

        extremidades.add(4);
        extremidades.add(26);

        List<GPS> caminho = new ArrayList<>();
        if(source.getY() == destination.getY()){

            int yfinal = source.getY();
            if(source.getX() < destination.getX()){
                int xtemp = source.getX();
                do{
                    xtemp++;
                    GPS temp = new GPS(xtemp,yfinal);
                    if(mapa.contains(temp)){
                        caminho.add(temp.clone());
                    }
                } while(xtemp != destination.getX());
            }
            else if(source.getX() > destination.getX()){
                int xtemp = source.getX();
                do{
                    xtemp--;
                    GPS temp = new GPS(xtemp,yfinal);
                    if(mapa.contains(temp)){
                        caminho.add(temp.clone());
                    }
                } while(xtemp != destination.getX());
            }
            else
                return null;
        }
        // y diferentes
        else{
            List<GPS> caminho1 = new ArrayList<>();

            int xtemp = source.getX();
            if(xtemp < extremidades.get(0)){
                do{
                    xtemp ++;
                    GPS temp = new GPS(xtemp,source.getX());
                    if(mapa.contains(temp)){
                        caminho.add(temp.clone());
                    }
                } while(xtemp<extremidades.get(0));



            }
            List<GPS> caminho2 = new ArrayList<>();
        }
        return caminho;
    }

    public static List<GPS> criaMapa(){
        List<GPS> mapa = new ArrayList<>();
        mapa.add(new GPS(0,0));
        mapa.add(new GPS(4,0));
        mapa.add(new GPS(5,0));
        mapa.add(new GPS(10,0));
        mapa.add(new GPS(15,0));
        mapa.add(new GPS(20,0));
        mapa.add(new GPS(25,0));
        mapa.add(new GPS(26,0));
        mapa.add(new GPS(4,5));
        mapa.add(new GPS(5,5));
        mapa.add(new GPS(10,5));
        mapa.add(new GPS(15,5));
        mapa.add(new GPS(20,5));
        mapa.add(new GPS(25,5));
        mapa.add(new GPS(26,5));
        mapa.add(new GPS(26,3));
        mapa.add(new GPS(28,3));
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
