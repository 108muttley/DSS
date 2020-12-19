package Modelo;


public class Robot {
    private String codRobot;
    private boolean livre;
    private Palete palete;
    private GPS localizacao;

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
        setLivre(r.getLivre());
        setPalete(r.getPalete());
    }

    public GPS getLocalizacao() {
        return this.localizacao;
    }

    public void setLocalizacao(GPS localizacao) {
        this.localizacao = localizacao;
    }

    public boolean getLivre() {
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

}
