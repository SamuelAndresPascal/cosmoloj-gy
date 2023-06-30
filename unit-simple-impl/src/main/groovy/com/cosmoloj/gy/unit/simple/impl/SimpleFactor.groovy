package com.cosmoloj.gy.unit.simple.impl

import com.cosmoloj.gy.unit.simple.api.Factor
import com.cosmoloj.gy.unit.simple.api.Unit

class SimpleFactor implements Factor {

    private final Unit dim
    private final int numerator
    private final int denominator

    private SimpleFactor(final Unit dim, final int numerator, final int denominator) {
        this.dim = dim
        this.numerator = numerator
        this.denominator = denominator
    }

    @Override
    Unit dim() {
        dim
    }

    @Override
    int numerator() {
        numerator
    }

    @Override
    int denominator() {
        denominator
    }

    static Factor of(final Unit dim, final int numerator, final int denominator = 1) {
        return new SimpleFactor(dim, numerator, denominator);
    }
}
