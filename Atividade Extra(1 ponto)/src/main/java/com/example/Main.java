package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    

    private static final PokemonDAO dao = new PokemonDAO();
    private static final Scanner scanner = new Scanner(System.in);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        
        // 1. Configuração Inicial do Banco de Dados
        DatabaseConfig.createTable();
        
        System.out.println("=================================================");
        System.out.println("  SISTEMA DE GERENCIAMENTO DE POKÉMONS (CRUD + API)");
        System.out.println("=================================================");

        int option = -1;
        while (option != 0) {
            displayMenu();
            
            try {
                System.out.print("Escolha uma opção: ");
                option = scanner.nextInt();
                scanner.nextLine();


                switch (option) {
                    case 1:
                        fetchAndCreatePokemon();
                        break;
                    case 2:
                        readAllPokemons();
                        break;
                    case 3:
                        updatePokemonWeight(); 
                        break;
                    case 4:
                        deletePokemon();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine(); 
                option = -1;
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n--- MENU DE OPERAÇÕES ---");
        System.out.println("1. Consumir API e Salvar Novo Pokémon (CREATE)");
        System.out.println("2. Listar todos os Pokémons salvos (READ)");
        System.out.println("3. Atualizar Peso de um Pokémon (UPDATE)");
        System.out.println("4. Excluir Pokémon (DELETE)");
        System.out.println("0. Sair");
        System.out.println("-------------------------");
    }

    // CRUD

    private static void fetchAndCreatePokemon() throws Exception {
        System.out.print("Digite o NOME ou ID do Pokémon a buscar na API (Ex: pikachu, 1): ");
        String nameOrId = scanner.nextLine().toLowerCase();
        
 
        Pokemon pokemon = fetchPokemonFromApi(nameOrId);
        
        if (pokemon != null) {
            System.out.println(pokemon.toString());
            

            dao.create(pokemon);
        }
    }

    private static void readAllPokemons() {
        List<Pokemon> pokemons = dao.readAll();
        
        if (pokemons.isEmpty()) {
            System.out.println("Não há Pokémons salvos no banco de dados.");
            return;
        }

        System.out.println("\n--- LISTA DE POKÉMONS SALVOS (" + pokemons.size() + ") ---");
        for (Pokemon p : pokemons) {
            System.out.println(p.toString());
        }
        System.out.println("----------------------------------------");
    }

    private static void updatePokemonWeight() {
        try {
            System.out.print("Digite o ID do Pokémon para atualizar: ");
            int id = scanner.nextInt();

            System.out.print("Digite o NOVO PESO em hectogramas (hg) - Ex: 150 para 15.0 kg: "); 
            int newWeight = scanner.nextInt();
            scanner.nextLine(); 

            dao.updateWeight(id, newWeight);
        } catch (InputMismatchException e) {
            System.err.println("Entrada inválida. O ID e o peso devem ser números inteiros.");
            scanner.nextLine(); 
        }
    }

    private static void deletePokemon() {
        try {
            System.out.print("Digite o ID do Pokémon para EXCLUIR: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            dao.delete(id);
        } catch (InputMismatchException e) {
            System.err.println("Entrada inválida. O ID deve ser um número inteiro.");
            scanner.nextLine(); 
        }
    }


    private static Pokemon fetchPokemonFromApi(String nameOrId) {
        String apiEndpoint = "https://pokeapi.co/api/v2/pokemon/" + nameOrId;
        
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(apiEndpoint))
                .GET()
                .header("Accept", "application/json")
                .build();

             HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
             
             if (httpResponse.statusCode() == 200) {
                 Pokemon pokemon = mapper.readValue(httpResponse.body(), Pokemon.class);
                 System.out.println("Dados da API obtidos com sucesso para " + pokemon.getName().toUpperCase() + ".");
                 return pokemon;
             } else {
                 System.err.println("Falha ao buscar Pokémon na API. Status: " + httpResponse.statusCode() + ". Verifique se o nome/ID está correto.");
                 return null;
             }
        } catch (Exception e) {
            System.err.println("Erro durante a requisição da API. Verifique a conexão.");
            return null;
        }
    }
}