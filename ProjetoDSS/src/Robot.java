public class Robot {
    private GPS localizacao;
    private Boolean livre;
    private Palete pal;


    public Robot(GPS localizacao, Boolean livre, Palete pal) {
        this.localizacao = localizacao;
        this.livre = livre;
        this.pal = pal;
    }

    public GPS getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(GPS localizacao) {
        this.localizacao = localizacao;
    }

    public Boolean getLivre() {
        return livre;
    }

    public void setLivre(Boolean livre) {
        this.livre = livre;
    }

    public Palete getPal() {
        return pal;
    }

    public void setPal(Palete pal) {
        this.pal = pal;
    }
}
