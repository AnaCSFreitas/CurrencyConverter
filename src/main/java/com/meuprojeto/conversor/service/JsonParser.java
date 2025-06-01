package com.meuprojeto.conversor.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meuprojeto.conversor.model.ExchangeRatesResponse;

// Classe responsável por parsear (converter) strings JSON em objetos Java e vice-versa
public class JsonParser {

    // Cria uma instância do Gson com formatação bonita para leitura (PrettyPrinting)
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Converte uma string JSON em um objeto ExchangeRatesResponse.
     * @param jsonString A string JSON a ser parseada.
     * @return Um objeto ExchangeRatesResponse preenchido com os dados do JSON, ou null se houver um erro.
     */
    public ExchangeRatesResponse parseJson(String jsonString) {
        try {
            return gson.fromJson(jsonString, ExchangeRatesResponse.class); // Converte a string JSON para o objeto
        } catch (Exception e) {
            System.err.println("Erro ao parsear JSON: " + e.getMessage());
            return null; // Retorna nulo em caso de erro no parsing
        }
    }
}