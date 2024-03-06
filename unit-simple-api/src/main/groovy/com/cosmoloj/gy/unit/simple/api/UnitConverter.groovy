package com.cosmoloj.gy.unit.simple.api

interface UnitConverter {
    double scale()
    double offset()
    UnitConverter inverse()
    UnitConverter linear()
    UnitConverter linearPow(double pow)
    double convert(double value)
    UnitConverter concatenate(UnitConverter unitConverter)

    default UnitConverter bitwiseNegate() {
        inverse()
    }
}
