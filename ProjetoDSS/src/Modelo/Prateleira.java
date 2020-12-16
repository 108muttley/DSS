package Modelo;

public class Prateleira {
    private String codPrateleira;
    private boolean disponibilidade;
    private Palete p;
    private GPS localizacao;

    public Prateleira(String codPrateleira, boolean disponibilidade, Palete p, GPS localizacao) {
        this.codPrateleira = codPrateleira;
        this.disponibilidade = disponibilidade;
        this.p = p;
        this.localizacao = localizacao;
    }

    public String getCodPrateleira() {
        return codPrateleira;
    }

    public void setCodPrateleira(String codPrateleira) {
        this.codPrateleira = codPrateleira;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Palete getP() {
        return p;
    }

    public void setP(Palete p) {
        this.p = p;
    }

    public GPS getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(GPS localizacao) {
        this.localizacao = localizacao;
    }
}
