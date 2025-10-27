package br.com.breno.jogodaforca.dao;

import br.com.breno.jogodaforca.model.Partida;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Sorts.descending;

public class RemoteRankingDAO {

    private final MongoCollection<Document> colecao;

    public RemoteRankingDAO() {
        MongoDatabase db = MongoConnection.getDatabase();
        colecao = db.getCollection("ranking");
    }

    public void salvarPartida(Partida partida) {
        Document doc = new Document()
                .append("jogadorId", partida.getJogadorId())
                .append("pontuacao", partida.getPontuacao())
                .append("data", partida.getData());
        colecao.insertOne(doc);
        System.out.println("✅ Partida salva no MongoDB!");
    }

    public List<Partida> top10() {
        List<Partida> lista = new ArrayList<>();
        try (MongoCursor<Document> cursor = colecao.find()
                .sort(descending("pontuacao"))
                .limit(10)
                .iterator()) {

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Partida p = new Partida(
                        doc.getString("jogadorId"),
                        doc.getInteger("pontuacao"),
                        doc.getString("data")
                );
                lista.add(p);
            }
        }
        return lista;
    }
}
