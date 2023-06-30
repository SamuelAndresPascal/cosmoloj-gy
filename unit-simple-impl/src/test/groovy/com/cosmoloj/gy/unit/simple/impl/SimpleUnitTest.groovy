package com.cosmoloj.gy.unit.simple.impl

import com.cosmoloj.gy.unit.simple.api.Unit
import com.cosmoloj.gy.unit.simple.api.UnitConverter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SimpleUnitTest {
    private SimpleUnitTest() {
    }

    @Test
    void transformedUnitConversion() {
        final Unit m = new SimpleFundamentalUnit()
        final Unit km = m.scaleMultiply(1000)
        final Unit cm = m.scaleDivide(100)
        final UnitConverter cmToKm = cm.getConverterTo(km)

        Assertions.assertEquals(0.00003, cmToKm.convert(3.0), 1e-10)
        Assertions.assertEquals(3.0, cmToKm.inverse().convert(0.00003), 1e-10)
    }

    @Test
    void derivedUnitConversion() {

        final Unit m = new SimpleFundamentalUnit()
        final Unit km = m.scaleMultiply(1000)

        final Unit km2 = SimpleDerivedUnit.of(km.factor(2))
        final Unit cm = m.scaleDivide(100)
        final Unit cm2 = SimpleDerivedUnit.of(cm.factor(2))
        final UnitConverter km2Tocm2 = km2.getConverterTo(cm2)

        Assertions.assertEquals(30000000000.0, km2Tocm2.convert(3.0), 1e-10)
        Assertions.assertEquals(3.0, km2Tocm2.inverse().convert(30000000000.0), 1e-10)
    }

    @Test
    void combinedDimensionDerivedUnitConversion() {

        final Unit m = new SimpleFundamentalUnit()
        final Unit kg = new SimpleFundamentalUnit()
        final Unit g = kg.scaleDivide(1000)
        final Unit ton = kg.scaleMultiply(1000)
        final Unit gPerM2 = SimpleDerivedUnit.of(g, m.factor(-2))
        final Unit km = m.scaleMultiply(1000)
        final Unit tonPerKm2 = SimpleDerivedUnit.of(ton, km.factor(-2))
        final Unit cm = m.scaleDivide(100)
        final Unit tonPerCm2 = SimpleDerivedUnit.of(ton, cm.factor(-2))
        final UnitConverter gPerM2ToTonPerKm2 = gPerM2.getConverterTo(tonPerKm2)
        final UnitConverter gPerM2ToTonPerCm2 = gPerM2.getConverterTo(tonPerCm2)

        Assertions.assertEquals(1.0, gPerM2ToTonPerKm2.convert(1.0), 1e-10)
        Assertions.assertEquals(3.0, gPerM2ToTonPerKm2.inverse().convert(3.0), 1e-10)
        Assertions.assertEquals(1e-10, gPerM2ToTonPerCm2.convert(1.0), 1e-20)
        Assertions.assertEquals(3e-10, gPerM2ToTonPerCm2.convert(3.0), 1e-20)
        Assertions.assertEquals(0.0, gPerM2ToTonPerCm2.offset())
        Assertions.assertEquals(1e-10, gPerM2ToTonPerCm2.scale(), 1e-10)
        Assertions.assertEquals(-0.0d, gPerM2ToTonPerCm2.inverse().offset())
        Assertions.assertEquals(3.0, gPerM2ToTonPerCm2.inverse().convert(3e-10), 1e-10)
    }

    @Test
    void temperatures() {

        final Unit k = new SimpleFundamentalUnit()
        final Unit c = k.shift(273.15)
        final UnitConverter kToC = k.getConverterTo(c)

        Assertions.assertEquals(-273.15, kToC.convert(0), 1e-10)
        Assertions.assertEquals(273.15, kToC.inverse().convert(0), 1e-10)

        // en combinaison avec d'autres unités, les conversions d'unités de températures doivent devenir linéaires
        final Unit m = new SimpleFundamentalUnit()
        final Unit cPerM = SimpleDerivedUnit.of(c, m.factor(-1))
        final Unit kPerM = SimpleDerivedUnit.of(k, m.factor(-1))
        final UnitConverter kPerMToCPerM = kPerM.getConverterTo(cPerM)
        Assertions.assertEquals(3.0, kPerMToCPerM.convert(3.0), 1e-10)
        Assertions.assertEquals(3.0, kPerMToCPerM.inverse().convert(3.0), 1e-10)
    }

    @Test
    void speed() {

        final Unit m = new SimpleFundamentalUnit()
        final Unit km = m.scaleMultiply(1000.0)

        final Unit s = new SimpleFundamentalUnit()
        final Unit h = s.scaleMultiply(3600.0)

        final Unit ms = SimpleDerivedUnit.of(m, s.factor(-1))
        final Unit kmh = SimpleDerivedUnit.of(km, h.factor(-1))

        final UnitConverter msToKmh = ms.getConverterTo(kmh)

        Assertions.assertEquals(360.0, msToKmh.convert(100.0), 1e-10)
        Assertions.assertEquals(5.0, msToKmh.inverse().convert(18.0), 1e-10)
    }
}
