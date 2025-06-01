package com.meuprojeto.conversor;

import com.meuprojeto.conversor.model.ConversionRecord;
import com.meuprojeto.conversor.model.ExchangeRatesResponse;
import com.meuprojeto.conversor.service.*; // Importa todas as classes do pacote service

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    // ATENÇÃO: SUBSTITUA "SUA_CHAVE_DE_API_AQUI" PELA SUA CHAVE REAL DA EXCHANGERATE-API!
    private static final String API_KEY = "0038292d0637f1e6f1f74ce1"; // <<< SUA CHAVE AQUI
    private static final String BASE_API_CURRENCY = "USD"; // Moeda base para buscar na API (pode ser BRL, EUR, etc.)

    private static final Scanner scanner = new Scanner(System.in);
    private static ApiService apiService = new ApiService(API_KEY);
    private static JsonParser jsonParser = new JsonParser();
    private static CurrencyFilter currencyFilter = new CurrencyFilter();
    private static CurrencyConverter currencyConverter = new CurrencyConverter();
    private static Map<String, Double> rates; // Armazena as taxas filtradas
    private static List<ConversionRecord> history = new ArrayList<>(); // Histórico de conversões

    public static void main(String[] args) {
        System.out.println("Iniciando Conversor de Moedas...");

        if (API_KEY.equals("SUA_CHAVE_DE_API_AQUI")) {
            System.err.println("\n-------------------------------------------------------------");
            System.err.println("ERRO CRÍTICO: Por favor, substitua 'SUA_CHAVE_DE_API_AQUI' ");
            System.err.println("no arquivo Main.java pela sua chave real da ExchangeRate-API.");
            System.err.println("Obtenha sua chave gratuita em: https://www.exchangerate-api.com/");
            System.err.println("-------------------------------------------------------------\n");
            return; // Sai do programa
        }

        if (!loadExchangeRates()) {
            System.out.println("Não foi possível carregar as taxas de câmbio. Verifique sua conexão ou chave de API.");
            return; // Sai do programa se não carregar as taxas
        }

        int choice = -1;
        while (choice != 0) {
            displayMenu();
            try {
                System.out.print("Escolha uma opção: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha após o nextInt()

                processChoice(choice);

            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine(); // Limpar o buffer do scanner
            } catch (IOException | InterruptedException e) {
                System.err.println("Erro ao obter taxas de câmbio: " + e.getMessage());
                System.out.println("Tente novamente mais tarde.");
                // choice = 0; // Opcional: Sair do programa em caso de erro grave da API
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace(); // Para debug, remova em produção
            }
            System.out.println(); // Linha em branco para melhor leitura
        }
        System.out.println("Obrigado por usar o Conversor de Moedas. Até mais!");
        scanner.close();
    }

    private static boolean loadExchangeRates() {
        try {
            System.out.println("Carregando taxas de câmbio para " + BASE_API_CURRENCY + "...");
            String jsonResponse = apiService.getExchangeRates(BASE_API_CURRENCY);
            ExchangeRatesResponse response = jsonParser.parseJson(jsonResponse);

            if (response != null && "success".equals(response.getResult())) {
                rates = currencyFilter.getFilteredRates(response);
                if (rates != null && !rates.isEmpty()) {
                    System.out.println("Taxas carregadas com sucesso!");
                    return true;
                } else {
                    System.out.println("Nenhuma taxa de moeda suportada encontrada ou erro ao filtrar.");
                    return false;
                }
            } else {
                System.out.println("Erro na resposta da API: " + (response != null ? response.getResult() : "Resposta nula ou inválida"));
                return false;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao carregar taxas iniciais: " + e.getMessage());
            return false;
        }
    }

    private static void displayMenu() {
        System.out.println("------------------------------------------");
        System.out.println("            CONVERSOR DE MOEDAS           ");
        System.out.println("------------------------------------------");
        System.out.println("1. Dólar (USD) para Real (BRL)");
        System.out.println("2. Real (BRL) para Dólar (USD)");
        System.out.println("3. Dólar (USD) para Peso Argentino (ARS)");
        System.out.println("4. Peso Argentino (ARS) para Dólar (USD)");
        System.out.println("5. Dólar (USD) para Boliviano Boliviano (BOB)");
        System.out.println("6. Boliviano Boliviano (BOB) para Dólar (USD)");
        System.out.println("7. Dólar (USD) para Peso Chileno (CLP)");
        System.out.println("8. Peso Chileno (CLP) para Dólar (USD)");
        System.out.println("9. Dólar (USD) para Peso Colombiano (COP)");
        System.out.println("10. Peso Colombiano (COP) para Dólar (USD)");
        System.out.println("11. Ver Histórico de Conversões");
        System.out.println("0. Sair");
        System.out.println("------------------------------------------");
    }

    private static void processChoice(int choice) throws IOException, InterruptedException {
        if (choice == 0) {
            return; // Sair do loop principal
        }

        if (choice == 11) {
            displayHistory();
            return;
        }

        String fromCurrency = "";
        String toCurrency = "";

        switch (choice) {
            case 1: fromCurrency = "USD"; toCurrency = "BRL"; break;
            case 2: fromCurrency = "BRL"; toCurrency = "USD"; break;
            case 3: fromCurrency = "USD"; toCurrency = "ARS"; break;
            case 4: fromCurrency = "ARS"; toCurrency = "USD"; break;
            case 5: fromCurrency = "USD"; toCurrency = "BOB"; break;
            case 6: fromCurrency = "BOB"; toCurrency = "USD"; break;
            case 7: fromCurrency = "USD"; toCurrency = "CLP"; break;
            case 8: fromCurrency = "CLP"; toCurrency = "USD"; break;
            case 9: fromCurrency = "USD"; toCurrency = "COP"; break;
            case 10: fromCurrency = "COP"; toCurrency = "USD"; break;
            default:
                System.out.println("Opção inválida. Por favor, escolha uma opção do menu.");
                return;
        }

        System.out.printf("Digite o valor em %s para converter para %s: ", fromCurrency, toCurrency);
        double amount;
        try {
            amount = scanner.nextDouble();
            scanner.nextLine(); // Consumir a nova linha
            if (amount < 0) {
                System.out.println("O valor não pode ser negativo.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Valor inválido. Por favor, digite um número.");
            scanner.nextLine(); // Limpar o buffer
            return;
        }

        Double rateFrom = rates.get(fromCurrency);
        Double rateTo = rates.get(toCurrency);

        if (rateFrom == null || rateTo == null) {
            System.out.println("Erro: Taxa de câmbio para uma das moedas não encontrada. Tente carregar novamente as taxas.");
            return;
        }

        double convertedAmount;
        if (fromCurrency.equals(BASE_API_CURRENCY)) { // Ex: USD para BRL (se USD é a BASE_API_CURRENCY)
            convertedAmount = currencyConverter.convert(amount, rateTo);
        } else if (toCurrency.equals(BASE_API_CURRENCY)) { // Ex: BRL para USD (se USD é a BASE_API_CURRENCY)
            convertedAmount = amount / rateFrom;
        } else { // Ex: BRL para ARS (ambos não são a BASE_API_CURRENCY)
            // 1. Converter de fromCurrency para BASE_API_CURRENCY
            double amountInBase = amount / rateFrom;
            // 2. Converter de BASE_API_CURRENCY para toCurrency
            convertedAmount = currencyConverter.convert(amountInBase, rateTo);
        }

        System.out.printf("Valor convertido: %.2f %s = %.2f %s\n", amount, fromCurrency, convertedAmount, toCurrency);
        // Adiciona ao histórico e registra no log
        history.add(new ConversionRecord(fromCurrency, toCurrency, amount, convertedAmount));
        Logger.logConversion(fromCurrency, toCurrency, amount, convertedAmount);
    }

    private static void displayHistory() {
        if (history.isEmpty()) {
            System.out.println("Nenhuma conversão realizada ainda.");
        } else {
            System.out.println("\n--- Histórico de Conversões ---");
            for (ConversionRecord record : history) {
                System.out.println(record);
            }
            System.out.println("-------------------------------\n");
        }
    }
}