# ApiPokemon

Este é um projeto individual desenvolvido em Java (Maven) que demonstra o consumo de uma API externa (PokeAPI) e a persistência de dados em um banco de dados relacional (SQLite), utilizando a arquitetura em camadas e o padrão CRUD.

##  Requisitos Técnicos do Sistema

* **Consumo de API:** Busca dados de Pokémons na **PokeAPI** usando `HttpClient` e converte o JSON para objetos Java usando a biblioteca **Jackson**.
* **POO e Modelagem:** Classes bem definidas (`Pokemon`, `PokemonDAO`, `DatabaseConfig`).
* **Persistência:** CRUD completo (Create, Read, Update, Delete) com **JDBC** e banco de dados **SQLite**.
* **Interface:** Console interativo via Menu.

##  Pré-requisitos

Para compilar e executar o projeto, você precisa ter instalado:

1.  **Java Development Kit (JDK):** Versão 17 ou superior.
2.  **Apache Maven:** Versão 3.x (necessário para gerenciar dependências e compilar).

##  Como Executar o Sistema

Siga os passos abaixo para construir e rodar a aplicação:

### Passo 1: Compilar e Empacotar

Abra o terminal (ou Prompt de Comando/PowerShell) na pasta **raiz** do projeto (onde está o `pom.xml`) e execute o comando:

```bash
mvn clean package
