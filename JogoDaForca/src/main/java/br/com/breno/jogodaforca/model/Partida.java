/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.breno.jogodaforca.model;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author breno
 */
public class Partida {
    private String jogadorNome;
    private int pontuacao;
    private int palavrasVencidas;
    private int dicasUsadas;
    private String data;
    
    private String categoria;
    private String dificuldade;

    public Partida(String jogadorNome, int pontuacao, int palavrasVencidas, int dicasUsadas, String categoria, String dificuldade) {
        this.jogadorNome = jogadorNome;
        this.pontuacao = pontuacao;
        this.palavrasVencidas = palavrasVencidas;
        this.dicasUsadas = dicasUsadas;
        this.categoria = categoria;
        this.dificuldade = dificuldade;       
        this.data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    
    

    public Partida(String jogadorNome, int pontuacao, int palavrasVencidas, int dicasUsadas, String categoria, String dificuldade, String data) {
        this.jogadorNome = jogadorNome;
        this.pontuacao = pontuacao;
        this.palavrasVencidas = palavrasVencidas;
        this.dicasUsadas = dicasUsadas;

        this.categoria = categoria;
        this.dificuldade = dificuldade;
        this.data = data;
    }

    public String getJogadorNome() {
        return jogadorNome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public String getData() {
        return data;
    }

    public int getPalavrasVencidas() {
        return palavrasVencidas;
    }

    public int getDicasUsadas() {
        return dicasUsadas;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDificuldade() {
        return dificuldade;
    }
    
}