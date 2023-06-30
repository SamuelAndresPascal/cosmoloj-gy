package com.cosmoloj.gy.unit.simple.impl

import com.cosmoloj.gy.unit.simple.api.TransformedUnit
import com.cosmoloj.gy.unit.simple.api.Unit
import com.cosmoloj.gy.unit.simple.api.UnitConverter

class SimpleTransformedUnit  extends SimpleUnit implements TransformedUnit {

    private final Unit reference
    private final UnitConverter toReference

    private SimpleTransformedUnit(final UnitConverter toReference, final Unit reference) {
        this.toReference = toReference
        this.reference = reference
    }

    @Override
    Unit reference() {
        reference
    }

    @Override
    UnitConverter toReference() {
        toReference
    }

    static TransformedUnit of(final UnitConverter toReference, final Unit reference) {
        new SimpleTransformedUnit(toReference, reference);
    }
}
