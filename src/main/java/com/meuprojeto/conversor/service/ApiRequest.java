package com.meuprojeto.conversor.service;

import java.net.URI;
import java.net.http.HttpRequest;

// Classe responsável por construir as requisições HTTP para a API
public class ApiRequest {

    private final String apiKey; // A chave da API para autenticação
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/"; // URL base da API

    // Construtor que recebe a chave da API
    public ApiRequest(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Cria uma requisição HTTP para obter as taxas de câmbio de uma moeda base.
     * @param baseCurrency O código da moeda base (ex: "USD", "BRL").
     * @return Um objeto HttpRequest configurado.
     */
    public HttpRequest createRequest(String baseCurrency) {
        // Monta a URL completa para a requisição
        String url = BASE_URL + apiKey + "/latest/" + baseCurrency;
        return HttpRequest.newBuilder() // Inicia a construção da requisição
                .uri(URI.create(url)) // Define a URI (URL) da requisição
                .GET() // Define o método HTTP como GET (para buscar dados)
                .build(); // Constrói o objeto HttpRequest
    }
}