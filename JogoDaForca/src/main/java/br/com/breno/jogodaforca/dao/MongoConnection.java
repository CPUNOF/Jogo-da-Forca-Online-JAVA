package br.com.breno.jogodaforca.dao;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoConnection {

    // logger de eventos
    private static final Logger logger = Logger.getLogger(MongoConnection.class.getName());


    private static MongoClient client;
    private static MongoDatabase database;


    private MongoConnection() {
    }

    public static void conectar(String connectionString, String dbName) {
        // try-catch parar erros de conexão
        try {
            if (client == null) {
                logger.info("Iniciando conexão com o MongoDB...");           
 
                client = MongoClients.create(connectionString);
                
                database = client.getDatabase(dbName);
                
                logger.info("✅ Conexão com MongoDB estabelecida com sucesso!");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "⛔ Falha ao conectar no MongoDB", e);
            
            throw new RuntimeException("Não foi possível conectar ao banco de dados.", e);
        }
    }

    public static MongoDatabase getDatabase() {
        if (database == null) {
            throw new IllegalStateException("A conexão com o MongoDB não foi iniciada. Chame MongoConnection.conectar() primeiro.");
        }
        return database;
    }

    public static void fechar() {
        if (client != null) {
            logger.info("Fechando conexão com o MongoDB...");
            client.close();
            // Limpa as variáveis estáticas
            client = null;
            database = null;
        }
    }
}