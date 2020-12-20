package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Teste {
    public static void main(String[] args) {
        Sistema s = new Sistema();
        List<String> l = new ArrayList<>();
        l.add("cimento");
        s.geraCodigoQR();
        //s.run();
    }
}
