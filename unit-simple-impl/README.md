# Simple Unit (implémentation Groovy - implémentation de référence)

## Utilisation standard

L'utilisation standard se réfère aux méthodes implémentant la spécification Simple Unit.

Utilisation des unités transformées :

```groovy
final Unit m = new SimpleFundamentalUnit()
final Unit km = m.scaleMultiply(1000)
final Unit cm = m.scaleDivide(100)
final UnitConverter cmToKm = cm.getConverterTo(km)

cmToKm.convert(3.0) // 0.00003
cmToKm.inverse().convert(0.00003) // 3
```

Utilisation des unités dérivées :

```groovy
final Unit m = new SimpleFundamentalUnit()
final Unit km = m.scaleMultiply(1000)

final Unit km2 = SimpleDerivedUnit.of(km.factor(2))
final Unit cm = m.scaleDivide(100)
final Unit cm2 = SimpleDerivedUnit.of(cm.factor(2))
final UnitConverter km2Tocm2 = km2.getConverterTo(cm2)

km2Tocm2.convert(3.0) // 30000000000
km2Tocm2.inverse().convert(30000000000) // 3
```

Utilisation des unités dérivées en combinant les dimensions :

```groovy
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

gPerM2ToTonPerKm2.convert(1.0) // 1
gPerM2ToTonPerKm2.inverse().convert(3.0) // 3
gPerM2ToTonPerCm2.convert(1.0) // 1e-4
gPerM2ToTonPerCm2.convert(3.0) // 3e-10
gPerM2ToTonPerCm2.offset() // 0.0
gPerM2ToTonPerCm2.scale() // 1e-10
gPerM2ToTonPerCm2.inverse().offset() // -0.0
gPerM2ToTonPerCm2.inverse().convert(3e-10) // 3
```

Utilisation des températures (conversions affines et linéaires) :

```groovy
final Unit k = new SimpleFundamentalUnit()
final Unit c = k.shift(273.15)
final UnitConverter kToC = k.getConverterTo(c)

kToC.convert(0.0) // -273.15
kToC.inverse().convert(0.0) // 273.15

// en combinaison avec d'autres unités, les conversions d'unités de températures doivent devenir linéaires
final Unit m = new SimpleFundamentalUnit()
final Unit cPerM = SimpleDerivedUnit.of(c, m.factor(-1))
final Unit kPerM = SimpleDerivedUnit.of(k, m.factor(-1))
final UnitConverter kPerMToCPerM = kPerM.getConverterTo(cPerM)

kPerMToCPerM.convert(3.0) // 3
kPerMToCPerM.inverse().convert(3.0) // 3
```

Utilisation des conversions non décimales :

```groovy
final Unit m = new SimpleFundamentalUnit()
final Unit km = m.scaleMultiply(1000.0)

final Unit s = new SimpleFundamentalUnit()
final Unit h = s.scaleMultiply(3600.0)

final Unit ms = SimpleDerivedUnit.of(m, s.factor(-1))
final Unit kmh = SimpleDerivedUnit.of(km, h.factor(-1))

final UnitConverter msToKmh = ms.getConverterTo(kmh)

msToKmh.convert(100.0) // 360.
msToKmh.inverse().convert(18.0) // 5
```

## Utilisation avec surcharge d'opérateurs

L'implémentation en Groovy de Simple Unit utilise la surcharge d'opérateurs utilisable dans ce langage comme
alternative aux méthodes standards.

Utilisation des unités transformées :

```groovy
final Unit m = new SimpleFundamentalUnit()
final Unit km = m * 1000
final Unit cm = m / 100
final UnitConverter cmToKm = cm >> km

cmToKm.convert(3.0) // 0.00003
(~cmToKm).convert(0.00003) // 3
```

Utilisation des unités dérivées :

```groovy
final Unit m = new SimpleFundamentalUnit()
final Unit km = m * 1000

final Unit km2 = km ** 2
final Unit cm = m / 100
final Unit cm2 = cm ** 2
final UnitConverter km2Tocm2 = km2 >> cm2

km2Tocm2.convert(3.0) // 30000000000
(~km2Tocm2).convert(30000000000) // 3
```

Utilisation des unités dérivées en combinant les dimensions :

```groovy
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

gPerM2ToTonPerKm2.convert(1.0) // 1
(~gPerM2ToTonPerKm2).convert(3.0) // 3
gPerM2ToTonPerCm2.convert(1.0) // 1e-4
gPerM2ToTonPerCm2.convert(3.0) // 3e-10
gPerM2ToTonPerCm2.offset() // 0.0
gPerM2ToTonPerCm2.scale() // 1e-10
(~gPerM2ToTonPerCm2).offset() // -0.0
(~gPerM2ToTonPerCm2).convert(3e-10) // 3
```

Utilisation des températures (conversions affines et linéaires) :

```groovy
final Unit k = new SimpleFundamentalUnit()
final Unit c = k + 273.15
final UnitConverter kToC = k >> c

kToC.convert(0.0) // -273.15
(~kToC).convert(0.0) // 273.15

// en combinaison avec d'autres unités, les conversions d'unités de températures doivent devenir linéaires
final Unit m = new SimpleFundamentalUnit()
final Unit cPerM = c / m
final Unit kPerM = k / m
final UnitConverter kPerMToCPerM = kPerM >> cPerM

kPerMToCPerM.convert(3.0) // 3
(~kPerMToCPerM).convert(3.0) // 3
```

Utilisation des conversions non décimales :

```groovy
final Unit m = new SimpleFundamentalUnit()
final Unit km = m * 1000.0

final Unit s = new SimpleFundamentalUnit()
final Unit h = s * 3600.0

final Unit ms = m / s
final Unit kmh = km / h

final UnitConverter msToKmh = ms >> kmh

msToKmh.convert(100.0) // 360.
(~msToKmh).convert(18.0) // 5
```
