package br.com.breno.jogodaforca.model;

public class Palavra {

    private String id;
    private String categoria;
    private String texto;

    public Palavra() {
    }

    public Palavra(String categoria, String texto) {
        this.categoria = categoria;
        this.texto = texto.toUpperCase();
    }

    // getters/setters...
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        this.texto = texto;
    }
}
