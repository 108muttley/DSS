package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Palete {
    private String codPalete;
    private String localizacao; //localização da prateleira/robot
    private String material;
    private float preco;
    private List<String> listaDeMateriais;

    public Palete(String codPalete, String localizacao, String material, float preco, List<String> listaDeMateriais) {
        this.codPalete = codPalete;
        this.localizacao = localizacao;
        this.material = material;
        this.preco = preco;
        this.listaDeMateriais = listaDeMateriais;
    }


    public Palete() {
        this.codPalete = "";
        this.localizacao = "";
        this.material = "";
        this.preco = 0;
        this.listaDeMateriais = new ArrayList<>();

    }

    public Palete(Palete p) {
        setCodPalete(p.getCodPalete());
        setLoc(p.getLoc());
        setM(p.getM());
        setPreco(p.getPreco());
        setListaDeMateriais(p.getListaDeMateriais());

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

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public List<String> getListaDeMateriais() {
        List<String> novo = new ArrayList<>();
        for (String s : this.listaDeMateriais)
            novo.add(s);
        return novo;
    }

    public void setListaDeMateriais(List<String> list){
        this.listaDeMateriais = list;
    }
}
