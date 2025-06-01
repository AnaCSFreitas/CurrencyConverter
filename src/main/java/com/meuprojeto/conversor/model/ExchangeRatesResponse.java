package com.meuprojeto.conversor.model;

import java.util.Map;

// Esta classe representa a estrutura da resposta JSON que recebemos da API
public class ExchangeRatesResponse {
    private String result; // Campo 'result' do JSON (ex: "success" ou "error")
    private String base_code; // Campo 'base_code' do JSON (a moeda base da qual as taxas são calculadas)
    private Map<String, Double> conversion_rates; // Campo 'conversion_rates' do JSON (um mapa de moedas e suas taxas)

    // Getters e Setters (métodos para acessar e modificar os campos)
    // O Gson usa esses métodos para preencher o objeto com os dados do JSON

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getBaseCode() {
        return base_code;
    }

    public void setBaseCode(String base_code) {
        this.base_code = base_code;
    }

    public Map<String, Double> getConversionRates() {
        return conversion_rates;
    }

    public void setConversionRates(Map<String, Double> conversion_rates) {
        this.conversion_rates = conversion_rates;
    }
}