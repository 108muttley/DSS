package Modelo;

import java.util.HashMap;
import java.util.Map;

public class Corredor {
    private String idCorredor;
    private Map<Integer ,Prateleira> prateleiras;


    public Corredor(String idCorredor, Map<Integer, Prateleira> prateleiras) {
        this.idCorredor = idCorredor;
        Map<Integer, Prateleira> prat = new HashMap<>();
        //não me lembro do clone
    }

    public Corredor(){
        this.idCorredor = "";
        this.prateleiras = new HashMap<>();
    }



}
