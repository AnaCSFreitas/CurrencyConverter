package com.meuprojeto.conversor.service;

import com.meuprojeto.conversor.model.ExchangeRatesResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Classe responsável por filtrar as taxas de câmbio recebidas da API
public class CurrencyFilter {

    // Lista das moedas que nosso aplicativo vai suportar (as 6 mínimas exigidas)
    private static final List<String> SUPPORTED_CURRENCIES = Arrays.asList(
            "ARS", "BOB", "BRL", "CLP", "COP", "USD"
    );

    /**
     * Filtra as taxas de câmbio de uma resposta da API, mantendo apenas as moedas suportadas.
     * @param response O objeto ExchangeRatesResponse parseado da API.
     * @return Um mapa contendo apenas as taxas das moedas suportadas.
     */
    public Map<String, Double> getFilteredRates(ExchangeRatesResponse response) {
        // Verifica se a resposta ou as taxas de conversão são nulas
        if (response == null || response.getConversionRates() == null) {
            System.err.println("Erro: Resposta da API ou taxas de conversão são nulas.");
            return null;
        }

        // Filtra o mapa de taxas para incluir apenas as moedas que estão na lista SUPPORTED_CURRENCIES
        Map<String, Double> filtered = response.getConversionRates().entrySet().stream()
                .filter(entry -> SUPPORTED_CURRENCIES.contains(entry.getKey())) // Mantém apenas entradas com moedas suportadas
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); // Coleta em um novo mapa

        if (filtered.isEmpty()) {
            System.err.println("Nenhuma das moedas suportadas foi encontrada na resposta da API.");
        }
        return filtered;
    }

    // Método para obter a lista de moedas suportadas
    public static List<String> getSupportedCurrencies() {
        return SUPPORTED_CURRENCIES;
    }
}