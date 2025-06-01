package com.meuprojeto.conversor.service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Classe de serviço que orquestra a chamada à API e lida com a resposta
public class ApiService {

    private final ApiRequest apiRequest; // Instância da classe que constrói a requisição

    // Construtor que recebe a chave da API e inicializa ApiRequest
    public ApiService(String apiKey) {
        this.apiRequest = new ApiRequest(apiKey);
    }

    /**
     * Faz uma requisição à API de taxas de câmbio para uma moeda base.
     * @param baseCurrency O código da moeda base para buscar as taxas.
     * @return O corpo da resposta HTTP em formato JSON (String).
     * @throws IOException Se ocorrer um erro de I/O (ex: problema de conexão).
     * @throws InterruptedException Se a operação for interrompida.
     */
    public String getExchangeRates(String baseCurrency) throws IOException, InterruptedException {
        HttpClient client = ApiClient.getClient(); // Obtém a instância do HttpClient
        HttpRequest request = apiRequest.createRequest(baseCurrency); // Cria a requisição

        // Envia a requisição e recebe a resposta, lendo o corpo como String
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Verifica o código de status da resposta HTTP
        if (response.statusCode() == 200) { // Código 200 significa SUCESSO
            return response.body(); // Retorna o corpo da resposta JSON
        } else { // Se o status não for 200, houve um erro
            String errorMessage = "Erro na requisição: Status " + response.statusCode();
            if (response.body() != null && !response.body().isEmpty()) {
                errorMessage += " - " + response.body(); // Adiciona o corpo da resposta para mais detalhes do erro
            }
            throw new IOException(errorMessage); // Lança uma exceção para indicar o erro
        }
    }
}