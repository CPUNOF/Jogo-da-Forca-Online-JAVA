/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.breno.jogodaforca.dao;

import br.com.breno.jogodaforca.model.Partida;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;

/**
 *
 * @author breno
 */
public class RemoteRankingDAO {
    
    private final MongoCollection<Document> colecao;
    
    public RemoteRankingDAO(){
        MongoDatabase db = MongoConnection.getDatabase();
        
        if(db != null){
            this.colecao = db.getCollection("ranking");
        }else{
            this.colecao = null;
            Logger.getLogger(RemoteRankingDAO.class.getName())
                    .warning("RankingDAO iniciado em MODO OFFLINE. Não será possível salvar ou ler.");
        }
    }
    
    public void salvarPartida(Partida partida){
        if(colecao == null){
            Logger.getLogger(RemoteRankingDAO.class.getName())
                    .severe("Falha ao salvar: Ranking está em modo offline.");
            throw new IllegalStateException("Não é possível salvar o ranking, o jogo está offline.");
        }
        
        Document doc = new Document()
                .append("Jogador", partida.getJogadorNome())
                .append("Pontuacao", partida.getPontuacao())
                .append("PalavrasVencidas", partida.getPalavrasVencidas())
                .append("DicasUsadas", partida.getDicasUsadas())
                .append("Data", partida.getData());
        
        colecao.insertOne(doc);
        Logger.getLogger(RemoteRankingDAO.class.getName())
                .info("Partida salva no MongoDB!");
    }
    
    public List<Partida> top25(){
        List<Partida> lista = new ArrayList();
        
        if(colecao == null){
            Logger.getLogger(RemoteRankingDAO.class.getName())
                    .warning("Falha ao ler: Ranking está em modo offline.");
            return lista;
        }
        
        try (MongoCursor<Document> cursor = colecao.find()
                .sort(orderBy(
                    descending("Pontuacao"),        
                    descending("PalavrasVencidas"), 
                    ascending("DicasUsadas")        
                ))
                .limit(25)
                .iterator()){
            while(cursor.hasNext()){
                Document doc = cursor.next();
                Partida p = new Partida(
                        doc.getString("Jogador"),
                        doc.getInteger("Pontuacao"),
                        doc.getInteger("PalavrasVencidas", 0),
                        doc.getInteger("DicasUsadas", 0),
                        doc.getString("Data")
                );
                lista.add(p);
            }
        }
        return lista;
    }
    
    
    
}
