package Modelo;


public class Robot {
    private boolean livre;
    private Palete pal;
    private GPS loc;

    public Robot(GPS localizacao, boolean livre, Palete pal) {
        setLocalizacao(localizacao);
        setLivre(livre);
        setPal(pal);
    }

    public Robot(){
        setLocalizacao(new GPS());
        setLivre(true);
        setPal(null);
    }    private GPS localizacao;


    public Robot(Robot r){
        setLocalizacao(r.getLocalizacao());
        setLivre(r.getLivre());
        setPal(r.getPal());
    }

    public GPS getLocalizacao() {
        return this.loc;
    }

    public void setLocalizacao(GPS localizacao) {
        this.loc = localizacao;
    }

    public boolean getLivre() {
        return livre;
    }

    public void setLivre(boolean livre) {
        this.livre = livre;
    }

    public Palete getPal() {
        return pal;
    }

    public void setPal(Palete p) {
        this.pal = p;
    }
}
