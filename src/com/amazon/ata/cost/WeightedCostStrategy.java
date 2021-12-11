package com.amazon.ata.cost;

import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class WeightedCostStrategy implements CostStrategy {

    private Map<BigDecimal, CostStrategy> costStrategyWeightMap = new HashMap<>();

    /**
     *
     * @param costStrategyWeightMap Takes in a costStrategy and weight map parameter
     *
     */

    public WeightedCostStrategy(Map<BigDecimal, CostStrategy> costStrategyWeightMap) {
        this.costStrategyWeightMap.putAll(costStrategyWeightMap);
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {

        BigDecimal blendedCost = BigDecimal.valueOf(0);

        for (Map.Entry<BigDecimal, CostStrategy> entry : costStrategyWeightMap.entrySet()) {
            CostStrategy strategy = entry.getValue();
            BigDecimal weight = entry.getKey();
            BigDecimal costStrategy = strategy.getCost(shipmentOption).getCost().multiply(weight);
            blendedCost = blendedCost.add(costStrategy);
        }
        return new ShipmentCost(shipmentOption, blendedCost);
    }
}
