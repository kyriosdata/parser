# parser
Analisador léxico, sintático e avaliador de expressões matemáticas.

[<img src="https://api.travis-ci.org/kyriosdata/parser.svg?branch=master">](https://travis-ci.org/kyriosdata/parser)
[![Dependency Status](https://www.versioneye.com/user/projects/5816220fd33a7126e32ff001/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5816220fd33a7126e32ff001)
[![Coverage Status](https://coveralls.io/repos/github/kyriosdata/parser/badge.svg?branch=master)](https://coveralls.io/github/kyriosdata/parser?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1eb0eb949dd14f2bb8b31c56a988350f)](https://www.codacy.com/app/kyriosdata/parser?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=kyriosdata/parser&amp;utm_campaign=Badge_Grade)
[![Javadocs](http://javadoc.io/badge/com.github.kyriosdata.parser/parser.svg)](http://javadoc.io/doc/com.github.kyriosdata.parser/parser)
[![Sonarqube](https://sonarqube.com/api/badges/gate?key=com.github.kyriosdata.parser%3Aparser)](https://sonarqube.com/dashboard?id=com.github.kyriosdata.parser%3Aparser)

<br />
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by/4.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by/4.0/">Creative Commons Attribution 4.0 International License</a>. 
<br />Fábio Nogueira de Lucena - Fábrica de Software - Instituto de Informática (UFG).

## Como usar?

### Obtenha a versão mais recente

Seu projeto precisa de uma única dependência, indicada abaixo. 

<pre>
&lt;dependency&gt;
  &lt;groupId&gt;com.github.kyriosdata.parser&lt;/groupId&gt;
  &lt;artifactId&gt;parser&lt;/artifactId&gt;
  &lt;version&gt;1.0.1&lt;/version&gt;
&lt;/dependency&gt;
</pre>

### Avaliação de uma expressão
O _parser_ é o principal componente da biblioteca. Ele recebe uma sequência de _tokens_ produzida pelo _lexer_ e produz como saída uma expressão, que pode ser avaliada conforme ilustrado abaixo.

<pre>
List&lt;Token&gt; tokens = new Lexer("2.3 * 10").tokenize();
Parser parser = new Parser(tokens);
float resultado = parser.expressao().valor(); // 23.0
</pre>

Expressões podem conter variáveis e, nesse caso, valores a serem utilizados devem ser fornecidos. Caso contrário, o valor 0f é assumido.

<pre>
Map&lt;String, Float&gt; ctx = new HashMap&lt;&gt;();
ctx.put("a", 10);

List&lt;Token&gt; tokens = new Lexer("2.3 * a").tokenize();
Parser parser = new Parser(tokens);
float resultado = parser.expressao().valor(ctx); // 23.0
</pre>

## Especificação das expressões

Expressões admitidas incluem o uso de constantes, variáveis (sequências iniciadas por caractere, seguidas ou não de dígitos e abre/fecha parênteses. Os operadores incluem soma, subtração, multiplicação e divisão. Uma expressão também pode ser uma condição e, nesse caso, os operadores são E, OU e igualdade. As expressões lógicas produzem o valor 0 (caso verdadeira) ou diferente de zero (caso falsa). Alguns exemplos são ilustrados abaixo.

- a + b * (x - y)
- x
- 10.2
- a*(b - (c * d))
- feriado & comDinheiroNoBolso
- feliz | vitoriaDoTimao
- (5 - 3) = (10 / 5)

## Expressões consideradas inválidas
Não é permitido o emprego de mais de um operador sem uso de parênteses, conforme ilustrado abaixo.

- 10 - 9 - 8
- 2 + 3 + 4

