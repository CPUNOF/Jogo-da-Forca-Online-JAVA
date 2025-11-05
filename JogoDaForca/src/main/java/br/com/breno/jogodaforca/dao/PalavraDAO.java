/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.breno.jogodaforca.dao;

import br.com.breno.jogodaforca.model.Palavra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PalavraDAO {
    
    private static final Logger logger = Logger.getLogger(PalavraDAO.class.getName());
    private static final String NOME_ARQUIVO = "palavras.txt";

    public List<Palavra> carregarPalavras() {
        List<Palavra> palavras = new ArrayList<>();
        logger.info("Tentando carregar palavras do arquivo: " + NOME_ARQUIVO);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(NOME_ARQUIVO);

        if (inputStream == null) {
            logger.log(Level.SEVERE, "⛔ Arquivo de palavras '" + NOME_ARQUIVO + "' não encontrado!");
            palavras.add(new Palavra("ERRO", "ARQUIVO", "O arquivo .txt não foi encontrado."));
            return palavras;
        }

        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(reader)) {

            String linha;
            while ((linha = br.readLine()) != null) {
                
                if (linha.isBlank() || linha.trim().startsWith("#")) {
                    continue; 
                }

                String[] partes = linha.split(";");


                if (partes.length == 3) { 
                    String categoria = partes[0].trim();
                    String texto = partes[1].trim();
                    String dica = partes[2].trim();
                    
                    palavras.add(new Palavra(categoria, texto, dica));
                } else {
                    logger.warning("Linha mal formatada no .txt: " + linha);
                }
            }
            
            logger.info("✅ " + palavras.size() + " palavras carregadas do arquivo.");

        } catch (IOException e) {
            logger.log(Level.SEVERE, "⛔ Erro ao ler o arquivo de palavras", e);
        }

        return palavras;
    }
}
