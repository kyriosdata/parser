# Benchmark
Além de permitir a validação de uma implementação de um avaliador de express
ões, o presente _benchmark_ também considera relevante outros aspectos:
- Desempenho. Tempo gasto para avaliação de uma expressão.
- Memória. Consumo de memória incorrido ao avaliar expressões.
- Preparação. Tempo gasto para pré-processamento de uma expressão, caso
 exista esta opção, sem incluir o tempo consumido na avaliação da expressão. 

# Validação da correção

A validação da correção de uma implementação será fornecidapor meio de um
 arquivo CSV contendo três colunas, na ordem descrita abaixo:

- Expressão. Por exemplo, 2*(3-x).
- Variáveis. Valores das variáveis a serem utilizadas na avaliação da expressão
serão fornecidas separadas por vírgula. Por exemplo, para a expressão acima
 podemos ter x=10. Se a expressão faz uso de mais de uma variável, então 
 os valores devem ser separados por vírgula. Por exemplo, para a expressão 
 "a + b", os valores das variáveis podem ser "a=1,b=3.4", por exemplo.
- Resultado. O valor da expressão. Por exemplo, para a expressão acima e o
 valor de x igual a 10 (x=10), o resultado é -14. Caso a expressão seja
 inválida, então o resultado deve ser a  sequência "ERRO". Por exemplo, se a
 expressão fornecida é "2*)", então o resultado deve ser "ERRO", pois não se
  trata de uma expressão válida. 