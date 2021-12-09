package com.amazon.ata.cost;

import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;

public class WeightedCostStrategy implements CostStrategy {

    private MonetaryCostStrategy monetaryCostStrategy;
    private CarbonCostStrategy carbonCostStrategy;

    /**
     *
     * @param monetaryCostStrategy Takes in a monetaryCostStrategy parameter
     * @param carbonCostStrategy Takes in a carbonCostStrategy parameter
     */

    public WeightedCostStrategy(MonetaryCostStrategy monetaryCostStrategy, CarbonCostStrategy carbonCostStrategy) {
        this.monetaryCostStrategy = monetaryCostStrategy;
        this.carbonCostStrategy = carbonCostStrategy;
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        Packaging packaging = shipmentOption.getPackaging();
        ShipmentCost monetaryCost = monetaryCostStrategy.getCost(shipmentOption);
        ShipmentCost carbonCost = carbonCostStrategy.getCost(shipmentOption);

        BigDecimal blendedCost = (monetaryCost.getCost().multiply(BigDecimal.valueOf(0.80)))
                .add(carbonCost.getCost().multiply(BigDecimal.valueOf(0.20)));

        return new ShipmentCost(shipmentOption, blendedCost);
    }
}
