package com.conversormoedas;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe que representa as moedas disponíveis para conversão.
 */
public class Moeda {
    private String codigo;
    private String nome;
    
    // Mapa de moedas suportadas
    private static final Map<String, Moeda> MOEDAS_SUPORTADAS = new HashMap<>();
    
    // Inicializa as moedas suportadas
    static {
        // Moedas principais
        adicionarMoeda("USD", "Dólar Americano");
        adicionarMoeda("EUR", "Euro");
        adicionarMoeda("GBP", "Libra Esterlina");
        adicionarMoeda("JPY", "Iene Japonês");
        adicionarMoeda("CAD", "Dólar Canadense");
        adicionarMoeda("AUD", "Dólar Australiano");
        adicionarMoeda("CHF", "Franco Suíço");
        adicionarMoeda("CNY", "Yuan Chinês");
        adicionarMoeda("BRL", "Real Brasileiro");
        adicionarMoeda("INR", "Rupia Indiana");
        
        // Moedas adicionais
        adicionarMoeda("MXN", "Peso Mexicano");
        adicionarMoeda("SGD", "Dólar de Singapura");
        adicionarMoeda("NZD", "Dólar Neozelandês");
        adicionarMoeda("HKD", "Dólar de Hong Kong");
        adicionarMoeda("SEK", "Coroa Sueca");
        adicionarMoeda("KRW", "Won Sul-Coreano");
        adicionarMoeda("PLN", "Zloty Polonês");
        adicionarMoeda("NOK", "Coroa Norueguesa");
        adicionarMoeda("RUB", "Rublo Russo");
        adicionarMoeda("TRY", "Lira Turca");
    }
    
    /**
     * Construtor para criar uma Moeda.
     * 
     * @param codigo Código da moeda (ex: USD, BRL)
     * @param nome Nome da moeda (ex: Dólar Americano, Real Brasileiro)
     */
    private Moeda(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }
    
    /**
     * Adiciona uma moeda ao mapa de moedas suportadas.
     * 
     * @param codigo Código da moeda
     * @param nome Nome da moeda
     */
    private static void adicionarMoeda(String codigo, String nome) {
        MOEDAS_SUPORTADAS.put(codigo, new Moeda(codigo, nome));
    }
    
    /**
     * Obtém uma moeda pelo código.
     * 
     * @param codigo Código da moeda
     * @return A moeda correspondente ou null se não existir
     */
    public static Moeda obterPorCodigo(String codigo) {
        return MOEDAS_SUPORTADAS.get(codigo);
    }
    
    /**
     * Verifica se um código de moeda é válido.
     * 
     * @param codigo Código da moeda
     * @return true se o código for válido, false caso contrário
     */
    public static boolean codigoValido(String codigo) {
        return codigo != null && MOEDAS_SUPORTADAS.containsKey(codigo);
    }
    
    /**
     * Obtém todas as moedas suportadas.
     * 
     * @return Um array de moedas suportadas
     */
    public static Moeda[] obterTodas() {
        return MOEDAS_SUPORTADAS.values().toArray(new Moeda[0]);
    }
    
    /**
     * Obtém o código da moeda.
     * 
     * @return O código da moeda
     */
    public String getCodigo() {
        return codigo;
    }
    
    /**
     * Obtém o nome da moeda.
     * 
     * @return O nome da moeda
     */
    public String getNome() {
        return nome;
    }
    
    @Override
    public String toString() {
        return codigo + " - " + nome;
    }
}