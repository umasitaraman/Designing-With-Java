package com.amazon.ata.cost;

import com.amazon.ata.types.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeightedCostStrategyTest {

    private static final Box BOX_10x10x20 =
            new Box(Material.CORRUGATE, BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(20));

    private static final PolyBag POLYBAG_10x10x20 =
            new PolyBag(Material.LAMINATED_PLASTIC, BigDecimal.valueOf(2000));

    private WeightedCostStrategy strategy;
    private Map<BigDecimal, CostStrategy> costStrategyWeightMap = new HashMap<>();


    @BeforeEach
    void setUp() {

        costStrategyWeightMap.put(BigDecimal.valueOf(0.80), new MonetaryCostStrategy());
        costStrategyWeightMap.put(BigDecimal.valueOf(0.20), new CarbonCostStrategy());
        //strategy = new WeightedCostStrategy(costStrategyWeightMap);
        strategy = new WeightedCostStrategy.Builder().addStrategyWithWeight(new MonetaryCostStrategy(), BigDecimal.valueOf(0.80))
                .addStrategyWithWeight(new CarbonCostStrategy(), BigDecimal.valueOf(0.20)).build();

    }

    @Test
    void getCost_corrugateMaterial_returnsCorrectBlendedCost() {
        // GIVEN
        ShipmentOption option = ShipmentOption.builder()
                .withPackaging(BOX_10x10x20)
                .build();

        // WHEN
        ShipmentCost shipmentCost = strategy.getCost(option);

        // THEN
        assertTrue(BigDecimal.valueOf(7.7440).compareTo(shipmentCost.getCost()) == 0,
                "Incorrect monetary cost calculation for a box with dimensions 10x10x20.");
    }

    @Test
    void getCost_laminatedPlasticMaterial_returnsCorrectCost() {
        // GIVEN
        ShipmentOption option = ShipmentOption.builder()
                .withPackaging(POLYBAG_10x10x20)
                .build();

        // WHEN
        ShipmentCost shipmentCost = strategy.getCost(option);

        // THEN
        assertTrue(BigDecimal.valueOf(5.80880).compareTo(shipmentCost.getCost()) == 0,
                "Incorrect monetary cost calculation for a PolyBag with dimensions 10x10x20.");
    }
}
