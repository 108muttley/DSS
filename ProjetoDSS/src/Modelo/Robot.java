package Modelo;


import java.util.ArrayList;
import java.util.List;

public class Robot {
    private String codRobot;
    private boolean livre;
    private Palete palete;
    private GPS localizacao;
    //private Percurso percurso;

    public Robot(String codRobot,GPS localizacao, boolean livre, Palete pal) {
        this.codRobot = codRobot;
        setLocalizacao(localizacao);
        setLivre(livre);
        setPalete(pal);
    }

    public Robot(){
        this.codRobot = "";
        setLocalizacao(new GPS());
        setLivre(true);
        setPalete(null);
    }


    public Robot(Robot r){
        this.codRobot = r.getCod();
        setLocalizacao(r.getLocalizacao());
        setLivre(r.isAvailable());
        setPalete(r.getPalete());
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

    public Palete getPalete() {
        return palete;
    }

    public void setPalete(Palete p) {
        this.palete = p;
    }

    public String getCod(){
        return this.codRobot;
    }

    public void notificaSistemaRecolha(){
        List<Object> args = new ArrayList<>();
        args.add(this.codRobot); // Codigo de robot que está a comunicar
        args.add(this.palete.getCodPalete()); // Codigo de palete
        //args.add(this.percurso.getPontoDeEntrega()); // GPS
        //sistema.comunicaSistema("RobotRecolha",args);
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
        this.palete = null;
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
        System.out.println("Robot: Palete Recolhida com Sucesso");
        this.palete = palete.clone();
        return true;
    }

}
