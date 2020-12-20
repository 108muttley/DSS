package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Palete {
    private String codPalete;
    private String localizacao; //localização da prateleira/robot
    private String material;

    public Palete(String codPalete, String localizacao, String material) {
        this.codPalete = codPalete;
        this.localizacao = localizacao;
        this.material = material;

    }


    public Palete() {
        this.codPalete = "";
        this.localizacao = "";
        this.material = "";
    }

    public Palete(Palete p) {
        setCodPalete(p.getCodPalete());
        setLoc(p.getLoc());
        setM(p.getM());
    }


    public String getCodPalete() {
        return codPalete;
    }

    public void setCodPalete(String codPalete) {
        this.codPalete = codPalete;
    }

    public String getLoc() {
        return localizacao;
    }

    public void setLoc(String loc) {
        this.localizacao = loc;
    }

    public String getM() {
        return material;
    }

    public void setM(String material) {
        this.material = material;
    }
}
