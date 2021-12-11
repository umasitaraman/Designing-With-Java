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
     * @param builder Takes in a builder parameter
     *
     */

    //public WeightedCostStrategy(Map<BigDecimal, CostStrategy> costStrategyWeightMap) {
    //  this.costStrategyWeightMap.putAll(costStrategyWeightMap);
    //  }

    public WeightedCostStrategy(Builder builder) {
        this.costStrategyWeightMap.putAll(builder.costStrategyWeightMap);
    }

    /**
     *
     * @return returns a new Builder for the WeightedCostStrategy Class
     */

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {
        private Map<BigDecimal, CostStrategy> costStrategyWeightMap = new HashMap<>();

        /**
         * Sets the {@code strategies} and returns a reference to this Builder
         * so that the methods can be chained together.
         *
         * @param costStrategy the {@code strategies} to put in Map as Key
         * @param weight the {@code strategies} to put in Map as Value
         * @return a reference to this Builder
         */

        public Builder addStrategyWithWeight(CostStrategy costStrategy, BigDecimal weight) {
            this.costStrategyWeightMap.put(weight, costStrategy);
            return this;
        }

        /**
         *
         * @return returns a new WeightedCostStrategy
         */

        public WeightedCostStrategy build() {
            return new WeightedCostStrategy(this);
        }

    }
}
