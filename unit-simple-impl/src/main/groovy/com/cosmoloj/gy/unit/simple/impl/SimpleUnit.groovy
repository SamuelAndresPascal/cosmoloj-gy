package com.cosmoloj.gy.unit.simple.impl

import com.cosmoloj.gy.unit.simple.api.Factor
import com.cosmoloj.gy.unit.simple.api.TransformedUnit
import com.cosmoloj.gy.unit.simple.api.Unit

abstract class SimpleUnit implements Unit {

    @Override
    TransformedUnit shift(final double value) {
        SimpleTransformedUnit.of(SimpleUnitConverter.translation(value), this)
    }

    @Override
    TransformedUnit scaleMultiply(final double value) {
        SimpleTransformedUnit.of(SimpleUnitConverter.linear(value), this)
    }

    @Override
    TransformedUnit scaleDivide(final double value) {
        this.scaleMultiply(1.0 / value)
    }

    @Override
    Factor factor(final int numerator, final int denominator) {
        SimpleFactor.of(this, numerator, denominator)
    }

    @Override
    Factor factor(final int numerator) {
        SimpleFactor.of(this, numerator)
    }
}
