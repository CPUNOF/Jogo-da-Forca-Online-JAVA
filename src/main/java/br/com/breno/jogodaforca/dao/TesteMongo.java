package br.com.breno.jogodaforca.dao;

public class TesteMongo {
    public static void main(String[] args) {
        String conn = "mongodb+srv://appUser:breno123@cluster0.pyhhwz9.mongodb.net/?appName=Cluster0";

        // 1. Conecta ao cluster remoto
        MongoConnection.conectar(conn, "jogodaforca");

        // 2. Cria o DAO de ranking
        RemoteRankingDAO dao = new RemoteRankingDAO();

        // 3. (Opcional) salva uma partida para testar
        // dao.salvarPartida(new Partida("playerId123", 150));

        // 4. Lista o top 10
        System.out.println("Conectado! Top10: " + dao.top10().size());

        // 5. Fecha conexão
        MongoConnection.fechar();
    }
}
