package com.cosmoloj.gy.unit.simple.impl

import com.cosmoloj.gy.unit.simple.api.UnitConverter

class SimpleUnitConverter implements UnitConverter {

    private final double scale
    private final double offset
    private final UnitConverter inverse

    SimpleUnitConverter(final double scale, final double offset, final UnitConverter inverse) {
        this.scale = scale
        this.offset = offset
        this.inverse = inverse
    }

    private SimpleUnitConverter(final double scale, final double offset) {
        this.scale = scale
        this.offset = offset
        this.inverse = new SimpleUnitConverter(1.0 / scale, -offset / scale, this)
    }

    @Override
    double scale() {
        scale
    }

    @Override
    double offset() {
        offset
    }

    @Override
    UnitConverter inverse() {
        inverse
    }

    @Override
    UnitConverter linear() {
        // comparaison stricte volontaire sur un flottant
        if (offset == 0.0) {
            return this
        }
        linear(scale)
    }

    @Override
    UnitConverter linearPow(final double pow) {
        // comparaison stricte volontaire sur des flottants
        if (offset == 0.0 && pow == 1.0) {
            return this;
        }
        linear(Math.pow(scale, pow))
    }

    @Override
    double convert(final double value) {
        return scale * value + offset
    }

    @Override
    UnitConverter concatenate(final UnitConverter converter) {
        return of(converter.scale() * scale, this.convert(converter.offset()))
    }

    private static final UnitConverter IDENTITY = linear(1.0)

    static UnitConverter of(final double scale, final double offset) {
        new SimpleUnitConverter(scale, offset)
    }

    static UnitConverter linear(final double scale) {
        new SimpleUnitConverter(scale, 0.0)
    }

    static UnitConverter translation(final double offset) {
        new SimpleUnitConverter(1.0, offset)
    }

    static UnitConverter identity() {
        IDENTITY
    }
}
