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
    private String jogadorId;
    private int pontuacao;
    private String data;

    public Partida(String jogadorId, int pontuacao) {
        this.jogadorId = jogadorId;
        this.pontuacao = pontuacao;
        this.data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    
    

    public Partida(String jogadorId, int pontuacao, String data) {
        this.jogadorId = jogadorId;
        this.pontuacao = pontuacao;
        this.data = data;
    }

    public String getJogadorId() {
        return jogadorId;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public String getData() {
        return data;
    }

}