package com.cosmoloj.gy.unit.simple.api

interface Unit extends Factor {

    default UnitConverter getConverterTo(final Unit target) {
        return target.toBase().inverse().concatenate(toBase())
    }

    UnitConverter toBase();

    @Override
    default Unit dim() {
        this
    }

    @Override
    default int numerator() {
        1
    }

    @Override
    default int denominator() {
        1
    }

    TransformedUnit shift(double value)

    TransformedUnit scaleMultiply(double value)

    TransformedUnit scaleDivide(double value)

    Factor factor(int numerator, int denominator)

    Factor factor(int numerator)
}