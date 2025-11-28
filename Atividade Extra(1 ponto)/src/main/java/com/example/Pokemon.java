package com.example;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {
    
    // Atributos JSON
    private int id;
    private String name;
    private int height; // Altura 
    private int weight; // Peso 

    // Estrutura pra mapear as habilidades
    private List<AbilityWrapper> abilities;

    // Construtor Vazio
    public Pokemon() {}

    // Construtor Padrão
    public Pokemon(int id, String name, int height, int weight, List<AbilityWrapper> abilities) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.abilities = abilities;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getHeight() { return height; }
    public int getWeight() { return weight; }
    public List<AbilityWrapper> getAbilities() { return abilities; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setHeight(int height) { this.height = height; }
    public void setWeight(int weight) { this.weight = weight; }
    public void setAbilities(List<AbilityWrapper> abilities) { this.abilities = abilities; }

    @Override
    public String toString() {
        // converte as unidades
        String heightFormatted = String.format("%.1f m", this.height / 10.0);
        String weightFormatted = String.format("%.1f kg", this.weight / 10.0);

        StringBuilder abilitiesList = new StringBuilder();
        if (abilities != null) {
             for (AbilityWrapper wrapper : abilities) {
                abilitiesList.append(wrapper.getAbility().getName()).append(", ");
            }
        }
        String finalAbilities = abilitiesList.length() > 0 ? 
                                abilitiesList.substring(0, abilitiesList.length() - 2) : 
                                "Nenhuma";

        return "\n--- Detalhes do Pokémon ---" +
               "\nNome: " + name.toUpperCase() +
               "\nID (Pokedex): " + id +
               "\nAltura: " + heightFormatted + 
               "\nPeso: " + weightFormatted +
               "\nHabilidades: " + finalAbilities +
               "\n---------------------------";
    }
    //Garante que apenas atributos definidos na classe vão ser listados
    @JsonIgnoreProperties (ignoreUnknown = true)
    //Mapeia a estrutura do JSON
    public static class AbilityWrapper {
        private NamedApiResource ability;
        
        public NamedApiResource getAbility() { return ability; }
        public void setAbility(NamedApiResource ability) { this.ability = ability; }
    }
    
    @JsonIgnoreProperties (ignoreUnknown = true)
    public static class NamedApiResource {
        private String name;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}