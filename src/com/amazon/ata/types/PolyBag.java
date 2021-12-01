package com.amazon.ata.types;

import java.math.BigDecimal;

public class PolyBag extends Packaging {

    public static final Material material = Material.LAMINATED_PLASTIC;
    private BigDecimal volume;
    /**
     * Instantiates a new Packaging object.
     *
     * @param material - the Material of the package
     * @param length - the length of the package
     * @param width - the width of the package
     * @param height - the height of the package
     *
     */
    public PolyBag(Material material, BigDecimal length, BigDecimal width, BigDecimal height) {
        super(material);
        this.volume = length.multiply(width).multiply(height);
    }

    @Override
    public boolean canFitItem(Item item) {
        return this.volume.compareTo(item.getLength().multiply(item.getHeight()).multiply(item.getWidth())) > 0;
    }

    @Override
    public BigDecimal getMass() {

        double vol = volume.doubleValue();
        double mass = Math.ceil(Math.sqrt(vol) * 0.6);

        return BigDecimal.valueOf(mass);
    }
}
