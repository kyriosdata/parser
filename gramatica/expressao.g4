// Gramática para expressões matemáticas
grammar expressao;

// Sentença válida é uma equação (comparação) ou simples expressão
sentenca : ( equacao | expr );

equacao
   : expr comparacao expr
   ;

expr
   :  expr POTENCIA expr
   |  expr  (MULTIPLICACAO | DIVISAO)  expr
   |  expr  (SOMA | SUBTRACAO) expr
   |  ABRE expr FECHA
   |  (SUBTRACAO)? operando
   ;

operando
   : constante
   | variavel
   ;

constante
   : NUMERO
   ;

variavel
   : VARIAVEL
   ;

comparacao
   : EQ
   | NE
   | GT
   | GE
   | LT
   | LE
   ;


VARIAVEL
   : PRIMEIRO_CARACTERE OUTRO_CARACTERE*
   ;


fragment PRIMEIRO_CARACTERE
   : ('a' .. 'z') | ('A' .. 'Z')
   ;


fragment OUTRO_CARACTERE
   : PRIMEIRO_CARACTERE | DIGITO
   ;

NUMERO
   : IRRACIONAL (E SINAL? INTEIRO_SEM_SINAL)?
   ;

fragment IRRACIONAL
   : DIGITO + ('.' DIGITO +)?
   ;

fragment INTEIRO_SEM_SINAL
   : DIGITO+
   ;


fragment E
   : 'E' | 'e'
   ;

fragment DIGITO
    : ('0' .. '9')
    ;

fragment SINAL
   : ('+' | '-')
   ;


ABRE
   : '('
   ;


FECHA
   : ')'
   ;


SOMA
   : '+'
   ;


SUBTRACAO
   : '-'
   ;


MULTIPLICACAO
   : '*'
   ;


DIVISAO
   : '/'
   ;


GT
   : '>'
   ;

GE
    : '>='
    ;

LT
   : '<'
   ;

LE
   : '<='
   ;

EQ
   : '='
   ;

NE
   : '!='
   ;


POINT
   : '.'
   ;


POTENCIA
   : '^'
   ;


WS
   : [ \r\n\t] + -> skip
   ;
