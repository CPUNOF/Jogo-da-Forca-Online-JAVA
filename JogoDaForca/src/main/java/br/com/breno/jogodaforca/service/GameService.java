/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.breno.jogodaforca.service;

import br.com.breno.jogodaforca.dao.PalavraDAO;
import br.com.breno.jogodaforca.model.Palavra;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

/**
 *
 * @author breno
 */
public class GameService {
    
    private static final Logger logger = Logger.getLogger(GameService.class.getName());
    private final PalavraDAO palavraDAO;
    private final List<Palavra> bancoDePalavras;
    private final Random random;
    
    
    //pontuacao
    private static final int PONTOS_BASE_RODADA = 100;
    private static final int PENALIDADE_ERRO = 15;
    private static final int BONUS_POR_SEGUNDO = 1;
    
    private int pontuacaoDaRodada;
    //Ate aqui
    
    
    private Palavra palavraAtual;
    private Set<Character> letrasCorretas;
    private Set<Character> letrasErradas;
    
    private boolean dicaUtilizada;
    
    public static final int MAX_ERROS = 6;
    
    
    public GameService(){
        logger.info("Cérebro (GameService) está sendo inicializado...");
        this.palavraDAO = new PalavraDAO();
        this.bancoDePalavras = this.palavraDAO.carregarPalavras();
        this.random = new Random();
        this.letrasCorretas = new HashSet<>();
        this.letrasErradas = new HashSet<>();
        logger.info(() -> "Cérebro pronto" + bancoDePalavras.size() + " palvras carregadas. ");
    }
    
    public void iniciarNovoJogo(){
        int indexSorteado = random.nextInt(bancoDePalavras.size());
        this.palavraAtual = bancoDePalavras.get(indexSorteado);
        
        this.letrasCorretas.clear();
        this.letrasErradas.clear();
        this.dicaUtilizada = false;
        //pontuacao
        this.pontuacaoDaRodada = PONTOS_BASE_RODADA;
        
        
        logger.info(() -> "Novo Jogo Iniciado... Palavra: " + palavraAtual.getTexto()
                + " (" + palavraAtual.getCategoria() + ")");
    }
    
    public boolean tentarLetra(char letra){
        letra= Character.toUpperCase(letra);
        
        if (letrasCorretas.contains(letra) || letrasErradas.contains(letra)) {
            logger.warning("Letra '" + letra + "' já foi tentada. Tá vendo não?? Já foi!!");
            return false;
            }
        
        if(palavraAtual.getTexto().contains(String.valueOf(letra))){
            letrasCorretas.add(letra);
            logger.info("Acertou a letra:  " + letra + "É isso meu nobre, vc vai vencer um dia!");
            return true;
        }else{
            letrasErradas.add(letra);
            //pontuacao
            this.pontuacaoDaRodada -= PENALIDADE_ERRO;
            logger.info("Erro a letra: " + letra + " Na moral, muito Bu*** !', pedi uma dica já que não sabe!" );
            return false;
        }
    }
    
    public String usarDica(){
        if(!dicaUtilizada){
            this.dicaUtilizada = true;
            logger.info("Dica Utilizada parça. Penalidade Aplicada. Nada é de Graça!!");
            return palavraAtual.getDica();
        }else{
            return "Novamente quer pegar uma dica ? A vida não te humilhou por completo ? Rala meu nobre!! ";
        }
    }
    
    public boolean perdeu() {
        return getNumeroErros() >= MAX_ERROS;
    }
    
    public boolean venceu(){
        for(char c: palavraAtual.getTexto().toCharArray()){
            if(!letrasCorretas.contains(c)){
                return false;
            }
        }
        return true;
    }
    
    public String getPalavraParcial(){
    
        StringBuilder sb = new StringBuilder();
        for(char c : palavraAtual.getTexto().toCharArray()){
            if(letrasCorretas.contains(c)){
                sb.append(c).append(" ");
            }else{
                sb.append("_ ");
            }
        }
        return sb.toString().trim();
    }
    
    public int getNumeroErros() {
        return letrasErradas.size();
    }
    
    public String getLetrasErradasFormatado(){
        return letrasErradas.toString()
                .replace("[", "")
                .replace("]", "");
    }
    
    public Palavra getPalavraAtual(){
        return palavraAtual;
    }
    
    public boolean isDicaUtilizada(){
        return dicaUtilizada; 
    }
    
    public int getPontuacaoFinal(int tempoRestante){
        
        int pontuacaoFinal = this.pontuacaoDaRodada;
        
        pontuacaoFinal +=(tempoRestante * BONUS_POR_SEGUNDO);
        
        if(dicaUtilizada){
            pontuacaoFinal /= 2;
            
        }
        return Math.max(0, pontuacaoFinal);
        
    }
    
}
