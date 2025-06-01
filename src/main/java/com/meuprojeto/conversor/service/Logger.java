package com.meuprojeto.conversor.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_FILE = "conversion_log.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logConversion(String fromCurrency, String toCurrency, double originalAmount, double convertedAmount) {
        String logMessage = String.format("[%s] CONVERSION: %.2f %s -> %.2f %s\n",
                LocalDateTime.now().format(FORMATTER),
                originalAmount, fromCurrency, convertedAmount, toCurrency);

        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(logMessage);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no log: " + e.getMessage());
        }
    }
}