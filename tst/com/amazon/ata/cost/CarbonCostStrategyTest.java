package com.amazon.ata.cost;

import com.amazon.ata.types.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarbonCostStrategyTest {

    private static final Box BOX_10x10x20 =
            new Box(Material.CORRUGATE, BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(20));

    private static final PolyBag POLYBAG_10x10x20 =
            new PolyBag(Material.LAMINATED_PLASTIC, BigDecimal.valueOf(2000));

    private CarbonCostStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new CarbonCostStrategy();
    }

    @Test
    void getCost_corrugateMaterial_returnsCorrectCost() {
        // GIVEN
        ShipmentOption option = ShipmentOption.builder()
                .withPackaging(BOX_10x10x20)
                .build();

        // WHEN
        ShipmentCost shipmentCost = strategy.getCost(option);

        // THEN
        assertTrue(BigDecimal.valueOf(17.000).compareTo(shipmentCost.getCost()) == 0,
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
        assertTrue(BigDecimal.valueOf(0.3240).compareTo(shipmentCost.getCost()) == 0,
                "Incorrect monetary cost calculation for a PolyBag with dimensions 10x10x20.");
    }
}
