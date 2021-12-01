package com.amazon.ata.types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PolyBagTest {
    private Material packagingMaterial = Material.LAMINATED_PLASTIC;
    private BigDecimal packagingLength = BigDecimal.valueOf(5.6);
    private BigDecimal packagingWidth = BigDecimal.valueOf(3.3);
    private BigDecimal packagingHeight = BigDecimal.valueOf(8.1);
    private BigDecimal volume = (packagingLength.multiply(packagingWidth).multiply(packagingHeight));


    private Packaging packaging;

    @BeforeEach
    public void setUp() {
        packaging = new PolyBag(packagingMaterial, volume);
    }

    @Test
    public void canFitItem_itemLengthTooLong_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
                .withLength(packagingLength.add(BigDecimal.ONE))
                .withWidth(packagingWidth)
                .withHeight(packagingHeight)
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item with longer length than package should not fit in the package.");
    }

    @Test
    public void canFitItem_itemWithGreaterVolume_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
                .withLength(packagingLength)
                .withWidth(packagingWidth.add(BigDecimal.ONE))
                .withHeight(packagingHeight)
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item with longer width than package should not fit in the package.");
    }

    @Test
    public void canFitItem_itemSameVolumeAsPolyBag_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
                .withLength(packagingLength)
                .withWidth(packagingWidth)
                .withHeight(packagingHeight)
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item the same size as the package should not fit in the package.");
    }

    @Test
    public void canFitItem_itemWithLesserVolume_doesFit() {
        // GIVEN
        Item item = Item.builder()
                .withLength(packagingLength.subtract(BigDecimal.ONE))
                .withWidth(packagingWidth.subtract(BigDecimal.ONE))
                .withHeight(packagingHeight.subtract(BigDecimal.ONE))
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertTrue(canFit, "Item smaller than the package should fit in the package.");
    }

    @Test
    public void getMass_calculatesMass_returnsCorrectMass() {
        // GIVEN
        packaging = new PolyBag(Material.LAMINATED_PLASTIC, BigDecimal.valueOf(2000));

        // WHEN
        BigDecimal mass = packaging.getMass();

        // THEN
        assertEquals(BigDecimal.valueOf(27.0), mass,
                "getMass() returns the correct Mass as expected");
    }

}
