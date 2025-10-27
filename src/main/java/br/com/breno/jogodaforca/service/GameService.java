package br.com.breno.jogodaforca.service;

import br.com.breno.jogodaforca.model.Palavra;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameService {

    private Palavra palavraAtual;
    private int tentativasRestantes;
    private Set<Character> letrasCorretas;
    private Set<Character> letrasErradas;

    public GameService() {
        this.tentativasRestantes = 6;
        this.letrasCorretas = new HashSet<>();
        this.letrasErradas = new HashSet<>();
    }

    // ✅ Inicia o jogo com lista de palavras disponíveis
    public void iniciarJogo(List<Palavra> listaPalavras) {
        if (listaPalavras == null || listaPalavras.isEmpty()) {
            System.out.println("⚠️ Nenhuma palavra encontrada. Usando palavra padrão.");
            this.palavraAtual = new Palavra("Animais", "JACARÉ");
        } else {
            Random random = new Random();
            this.palavraAtual = listaPalavras.get(random.nextInt(listaPalavras.size()));
        }
        this.tentativasRestantes = 6;
        this.letrasCorretas.clear();
        this.letrasErradas.clear();
    }

    // ✅ Mostra a palavra parcialmente revelada
    public String getPalavraParcial() {
        if (palavraAtual == null) return "";
        StringBuilder parcial = new StringBuilder();
        for (char c : palavraAtual.getTexto().toUpperCase().toCharArray()) {
            if (letrasCorretas.contains(c)) {
                parcial.append(c).append(" ");
            } else {
                parcial.append("_ ");
            }
        }
        return parcial.toString();
    }

    // ✅ Verifica se o jogador acertou uma letra
    public boolean tentarLetra(char letra) {
        if (palavraAtual == null) return false;

        letra = Character.toUpperCase(letra);
        boolean acertou = false;

        if (palavraAtual.getTexto().toUpperCase().indexOf(letra) >= 0) {
            letrasCorretas.add(letra);
            acertou = true;
        } else {
            if (!letrasErradas.contains(letra)) {
                letrasErradas.add(letra);
                tentativasRestantes--;
            }
        }

        return acertou;
    }

    // ✅ Verifica se o jogador venceu
    public boolean venceu() {
        if (palavraAtual == null) return false;
        for (char c : palavraAtual.getTexto().toUpperCase().toCharArray()) {
            if (!letrasCorretas.contains(c)) return false;
        }
        return true;
    }

    // ✅ Verifica se perdeu
    public boolean perdeu() {
        return tentativasRestantes <= 0;
    }

    // ✅ Getters
    public Palavra getPalavraAtual() {
        return palavraAtual;
    }

    public int getTentativasRestantes() {
        return tentativasRestantes;
    }

    public Set<Character> getLetrasErradas() {
        return letrasErradas;
    }

    public Set<Character> getLetrasCorretas() {
        return letrasCorretas;
    }
    
    
}
