package com.meuprojeto.conversor.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversionRecord {
    private String fromCurrency;
    private String toCurrency;
    private double originalAmount;
    private double convertedAmount;
    private LocalDateTime timestamp;

    public ConversionRecord(String fromCurrency, String toCurrency, double originalAmount, double convertedAmount) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
        this.timestamp = LocalDateTime.now();
    }

    public String getFromCurrency() { return fromCurrency; }
    public String getToCurrency() { return toCurrency; }
    public double getOriginalAmount() { return originalAmount; }
    public double getConvertedAmount() { return convertedAmount; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("[%s] %.2f %s -> %.2f %s",
                timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                originalAmount, fromCurrency, convertedAmount, toCurrency);
    }
}