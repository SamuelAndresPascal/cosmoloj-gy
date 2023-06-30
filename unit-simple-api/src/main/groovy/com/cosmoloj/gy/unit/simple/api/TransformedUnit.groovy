package com.cosmoloj.gy.unit.simple.api

interface TransformedUnit extends Unit {

    Unit reference()

    UnitConverter toReference()

    @Override
    default UnitConverter toBase() {
        return this.reference().toBase().concatenate(this.toReference())
    }
}