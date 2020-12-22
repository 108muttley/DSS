package Modelo;


import java.util.List;

public class Robot {
    private String codRobot;
    private boolean livre;
    private String codPalete;
    private GPS localizacao;

    public Robot(String codRobot, GPS localizacao, boolean livre, String pal) {
        this.codRobot = codRobot;
        setLocalizacao(localizacao);
        setLivre(livre);
        this.codPalete = pal;
    }

    public Robot(Robot r) {
        this.codRobot = r.getCod();
        setLocalizacao(r.getLocalizacao());
        setLivre(r.isAvailable());
        this.codPalete = r.getCodPalete();
    }

    public GPS getLocalizacao() {
        return this.localizacao.clone();
    }

    public void setLocalizacao(GPS localizacao) {
        this.localizacao = localizacao;
    }

    public boolean isAvailable() {
        return livre;
    }

    public void setLivre(boolean livre) {
        this.livre = livre;
    }

    public String getCodPalete() {
        return this.codPalete;
    }

    public void setCodPalete(String p) {
        this.codPalete = p;
    }

    public String getCod() {
        return this.codRobot;
    }

    public Robot clone() {
        return new Robot(this);
    }

    public String doDelivering(Percurso percurso) {
        List<GPS> aux = percurso.getRecolha();
        for (GPS g : aux) {
            this.localizacao = g.clone();
        }
        this.codPalete = null;
        this.livre = true;
        return ("Robot " + this.codRobot + ":> Transporte Feito com Sucesso");
    }

    public String doRecolha(Percurso percurso, Palete palete) {
        List<GPS> aux = percurso.getRecolha();
        for (GPS g : aux) {
            this.localizacao = g.clone();
        }
        this.codPalete = palete.getCodPalete();
        return ("Robot " + this.codRobot + ":> Palete Recolhida com Sucesso!");
    }

}
