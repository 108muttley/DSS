package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Teste {
    public static void main(String[] args) {
        Sistema s = new Sistema();
        List<String> l = new ArrayList<>();
        l.add("cimento");
        s.addPaleteWaitingForReading(new RawPalete("ola","bom",23, l));
        s.addPaleteWaitingForReading(new RawPalete("foda-se","mau",23,l));


        s.teste();
        //s.run();
    }
}
