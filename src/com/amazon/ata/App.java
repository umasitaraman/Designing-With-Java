package com.amazon.ata;

import com.amazon.ata.cost.CarbonCostStrategy;
import com.amazon.ata.cost.CostStrategy;
import com.amazon.ata.cost.MonetaryCostStrategy;
import com.amazon.ata.cost.WeightedCostStrategy;
import com.amazon.ata.dao.PackagingDAO;
import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.service.ShipmentService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class App {
    /* don't instantiate me */
    private App() {}

    private static PackagingDatastore getPackagingDatastore() {
        return new PackagingDatastore();
    }

    private static PackagingDAO getPackagingDAO() {
        return new PackagingDAO(getPackagingDatastore());
    }

    private static CostStrategy getCostStrategy() {
        Map<BigDecimal, CostStrategy> costStrategyWeightMap = new HashMap<>();
        costStrategyWeightMap.put(BigDecimal.valueOf(0.80), new MonetaryCostStrategy());
        costStrategyWeightMap.put(BigDecimal.valueOf(0.20), new CarbonCostStrategy());
        //return new WeightedCostStrategy(new MonetaryCostStrategy(), new CarbonCostStrategy());
        return new WeightedCostStrategy(costStrategyWeightMap);
    }

    public static ShipmentService getShipmentService() {
        return new ShipmentService(getPackagingDAO(), getCostStrategy());
    }
}
