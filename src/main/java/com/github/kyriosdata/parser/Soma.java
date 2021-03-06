/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.parser;

import java.util.Map;

/**
 * Expressão que é soma de duas parcelas.
 */
class Soma implements Expressao {

    private final Expressao parcelaUm;
    private final Expressao parcelaDois;

    /**
     * Cria expressão que é a soma de duas parcelas.
     *
     * @param p1 Primeira parcela.
     * @param p2 Segunda parcela.
     *
     * @throws IllegalArgumentException Se um ou ambos os argumentos
     * forem {@code null}.
     */
    public Soma(Expressao p1, Expressao p2) {
        if (p1 == null || p2 == null) {
            throw new IllegalArgumentException("p1 ou p2 é null");
        }

        parcelaUm = p1;
        parcelaDois = p2;
    }

    /**
     * Obtém a soma das duas parcelas.
     * @return A soma das duas parcelas. Parcela que é variável
     * recebe 0 como valor.
     *
     * @see #valor(Map)
     */
    @Override
    public float valor() {
        return parcelaUm.valor() + parcelaDois.valor();
    }

    /**
     * Obtém a soma das duas parcelas. Usa o contexto fornecido
     * para recuperar valor das parcelas. Se não encontrado,
     * o valor zero é assumido.
     *
     * @param contexto Dicionário contendo valores
     *                 de variáveis, possivelmente
     *                 empregadas para avaliar a expressão.
     *
     * @return Soma das parcelas conforme os valores disponíveis no
     * contexto. Se não disponível, assume valor zero.
     */
    @Override
    public float valor(Map<String, Float> contexto) {
        return parcelaUm.valor(contexto) + parcelaDois.valor(contexto);
    }
}
