
# Benchmark
Além de permitir validar uma implementação de um avaliador de expressões, 
o presente _benchmark_ também considera relevante outros aspectos:
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

# Conceitos

## Avaliador  
Biblioteca ou serviço que implementa a avaliação de expressões matemáticas. 

## Adaptador
Código responsável por requisitar a avaliação de expressões matemáticas pelo Avaliador.
Deve ser produzido um adaptador para cada Avaliador. 

# Design preliminar

- Toda implementação a ser avaliada pelo presente _benchmark_ deve incluir as 
classes/interfaces identificadas abaixo. 

### Expressao (interface)
Esta interface deve possuir o método _avaliacao_.

### Adapter (interface)
Esta interface possui o método _Expressao getExpressaoPara(String expressao)_.
A execução deste método inclui a preparação da expressão fornecida, caso exista,
antes que seja executada. 

### AdapterFactory (classe)
Produz uma instância de _Adapter_ por meio do método 
_newInstance(String nomeDaClasse)_.

### Teste (classe)
Uma instância desta classe possui uma expressão (String),
um valor para cada uma das variáveis empregadas na expressão e o
resultado correspondente. 

### BancadaDeTestes implements Supplier<Teste>
O construtor recebe como argumento o nome de um arquivo CSV cujas
três colunas são, nesta ordem: (a) expressão; (b) valores para as variáveis
empregadas pela expressão (se for o caso); (c) o resultado da avaliação 
da expressão com os valores fornecidos. 

O método _get_ será empregado para recuperar, um por um, na ordem em 
que aparecem no arquivo CSV, os testes contidos neste arquivo. 

### Desempenho (classe)
Classe que avalia o desempenho de uma implementação de um avaliador de expressões.

### Aplicativo (classe)
Ponto de entrada para execução do _benchmark_. Este aplicativo recebe como
entrada um arquivo txt indicando o que deverá ser executado pelo _benchmark_.

- Nome da classe que implementa o adaptador
- nome de arquivo CSV contendo testes seguida da quantidade de vezes em 
que deverá ser executado. 
- Repete zero ou mais vezes o item anterior. 

O retorno será um relatório indicando o tempo gasto na execução de 
cada um dos testes (linha de teste do arquivo de entrada). 

#### Funcionamento básico
A partir do nome da classe é obtida uma instância de _Adapter_. 
Para cada linha de teste do arquivo de entrada, um instância de 
_BancadaDeTestes_ é criada. Convém observar que para cada _Teste_
retornado pela instância de _BancadaDeTestes_, por meio do método
_get_, obtém-se a expressão correspondente, a partir da qual, 
por meio da instância de _Adapter_ obtém-se a instância de 
_Expressao_ correspondente. Este último objeto recebe a mensagem
_avaliacao_ cujo argumento é o conjunto de valores. O retorno é verificado
com aquele do teste. Se o valor retornado difere do esperado, então
o _benchmark_ é interrompido. Caso contrário, o tempo gasto será 
acumulado. 

## Cenários

### 1 Constante 1
A expressão formada apenas deste dígito 1 é executada 
10, 100, 1000, 10.000, 100.000 e 1.000.000 de vezes, 
produzindo seis valores. 

### 2 Constante -12.45678

Idem do anterior. 



