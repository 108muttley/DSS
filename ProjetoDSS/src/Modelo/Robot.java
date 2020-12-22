package Modelo;


import java.util.ArrayList;
import java.util.List;

public class Robot {
    private String codRobot;
    private boolean livre;
    private String codPalete;
    private GPS localizacao;
    //private Percurso percurso;

    public Robot(String codRobot,GPS localizacao, boolean livre, String pal) {
        this.codRobot = codRobot;
        setLocalizacao(localizacao);
        setLivre(livre);
        this.codPalete = pal;
    }

    public Robot(){
        this.codRobot = "";
        setLocalizacao(new GPS());
        setLivre(true);
        setCodPalete(null);
    }


    public Robot(Robot r){
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

    public String getCod(){
        return this.codRobot;
    }


    // falta saber se é preciso atualizar a localização na database
    public boolean doDelivering(Percurso percurso){
        List<GPS> aux = percurso.getRecolha();
        for(GPS g : aux){
            this.localizacao = g.clone();
        }
        //this.localizacao = aux.get(aux.size()-1);
        //System.out.println("Robot: Palete Recolhida com Sucesso");

        //this.localizacao = percurso.getPontoDeEntrega();
        System.out.println("Robot: Transporte Feito com Sucesso");
        this.codPalete = null;
        this.livre = true;
        return true;
    }

    public boolean doRecolha(Percurso percurso, Palete palete){
        List<GPS> aux = percurso.getRecolha();
        for(GPS g : aux){
            this.localizacao = g.clone();
        }
        //this.localizacao = aux.get(aux.size()-1);
        //System.out.println("Robot: Palete Recolhida com Sucesso");

        //this.localizacao = percurso.getPontoDeEntrega();
        this.codPalete = palete.getCodPalete();
        System.out.println("Robot: Palete Recolhida com Sucesso");
        return true;
    }

}
