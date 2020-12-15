package Modelo;

public class Material {
    private String tipo;


    public Material(String tipo) {
        this.tipo = tipo;
    }

    public Material(){
        this.tipo = "";
    }

    public Material(Material m){
        setTipo(m.getTipo());
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
