package Modelo;

public class Prateleira {
    private String codPrateleira;
    private boolean disponibilidade;
    private String codPalete;
    private GPS localizacao;


    public Prateleira(String codPrateleira, boolean disponibilidade, String codPalete, GPS localizacao) {
        this.codPrateleira = codPrateleira;
        this.disponibilidade = disponibilidade;
        this.codPalete = codPalete;
        this.localizacao = localizacao;
    }

    public Prateleira(String codPrateleira,  GPS localizacao) {
        this.codPrateleira = codPrateleira;
        this.disponibilidade = true;
        this.codPalete = null;
        this.localizacao = localizacao;
    }

    public String getCodPrateleira() {
        return codPrateleira;
    }

    public void setCodPrateleira(String codPrateleira) {
        this.codPrateleira = codPrateleira;
    }

    public boolean isAvailable() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public String getCodPalete() {
        return codPalete;
    }

    public void setCodPalete(String codPalete) {
        this.codPalete = codPalete;
    }

    public GPS getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(GPS localizacao) {
        this.localizacao = localizacao;
    }
}
