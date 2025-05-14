package com.conversormoedas;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.json.JSONObject;

/**
 * Classe responsável por realizar conversões de moeda usando a API de taxas de câmbio.
 */
public class Conversor {
    // URL da API gratuita que não requer chave de acesso
    private static final String URL_API = "https://open.er-api.com/v6/latest/";
    
    private final HttpClient clienteHttp;
    
    /**
     * Resultado da conversão.
     */
    public static class ResultadoConversao {
        private double valor;
        private double valorConvertido;
        private double taxaCambio;
        private String dataAtualizacao;
        private Moeda moedaOrigem;
        private Moeda moedaDestino;
        
        /**
         * Construtor para criar um resultado de conversão.
         */
        public ResultadoConversao(Moeda moedaOrigem, Moeda moedaDestino, double valor) {
            this.moedaOrigem = moedaOrigem;
            this.moedaDestino = moedaDestino;
            this.valor = valor;
        }
        
        public double getValor() {
            return valor;
        }
        
        public double getValorConvertido() {
            return valorConvertido;
        }
        
        public void setValorConvertido(double valorConvertido) {
            this.valorConvertido = valorConvertido;
        }
        
        public double getTaxaCambio() {
            return taxaCambio;
        }
        
        public void setTaxaCambio(double taxaCambio) {
            this.taxaCambio = taxaCambio;
        }
        
        public String getDataAtualizacao() {
            return dataAtualizacao;
        }
        
        public void setDataAtualizacao(String dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
        }
        
        public Moeda getMoedaOrigem() {
            return moedaOrigem;
        }
        
        public Moeda getMoedaDestino() {
            return moedaDestino;
        }
    }
    
    /**
     * Construtor que inicializa o cliente HTTP.
     */
    public Conversor() {
        this.clienteHttp = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }
    
    /**
     * Converte um valor de uma moeda para outra.
     * 
     * @param moedaOrigem Moeda de origem
     * @param moedaDestino Moeda de destino
     * @param valor Valor a ser convertido
     * @return Resultado da conversão
     * @throws Exception Se ocorrer um erro na API ou na conversão
     */
    public ResultadoConversao converter(Moeda moedaOrigem, Moeda moedaDestino, double valor) throws Exception {
        if (valor <= 0) {
            throw new Exception("O valor deve ser maior que zero");
        }
        
        // Cria o objeto de resultado
        ResultadoConversao resultado = new ResultadoConversao(moedaOrigem, moedaDestino, valor);
        
        // Obtém a taxa de câmbio da API
        String codigoOrigem = moedaOrigem.getCodigo();
        String codigoDestino = moedaDestino.getCodigo();
        
        // Constrói a URL da API
        String urlApi = URL_API + codigoOrigem;
        
        // Cria a requisição HTTP
        HttpRequest requisicao = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(urlApi))
                .header("Accept", "application/json")
                .build();
        
        // Envia a requisição e obtém a resposta
        HttpResponse<String> resposta = clienteHttp.send(requisicao, HttpResponse.BodyHandlers.ofString());
        
        // Verifica se a resposta foi bem-sucedida
        if (resposta.statusCode() != 200) {
            throw new Exception("Erro na API: Código de status " + resposta.statusCode());
        }
        
        // Processa a resposta da API
        return processarResposta(resposta.body(), codigoDestino, resultado);
    }
    
    /**
     * Processa a resposta da API e calcula a conversão.
     * 
     * @param corpoResposta Corpo da resposta da API
     * @param codigoDestino Código da moeda de destino
     * @param resultado Objeto de resultado a ser preenchido
     * @return Resultado da conversão preenchido
     * @throws Exception Se ocorrer um erro no processamento da resposta
     */
    private ResultadoConversao processarResposta(String corpoResposta, String codigoDestino, ResultadoConversao resultado) throws Exception {
        try {
            // Converte a resposta para JSON
            JSONObject json = new JSONObject(corpoResposta);
            
            // Verifica se a API retornou sucesso
            if (!"success".equals(json.getString("result"))) {
                throw new Exception("Erro na API: A resposta não foi bem-sucedida");
            }
            
            // Obtém a data da última atualização
            String dataAtualizacao = json.getString("time_last_update_utc");
            resultado.setDataAtualizacao(dataAtualizacao);
            
            // Obtém as taxas de câmbio
            JSONObject taxas = json.getJSONObject("rates");
            
            // Verifica se a moeda de destino existe nas taxas
            if (!taxas.has(codigoDestino)) {
                throw new Exception("Moeda de destino não encontrada nas taxas de câmbio");
            }
            
            // Obtém a taxa de câmbio para a moeda de destino
            double taxa = taxas.getDouble(codigoDestino);
            resultado.setTaxaCambio(taxa);
            
            // Calcula o valor convertido
            double valorConvertido = resultado.getValor() * taxa;
            resultado.setValorConvertido(valorConvertido);
            
            return resultado;
        } catch (Exception e) {
            throw new Exception("Erro ao processar resposta da API: " + e.getMessage());
        }
    }
}