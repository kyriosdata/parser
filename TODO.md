# Melhorias

- Expressões matemáticas não incluem funções e estão restritas a casos simples. Estender com lexer e parser mais elaborados. Possivelmente fazer uso de [Antlr](https://www.antlr.org/).
- São interpretadas em tempo de execução, o que reduz o desempenho da execução. Verificar as alternativas:
  - Combinar Antlr e LLVM para geração de código executável ([aqui](https://theantlrguy.atlassian.net/wiki/spaces/ANTLR3/pages/2687062/LLVM))
  - Exemplos de Antlr e LLVM em conjunto: [aqui](https://github.com/AndreaOrru/Lucy) e [aqui](https://github.com/alongubkin/modern)
  - Usar Antlr com [Javassist](https://www.javassist.org/) ou [ByteBuddy](https://bytebuddy.net).
