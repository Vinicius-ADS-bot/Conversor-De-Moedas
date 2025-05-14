# Conversor de Moedas em Java (Versão Simplificada)

Este é um aplicativo de linha de comando para conversão de moedas em tempo real utilizando taxas de câmbio atualizadas. Esta versão foi simplificada para facilitar a compreensão e manutenção do código.

## Funcionalidades

- Conversão entre 20 moedas internacionais
- Taxas de câmbio em tempo real obtidas de API gratuita
- Interface amigável de linha de comando
- Exibição da data de atualização das taxas de câmbio
- Validação de entrada do usuário

## Requisitos

- Java 11 ou superior
- Biblioteca org.json (incluída no projeto)
- Conexão com a internet para obter taxas de câmbio atualizadas

## Como Executar

1. Certifique-se de ter o Java instalado em seu sistema.
2. Clone este repositório ou baixe os arquivos.
3. Compile o projeto:
   ```
   mkdir -p out
   cd src
   javac -cp .:../lib/org.json.jar -d ../out com/conversormoedas/Main.java
   ```
4. Execute a aplicação:
   ```
   cd ..
   java -cp out:lib/org.json.jar com.conversormoedas.Main
   ```

## Estrutura do Projeto

Esta versão simplificada possui apenas três arquivos Java:

1. **Main.java**: Contém a interface com o usuário, incluindo todas as interações via linha de comando.
2. **Moeda.java**: Gerencia as moedas disponíveis para conversão.
3. **Conversor.java**: Lida com a comunicação com a API de taxas de câmbio e realiza os cálculos de conversão.

## Moedas Suportadas

- USD (Dólar Americano)
- EUR (Euro)
- GBP (Libra Esterlina)
- JPY (Iene Japonês)
- CAD (Dólar Canadense)
- AUD (Dólar Australiano)
- CHF (Franco Suíço)
- CNY (Yuan Chinês)
- INR (Rupia Indiana)
- BRL (Real Brasileiro)
- MXN (Peso Mexicano)
- SGD (Dólar de Singapura)
- NZD (Dólar Neozelandês)
- HKD (Dólar de Hong Kong)
- SEK (Coroa Sueca)
- KRW (Won Sul-Coreano)
- PLN (Zloty Polonês)
- NOK (Coroa Norueguesa)
- RUB (Rublo Russo)
- TRY (Lira Turca)

## API Utilizada

Este projeto utiliza a API gratuita de [Exchange Rate API](https://open.er-api.com/) para obter taxas de câmbio atualizadas.

## Como Funciona

1. O programa exibe uma lista de moedas suportadas
2. O usuário escolhe a moeda de origem (ex: USD)
3. O usuário escolhe a moeda de destino (ex: BRL)
4. O usuário digita o valor a ser convertido
5. O programa consulta a API para obter a taxa de câmbio atualizada
6. O programa exibe o resultado da conversão
7. O usuário pode escolher fazer outra conversão ou sair