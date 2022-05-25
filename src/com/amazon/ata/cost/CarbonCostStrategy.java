package com.amazon.ata.cost;

import com.amazon.ata.types.Material;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class CarbonCostStrategy implements CostStrategy {

    private static final BigDecimal SUSTAINABILITY_INDEX_CORRUGATE = BigDecimal.valueOf(0.017);
    private static final BigDecimal SUSTAINABILITY_INDEX_LAMINATED_PLASTIC = BigDecimal.valueOf(0.012);
    private final Map<Material, BigDecimal> carbonCostPerGram;

    /**
     * Initializes a CarbonCostStrategy.
     */
    public CarbonCostStrategy() {
        carbonCostPerGram = new HashMap<>();
        carbonCostPerGram.put(Material.CORRUGATE, SUSTAINABILITY_INDEX_CORRUGATE);
        carbonCostPerGram.put(Material.LAMINATED_PLASTIC, SUSTAINABILITY_INDEX_LAMINATED_PLASTIC);
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        Packaging packaging = shipmentOption.getPackaging();
        BigDecimal sustainabilityIndex = this.carbonCostPerGram.get(packaging.getMaterial());
        BigDecimal carbonCost = packaging.getMass().multiply(sustainabilityIndex);

        return new ShipmentCost(shipmentOption, carbonCost);
    }
}
