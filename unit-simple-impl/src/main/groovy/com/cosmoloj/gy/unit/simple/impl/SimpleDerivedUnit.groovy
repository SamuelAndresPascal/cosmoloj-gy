package com.cosmoloj.gy.unit.simple.impl

import com.cosmoloj.gy.unit.simple.api.DerivedUnit
import com.cosmoloj.gy.unit.simple.api.Factor
import com.cosmoloj.gy.unit.simple.api.UnitConverter

class SimpleDerivedUnit extends SimpleUnit implements DerivedUnit {

    private final List<Factor> definition

    private SimpleDerivedUnit(final List<Factor> definition) {
        this.definition = Collections.unmodifiableList(definition)
    }

    @Override
    List<Factor> definition() {
        definition
    }

    @Override
    UnitConverter toBase() {
        /*
        En combinaison avec d'autres unités, il ne faut plus appliquer de décalage d'origine d'échelle (température)
        mais uniquement appliquer le facteur d'échelle.
        */
        UnitConverter transform = SimpleUnitConverter.identity();
        for (final Factor factor : this.definition) {
            transform = factor.dim().toBase().linearPow(factor.power()).concatenate(transform);
        }
        return transform;
    }

    static DerivedUnit of(final List<Factor> definition) {
        return new SimpleDerivedUnit(definition)
    }

    static DerivedUnit of(final Factor... definition) {
        return of(List.of(definition))
    }
}
