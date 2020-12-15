package Modelo;

import java.util.ArrayList;
import java.util.List;

public class RawPalete {
    private String codigo;
    private String material;
    private float preco;
    private List<String> listaDeMateriais;

    public RawPalete(String codigo, String material, float preco, List<String> listaDeMateriais){
        this.codigo = codigo;
        this.material = material;
        this.preco = preco;
        this.listaDeMateriais = listaDeMateriais;
    }

    public String getCodigo(){
        return this.codigo;
    }
    public String getMaterial(){
        return this.material;
    }
    public float getPreco(){
        return this.preco;
    }

    public List<String> getListaDeMateriais(){
        List<String> novo = new ArrayList<>();
        for(String s : this.listaDeMateriais)
            novo.add(s);
        return novo;
    }
}
