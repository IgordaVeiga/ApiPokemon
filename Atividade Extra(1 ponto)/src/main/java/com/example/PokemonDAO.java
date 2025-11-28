package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Não é necessário importar javax.xml.crypto.Data;

public class PokemonDAO {


    public void create(Pokemon pokemon) {

        String sql = "INSERT INTO pokemons (id, name, height, weight) VALUES (?, ?, ?, ?)";


        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pokemon.getId());
            stmt.setString(2, pokemon.getName());

            stmt.setDouble(3, pokemon.getHeight() / 10.0);
            stmt.setDouble(4, pokemon.getWeight() / 10.0);

            stmt.executeUpdate();
            System.out.println("Pokemon " + pokemon.getName() + " salvo com sucesso.");
            
        } catch (SQLException e) {
            //Quando o resultado retorna 19, ele tá duplicado
            if (e.getErrorCode() == 19) {
                System.out.println(" Pokemon " + pokemon.getName() + " já existe no banco (ID duplicado).");
            } else {
                System.err.println("Erro ao salvar Pokémon: " + e.getMessage());
            }
        }
    }


    public List<Pokemon> readAll() {

        String sql = "SELECT id, name, height, weight FROM pokemons";
        List<Pokemon> pokemons = new ArrayList<>();


        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pokemon pokemon = new Pokemon();
                pokemon.setId(rs.getInt("id"));
                pokemon.setName(rs.getString("name"));
                
                pokemon.setHeight((int)(rs.getDouble("height") * 10));
                pokemon.setWeight((int)(rs.getDouble("weight") * 10));

                pokemons.add(pokemon);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar Pokémons: " + e.getMessage());
        }
        
        return pokemons;
    }


    public void updateWeight(int id, int newWeight) {

        String sql = "UPDATE pokemons SET weight = ? WHERE id = ?";
        
        try(Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setDouble(1, newWeight / 10.0);
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pokemon ID " + id + " atualizado com sucesso! Novo peso: " + (newWeight/10.0) + " kg.");
            } else {
                System.out.println("Nenhum Pokemon encontrado com o ID " + id + " para atualização.");
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar Pokemon:" + e.getMessage());
        }
    }


    public void delete(int id) {
        String sql = "DELETE FROM pokemons WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Pokemon ID " + id + " excluído com sucesso.");
            } else {
                System.out.println("Nenhum Pokemon encontrado com o ID " + id + " para exclusão.");
            }
                
        } catch (SQLException e) {
            System.err.println("Erro ao excluir Pokemon " + e.getMessage());
        }
    }
}