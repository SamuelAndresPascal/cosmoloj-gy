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

    Unit multiply(final Object value) {
        SimpleDerivedUnit.of(this, value as Factor)
    }

    Unit div(final Object value) {
        SimpleDerivedUnit.of(this, of(value as Factor, -1))
    }

    static Factor of(final Factor dim, final int numerator, final int denominator = 1) {
        dim instanceof Unit ? new SimpleFactor(dim, numerator, denominator)
                : new SimpleFactor(dim.dim(), numerator * dim.numerator(), denominator * dim.denominator())
    }
}
