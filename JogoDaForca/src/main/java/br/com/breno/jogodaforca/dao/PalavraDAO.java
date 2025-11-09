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
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Set;
import org.bson.Document;
import com.mongodb.client.MongoCursor;


public class PalavraDAO {

    private static final Logger logger = Logger.getLogger(PalavraDAO.class.getName());
    private static final String NOME_ARQUIVO = "palavras.txt";
    private com.mongodb.client.MongoCollection<org.bson.Document> colecaoPalavras; // para o online

    public PalavraDAO() {
        try {
            com.mongodb.client.MongoDatabase db = MongoConnection.getDatabase();
            this.colecaoPalavras = db.getCollection("palavras");
            logger.info("PalavraDAO conectado à coleção 'palavras' do MongoDB.");
        } catch (Exception e) {
            this.colecaoPalavras = null;
            logger.warning("PalavraDAO iniciado em MODO OFFLINE (Não foi possível pegar a coleção 'palavras').");
        }
    }

    public List<Palavra> carregarPalavras() {
        List<Palavra> palavrasOnline = carregarPalavrasOnline();
        
        if (palavrasOnline != null && !palavrasOnline.isEmpty()) {
            // SUCESSO ONLINE
            logger.info("✅ " + palavrasOnline.size() + " palavras carregadas do MONGODB (Online).");
            return palavrasOnline;
        }
        
        // FALHA ONLINE
        logger.warning("Falha ao carregar do MongoDB. Usando backup 'palavras.txt' (Offline).");
        return carregarPalavrasOffline(); // Usa o método de backup
    }

    /**
     * (FASE 24) Tenta carregar do MongoDB.
     */
    private List<Palavra> carregarPalavrasOnline() {
        List<Palavra> palavras = new ArrayList<>();
        if (colecaoPalavras == null) {
            return null; // Esta em modo offline
        }
        
        try (com.mongodb.client.MongoCursor<org.bson.Document> cursor = colecaoPalavras.find().iterator()) {
            while (cursor.hasNext()) {
                org.bson.Document doc = cursor.next();
                palavras.add(new Palavra(
                        doc.getString("categoria"),
                        doc.getString("texto"),
                        doc.getString("dica")
                ));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, " Erro ao LER palavras do MongoDB", e);
            return null; //  forca o modo offline
        }
        return palavras;
    }

    private List<Palavra> carregarPalavrasOffline() {
        List<Palavra> palavras = new ArrayList<>();
        logger.info("Tentando carregar palavras do arquivo: " + NOME_ARQUIVO);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(NOME_ARQUIVO);
        if (inputStream == null) {
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
                    palavras.add(new Palavra(partes[0].trim(), partes[1].trim(), partes[2].trim()));
                } else {
                    logger.warning("Linha mal formatada no .txt: " + linha);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, " Erro ao ler o arquivo de palavras", e);
        }
        return palavras;
    }

    public List<String> getCategorias() {
        java.util.Set<String> nomesCategorias = new java.util.HashSet<>();
        

        List<Palavra> palavrasOffline = carregarPalavrasOffline();
        for (Palavra p : palavrasOffline) {
            nomesCategorias.add(p.getCategoria());
        }

        List<Palavra> palavrasOnline = carregarPalavrasOnline();
        if (palavrasOnline != null) {
            for (Palavra p : palavrasOnline) {
                nomesCategorias.add(p.getCategoria());
            }
        }
        return new java.util.ArrayList<>(nomesCategorias);
    }
}
