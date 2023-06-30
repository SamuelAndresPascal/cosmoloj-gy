package com.cosmoloj.gy.unit.simple.impl

import com.cosmoloj.gy.unit.simple.api.FundamentalUnit
import com.cosmoloj.gy.unit.simple.api.UnitConverter

class SimpleFundamentalUnit extends SimpleUnit implements FundamentalUnit {

    @Override
    UnitConverter toBase() {
        SimpleUnitConverter.identity()
    }
}
