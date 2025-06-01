package com.meuprojeto.conversor.service;

// Classe responsável por realizar o cálculo da conversão de moedas
public class CurrencyConverter {

    /**
     * Converte um valor monetário de uma moeda para outra.
     * @param amount O valor original a ser convertido.
     * @param exchangeRate A taxa de câmbio (valor da moeda destino por unidade da moeda de origem).
     * @return O valor convertido.
     */
    public double convert(double amount, double exchangeRate) {
        return amount * exchangeRate;
    }
}