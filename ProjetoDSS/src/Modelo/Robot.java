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
        setPal(pal);
    }

    public Robot(){
        this.codRobot = "";
        setLocalizacao(new GPS());
        setLivre(true);
        setPal(null);
    }


    public Robot(Robot r){
        this.codRobot = r.getCod();
        setLocalizacao(r.getLocalizacao());
        setLivre(r.getLivre());
        setPal(r.getPal());
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

    public Palete getPal() {
        return palete;
    }

    public void setPal(Palete p) {
        this.palete = p;
    }

    public String getCod(){
        return this.codRobot;
    }
}
