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

    default TransformedUnit plus(final double value) {
        shift(value)
    }

    default TransformedUnit minus(final double value) {
        shift(-value)
    }

    Unit multiply(Object value)

    Unit div(Object value)

    Unit power(int value)

    default UnitConverter rightShift(final Unit unit) {
        getConverterTo(unit)
    }

    default UnitConverter leftShift(final Unit unit) {
        unit.getConverterTo(this)
    }

    default Unit bitwiseNegate() {
        power(-1)
    }
}
