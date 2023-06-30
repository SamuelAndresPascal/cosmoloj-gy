package com.cosmoloj.gy.unit.simple.api

interface Factor {
    Unit dim()

    int numerator()

    int denominator()

    default double power() {
        return denominator() == 1 ? numerator() : ((double) numerator()) / denominator();
    }
}