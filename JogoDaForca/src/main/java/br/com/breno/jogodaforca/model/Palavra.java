package br.com.breno.jogodaforca.model;

/**
 *
 * @author breno
 */
public class Palavra {

    private String categoria;
    private String texto;
    private String dica;

  
    public Palavra(String categoria, String texto, String dica) {
        this.categoria = categoria;
        this.texto = texto.toUpperCase();
        this.dica = dica; 
    }
    

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto.toUpperCase();
    }

    
    public String getDica() {
        return dica;
    }

    public void setDica(String dica) {
        this.dica = dica;
    }
}
