package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Teste {
    public static void main(String[] args) {
        //Sistema s = new Sistema();
        //List<String> l = new ArrayList<>();
        //l.add("cimento");
        //s.geraCodigoQR();
        //s.teste();
        String aux = "R01: CONA, leite";
        String robotCode = aux.substring(aux.indexOf(" ")+1,aux.indexOf(","));
        System.out.println(robotCode);
    }
}
