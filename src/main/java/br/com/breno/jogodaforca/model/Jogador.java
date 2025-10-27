package br.com.breno.jogodaforca.model;

public class Jogador {
    private String id; // para MongoDB pode ser ObjectId string
    private String nome;
    private int pontuacaoTotal;

    public Jogador() {}
    public Jogador(String nome) { this.nome = nome; this.pontuacaoTotal = 0; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getPontuacaoTotal() { return pontuacaoTotal; }
    public void setPontuacaoTotal(int pontuacaoTotal) { this.pontuacaoTotal = pontuacaoTotal; }
}
