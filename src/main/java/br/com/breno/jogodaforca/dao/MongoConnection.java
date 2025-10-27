package br.com.breno.jogodaforca.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {
    private static MongoClient client;
    private static MongoDatabase database;

    public static void conectar(String connectionString, String dbName) {
        client = MongoClients.create(connectionString);
        database = client.getDatabase(dbName);
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static void fechar() {
        if (client != null) client.close();
    }
}
