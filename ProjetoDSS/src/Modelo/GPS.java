package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GPS {
    private int x;
    private int y;


//   ----*------*------*--------------------
    /*
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
*/
    public static List<GPS> criaCaminho(Map<GPS,List<GPS>> mapa,GPS source, GPS destination){
        List<GPS> menorCaminho = new ArrayList<>();



        menorCaminho.add(source);

        for (int i = 0; i< mapa.size(); i++){

        }

        return menorCaminho;
    }



    public static Map<GPS, List<GPS>> criaMapa(){
        Map<GPS, List<GPS>> map = new HashMap<>();
        List<GPS> zero = new ArrayList<>();
        zero.add(new GPS(4,0));
        map.put((new GPS(0,0)),zero);
        List<GPS> um = new ArrayList<>();
        um.add(new GPS(5,0));
        um.add(new GPS(4,5));
        map.put((new GPS(4,0)),um);
        List<GPS> dois = new ArrayList<>();
        dois.add(new GPS(4,0));
        dois.add(new GPS(10,0));
        map.put((new GPS(5,0)),dois);
        List<GPS> tres = new ArrayList<>();
        tres.add(new GPS(5,0));
        tres.add(new GPS(15,0));
        map.put((new GPS(10,0)),tres);
        List<GPS> quat = new ArrayList<>();
        quat.add(new GPS(10,0));
        quat.add(new GPS(20,0));
        map.put((new GPS(15,0)),quat);
        List<GPS> cinco = new ArrayList<>();
        cinco.add(new GPS(15,0));
        cinco.add(new GPS(25,0));
        map.put((new GPS(20,0)),cinco);
        List<GPS> seis = new ArrayList<>();
        seis.add(new GPS(20,0));
        seis.add(new GPS(26,0));
        map.put((new GPS(25,0)),seis);
        List<GPS> sete = new ArrayList<>();
        sete.add(new GPS(25,0));
        sete.add(new GPS(26,3));
        map.put((new GPS(26,0)),sete);
        List<GPS> oito = new ArrayList<>();
        oito.add(new GPS(26,0));
        oito.add(new GPS(28,3));
        oito.add(new GPS(26,5));
        map.put((new GPS(26,3)),oito);
        List<GPS> nove = new ArrayList<>();
        nove.add(new GPS(26,3));
        map.put((new GPS(28,3)),nove);
        List<GPS> dez = new ArrayList<>();
        dez.add(new GPS(26,3));
        dez.add(new GPS(25,5));
        map.put((new GPS(26,5)),dez);
        List<GPS> onze = new ArrayList<>();
        onze.add(new GPS(26,5));
        onze.add(new GPS(20,5));
        map.put((new GPS(25,5)),onze);
        List<GPS> doze = new ArrayList<>();
        doze.add(new GPS(25,5));
        doze.add(new GPS(15,5));
        map.put((new GPS(20,5)),doze);
        List<GPS> treze = new ArrayList<>();
        treze.add(new GPS(10,5));
        treze.add(new GPS(20,5));
        map.put((new GPS(15,5)),treze);
        List<GPS> cator = new ArrayList<>();
        cator.add(new GPS(5,5));
        cator.add(new GPS(15,5));
        map.put((new GPS(10,5)),cator);
        List<GPS> quinz= new ArrayList<>();
        quinz.add(new GPS(4,5));
        quinz.add(new GPS(10,5));
        map.put((new GPS(5,5)),quinz);
        List<GPS> d6 = new ArrayList<>();
        d6.add(new GPS(5,5));
        d6.add(new GPS(4,0));
        map.put((new GPS(4,5)),d6);
    return map;
    }

/*
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

 */

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
