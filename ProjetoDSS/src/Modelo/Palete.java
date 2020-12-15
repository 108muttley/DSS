package Modelo;

public class Palete {
    private String codPalete;
    private GPS loc;
    private Material m;
    private float preco;
    private String emPrateleira;


    public Palete(String codPalete, GPS loc, Material m, float preco, String emPrateleira ) {
        this.codPalete = codPalete;
        this.loc = loc;
        this.m = m;
        this.preco = preco;
        this.emPrateleira = emPrateleira;
    }


    public Palete() {
        this.codPalete = "";
        this.loc = new GPS();
        this.m = new Material();
        this.preco = 0;
        this.emPrateleira = "";
    }

    public Palete(Palete p) {
        setCodPalete(p.getCodPalete());
        setLoc(p.getLoc());
        setM(p.getM());
        setPreco(p.getPreco());
        setEmPrateleira(p.getEmPrateleira());

    }


    public String getCodPalete() {
        return codPalete;
    }

    public void setCodPalete(String codPalete) {
        this.codPalete = codPalete;
    }

    public GPS getLoc() {
        return loc;
    }

    public void setLoc(GPS loc) {
        this.loc = loc;
    }

    public Material getM() {
        return m;
    }

    public void setM(Material m) {
        this.m = m;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getEmPrateleira() {
        return emPrateleira;
    }

    public void setEmPrateleira(String emPrateleira) {
        this.emPrateleira = emPrateleira;
    }
}
