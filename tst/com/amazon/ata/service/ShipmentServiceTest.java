package com.amazon.ata.service;

import com.amazon.ata.cost.CostStrategy;
import com.amazon.ata.cost.MonetaryCostStrategy;
import com.amazon.ata.dao.PackagingDAO;
import com.amazon.ata.exceptions.NoPackagingFitsItemException;
import com.amazon.ata.exceptions.UnknownFulfillmentCenterException;
import com.amazon.ata.types.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ShipmentServiceTest {

    private Item smallItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1))
            .withWidth(BigDecimal.valueOf(1))
            .withLength(BigDecimal.valueOf(1))
            .withAsin("abcde")
            .build();

    private Item largeItem = Item.builder()
            .withHeight(BigDecimal.valueOf(1000))
            .withWidth(BigDecimal.valueOf(1000))
            .withLength(BigDecimal.valueOf(1000))
            .withAsin("12345")
            .build();

    private FulfillmentCenter existentFC = new FulfillmentCenter("ABE2");
    private FulfillmentCenter nonExistentFC = new FulfillmentCenter("NonExistentFC");
    private Packaging packaging  = new PolyBag(Material.LAMINATED_PLASTIC, BigDecimal.valueOf(100));
    BigDecimal materialCost = BigDecimal.valueOf(00.25);
    BigDecimal LABOR_COST = BigDecimal.valueOf(0.43);
    BigDecimal cost = packaging.getMass().multiply(materialCost).add(LABOR_COST);

    @InjectMocks
    private ShipmentService shipmentService;

    @Mock
    private PackagingDAO packagingDAO;

    @Mock
    private CostStrategy costStrategy;
    //private CostStrategy costStrategy = new MonetaryCostStrategy();

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void findBestShipmentOption_existentFCAndItemCanFit_returnsShipmentOption()
            throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {

        // GIVEN & WHEN
        //shipmentService = new ShipmentService(packagingDAO, costStrategy);

        ShipmentOption shipmentOption = ShipmentOption.builder()
                .withItem(smallItem)
                .withPackaging(packaging)
                .withFulfillmentCenter(existentFC)
                .build();

        ShipmentCost shipmentCost = new ShipmentCost(shipmentOption, cost);

        List<ShipmentOption> shipmentOptionList = new ArrayList<>();
        shipmentOptionList.add(shipmentOption);

        when(packagingDAO.findShipmentOptions(smallItem, existentFC)).thenReturn(shipmentOptionList);
        when(costStrategy.getCost(shipmentOption)).thenReturn(shipmentCost);

        ShipmentOption shipmentOption1 = shipmentService.findShipmentOption(smallItem, existentFC);

        // THEN
        assertEquals(shipmentOption1.getClass(), ShipmentOption.class, "the method should return a shipment option");
    }

    @Test
    void findBestShipmentOption_existentFCAndItemCannotFit_returnsShipmentOption()
            throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        //shipmentService = new ShipmentService(packagingDAO, new MonetaryCostStrategy());


        ShipmentOption shipmentOption = ShipmentOption.builder()
                .withItem(largeItem)
                .withFulfillmentCenter(existentFC)
                .build();

        when(packagingDAO.findShipmentOptions(largeItem, existentFC)).thenThrow(new NoPackagingFitsItemException());

        ShipmentOption shipmentOption1 = shipmentService.findShipmentOption(largeItem, existentFC);

        // THEN
        assertEquals(shipmentOption.getClass(), shipmentOption1.getClass(), "The Method returns a valid shipmentOption");
        assertEquals(shipmentOption.getItem(), shipmentOption1.getItem(), "The Method returns a valid shipment with the appropriate item");
        assertEquals(shipmentOption.getFulfillmentCenter(), shipmentOption1.getFulfillmentCenter(), "The Method returns a valid shipment with the appropriate fulfillment center");
        assertNull(shipmentOption1.getPackaging(), "The Method returns a valid shipment with null packaging");
    }

    @Test
    void findBestShipmentOption_nonExistentFCAndItemCanFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        //shipmentService = new ShipmentService(packagingDAO, new MonetaryCostStrategy());

        when(packagingDAO.findShipmentOptions(smallItem, nonExistentFC)).thenThrow(new UnknownFulfillmentCenterException());

        // THEN
        assertThrows(RuntimeException.class, () -> {
            shipmentService.findShipmentOption(smallItem, nonExistentFC);
        }, "When no FC Exists , a RuntimeException should be thrown");
    }

    @Test
    void findBestShipmentOption_nonExistentFCAndItemCannotFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN & WHEN
        //shipmentService = new ShipmentService(packagingDAO, new MonetaryCostStrategy());

        when(packagingDAO.findShipmentOptions(largeItem, nonExistentFC)).thenThrow(new UnknownFulfillmentCenterException());

        // THEN
        assertThrows(RuntimeException.class, () -> {
            shipmentService.findShipmentOption(largeItem, nonExistentFC);
        }, "When no FC Exists , a RuntimeException should be thrown");
    }
}