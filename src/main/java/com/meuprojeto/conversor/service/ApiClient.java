package com.meuprojeto.conversor.service;

import java.net.http.HttpClient;

// Classe responsável por criar e gerenciar uma instância do HttpClient
// O HttpClient é usado para enviar requisições HTTP (para a API)
public class ApiClient {

    // Cria uma única instância do HttpClient para ser reutilizada
    private static final HttpClient client = HttpClient.newHttpClient();

    // Método público para obter a instância do HttpClient
    public static HttpClient getClient() {
        return client;
    }
}