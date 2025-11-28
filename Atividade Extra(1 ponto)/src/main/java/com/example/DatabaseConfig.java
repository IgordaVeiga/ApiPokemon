//Classe que cria o banco de dados
package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {


    public static Connection getConnection() throws SQLException{
        try {
            //carrega o driver
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:pokemon_database.db");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC do sqlite não encontrado");
            throw new SQLException("Driver not found", e);
            // TODO: handle exception
        }
    }

    public static void createTable(){
        String sql= """
            CREATE TABLE IF NOT EXISTS pokemons (
                id INTEGER PRIMARY KEY,
                name TEXT NOT NULL,
                height REAL,
                weight REAL
            );
            """;

            try (Connection conn = getConnection()) {
                java.sql.Statement stmt = conn.createStatement();

                stmt.execute(sql);
                System.out.println("Tabela pokemons verificada/criada com sucesso");
            } catch (SQLException e) {
                System.out.println("Erro ao criar a tabela"+ e.getMessage());
                // TODO: handle exception
            }
    }

}
