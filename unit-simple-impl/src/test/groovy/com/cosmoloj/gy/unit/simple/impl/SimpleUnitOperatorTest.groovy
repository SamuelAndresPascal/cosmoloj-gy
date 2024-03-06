package com.cosmoloj.gy.unit.simple.impl

import com.cosmoloj.gy.unit.simple.api.Unit
import com.cosmoloj.gy.unit.simple.api.UnitConverter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SimpleUnitOperatorTest {
    private SimpleUnitOperatorTest() {
    }

    @Test
    void transformedUnitConversion() {
        final Unit m = new SimpleFundamentalUnit()
        final Unit km = m * 1000.0
        final Unit cm = m / 100
        final UnitConverter cmToKm = cm >> km

        Assertions.assertEquals(0.00003, cmToKm.convert(3.0), 1e-10)
        Assertions.assertEquals(3.0, (~cmToKm).convert(0.00003), 1e-10)
    }

    @Test
    void derivedUnitConversion() {

        final Unit m = new SimpleFundamentalUnit()
        final Unit km = m * 1000

        final Unit km2 = km ** 2
        final Unit cm = m / 100
        final Unit cm2 = cm ** 2
        final UnitConverter km2Tocm2 = km2 >> cm2

        Assertions.assertEquals(30000000000.0, km2Tocm2.convert(3.0), 1e-10)
        Assertions.assertEquals(3.0, (~km2Tocm2).convert(30000000000.0), 1e-10)
    }

    @Test
    void combinedDimensionDerivedUnitConversion() {

        final Unit m = new SimpleFundamentalUnit()
        final Unit kg = new SimpleFundamentalUnit()
        final Unit g = kg / 1000
        final Unit ton = kg * 1000
        final Unit gPerM2 = g / m ** 2
        final Unit km = m * 1000
        final Unit tonPerKm2 = ton * ~km ** 2
        final Unit cm = m / 100
        final Unit tonPerCm2 = ton / cm ** 2
        final UnitConverter gPerM2ToTonPerKm2 = gPerM2 >> tonPerKm2
        final UnitConverter gPerM2ToTonPerCm2 = tonPerCm2 << gPerM2

        Assertions.assertEquals(1.0, gPerM2ToTonPerKm2.convert(1.0), 1e-10)
        Assertions.assertEquals(3.0, (~gPerM2ToTonPerKm2).convert(3.0), 1e-10)
        Assertions.assertEquals(1e-10, gPerM2ToTonPerCm2.convert(1.0), 1e-20)
        Assertions.assertEquals(3e-10, gPerM2ToTonPerCm2.convert(3.0), 1e-20)
        Assertions.assertEquals(0.0, gPerM2ToTonPerCm2.offset())
        Assertions.assertEquals(1e-10, gPerM2ToTonPerCm2.scale(), 1e-10)
        Assertions.assertEquals(-0.0d, (~gPerM2ToTonPerCm2).offset())
        Assertions.assertEquals(3.0, (~gPerM2ToTonPerCm2).convert(3e-10), 1e-10)
    }

    @Test
    void temperatures() {

        final Unit k = new SimpleFundamentalUnit()
        final Unit c = k + 273.15
        final UnitConverter kToC = k >> c

        Assertions.assertEquals(-273.15, kToC.convert(0), 1e-10)
        Assertions.assertEquals(273.15, (~kToC).convert(0), 1e-10)

        // en combinaison avec d'autres unités, les conversions d'unités de températures doivent devenir linéaires
        final Unit m = new SimpleFundamentalUnit()
        final Unit cPerM = c / m
        final Unit kPerM = k / m
        final UnitConverter kPerMToCPerM = kPerM >> cPerM
        Assertions.assertEquals(3.0, kPerMToCPerM.convert(3.0), 1e-10)
        Assertions.assertEquals(3.0, (~kPerMToCPerM).convert(3.0), 1e-10)
    }

    @Test
    void speed() {

        final Unit m = new SimpleFundamentalUnit()
        final Unit km = m * 1000.0

        final Unit s = new SimpleFundamentalUnit()
        final Unit h = s * 3600.0

        final Unit ms = m / s
        final Unit kmh = km / h

        final UnitConverter msToKmh = ms >> kmh

        Assertions.assertEquals(360.0, msToKmh.convert(100.0), 1e-10)
        Assertions.assertEquals(5.0, (~msToKmh).convert(18.0), 1e-10)
    }
}
