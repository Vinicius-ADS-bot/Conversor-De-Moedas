package com.conversormoedas;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Classe principal que serve como ponto de entrada para o programa de Conversor de Moedas.
 */
public class Main {
    
    // Scanner para ler entrada do usuário
    private static final Scanner scanner = new Scanner(System.in);
    
    // Formatador para valores decimais
    private static final DecimalFormat formatador = new DecimalFormat("#,##0.00");
    
    // Conversor de moedas
    private static final Conversor conversor = new Conversor();
    
    /**
     * Método principal que inicia o programa.
     */
    public static void main(String[] args) {
        exibirMensagemBoasVindas();
        
        boolean continuar = true;
        
        while (continuar) {
            try {
                // Exibe as moedas suportadas
                exibirMoedasSuportadas();
                
                // Obtém a moeda de origem
                String codigoOrigem = obterCodigoMoedaOrigem();
                if (!Moeda.codigoValido(codigoOrigem)) {
                    exibirErroCodigoMoedaInvalido();
                    continue;
                }
                
                // Obtém a moeda de destino
                String codigoDestino = obterCodigoMoedaDestino();
                if (!Moeda.codigoValido(codigoDestino)) {
                    exibirErroCodigoMoedaInvalido();
                    continue;
                }
                
                // Obtém o valor a ser convertido
                double valor = obterValorParaConverter();
                if (valor <= 0) {
                    exibirErroValorInvalido();
                    continue;
                }
                
                // Realiza a conversão
                Moeda moedaOrigem = Moeda.obterPorCodigo(codigoOrigem);
                Moeda moedaDestino = Moeda.obterPorCodigo(codigoDestino);
                
                Conversor.ResultadoConversao resultado = conversor.converter(moedaOrigem, moedaDestino, valor);
                
                // Exibe o resultado
                exibirResultadoConversao(resultado);
                
                // Pergunta se o usuário deseja continuar
                continuar = perguntarSeContinua();
                
            } catch (Exception e) {
                exibirErro("Ocorreu um erro: " + e.getMessage());
            }
        }
        
        exibirMensagemDespedida();
        scanner.close();
    }
    
    /**
     * Exibe uma mensagem de boas-vindas.
     */
    private static void exibirMensagemBoasVindas() {
        System.out.println("===================================");
        System.out.println("Bem-vindo ao Conversor de Moedas!");
        System.out.println("===================================");
        System.out.println("Este aplicativo permite converter entre diferentes moedas usando taxas de câmbio em tempo real.");
        System.out.println();
    }
    
    /**
     * Exibe a lista de moedas suportadas.
     */
    private static void exibirMoedasSuportadas() {
        System.out.println("Moedas Suportadas:");
        System.out.println("------------------");
        for (Moeda moeda : Moeda.obterTodas()) {
            System.out.println(moeda);
        }
        System.out.println();
    }
    
    /**
     * Obtém o código da moeda de origem do usuário.
     */
    private static String obterCodigoMoedaOrigem() {
        System.out.print("Digite o código da moeda de origem (ex: USD): ");
        return scanner.nextLine().trim().toUpperCase();
    }
    
    /**
     * Obtém o código da moeda de destino do usuário.
     */
    private static String obterCodigoMoedaDestino() {
        System.out.print("Digite o código da moeda de destino (ex: BRL): ");
        return scanner.nextLine().trim().toUpperCase();
    }
    
    /**
     * Obtém o valor a ser convertido do usuário.
     */
    private static double obterValorParaConverter() {
        System.out.print("Digite o valor a ser convertido: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Entrada inválida. Por favor, digite um número válido.");
            System.out.print("Digite o valor a ser convertido: ");
            scanner.next(); // Consome entrada inválida
        }
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consome nova linha
        return valor;
    }
    
    /**
     * Exibe o resultado da conversão.
     */
    private static void exibirResultadoConversao(Conversor.ResultadoConversao resultado) {
        System.out.println("\nResultado da Conversão:");
        System.out.println("----------------------");
        System.out.printf("%s %s = %s %s%n", 
                formatador.format(resultado.getValor()),
                resultado.getMoedaOrigem().getCodigo(),
                formatador.format(resultado.getValorConvertido()),
                resultado.getMoedaDestino().getCodigo());
        System.out.printf("Taxa de Câmbio: 1 %s = %.6f %s%n", 
                resultado.getMoedaOrigem().getCodigo(),
                resultado.getTaxaCambio(),
                resultado.getMoedaDestino().getCodigo());
        
        // Exibe a data da última atualização
        if (resultado.getDataAtualizacao() != null) {
            System.out.println("Última Atualização da Taxa: " + resultado.getDataAtualizacao());
        }
        
        // Exibe a data e hora da conversão
        System.out.println("Hora da Conversão: " + LocalDateTime.now());
        System.out.println();
    }
    
    /**
     * Pergunta ao usuário se ele deseja realizar outra conversão.
     */
    private static boolean perguntarSeContinua() {
        System.out.print("Deseja realizar outra conversão? (s/n): ");
        String resposta = scanner.nextLine().trim().toLowerCase();
        System.out.println();
        return resposta.startsWith("s");
    }
    
    /**
     * Exibe uma mensagem de erro para código de moeda inválido.
     */
    private static void exibirErroCodigoMoedaInvalido() {
        System.out.println("Erro: Código de moeda inválido. Por favor, digite um código suportado.");
        System.out.println();
    }
    
    /**
     * Exibe uma mensagem de erro para valor inválido.
     */
    private static void exibirErroValorInvalido() {
        System.out.println("Erro: Valor inválido. Por favor, digite um número positivo.");
        System.out.println();
    }
    
    /**
     * Exibe uma mensagem de erro genérica.
     */
    private static void exibirErro(String mensagem) {
        System.out.println("Erro: " + mensagem);
        System.out.println("Por favor, tente novamente.");
        System.out.println();
    }
    
    /**
     * Exibe uma mensagem de despedida.
     */
    private static void exibirMensagemDespedida() {
        System.out.println("Obrigado por usar o Conversor de Moedas!");
        System.out.println("Até a próxima!");
    }
}