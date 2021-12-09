package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

public class PolyBag extends Packaging {

    public static final Material material = Material.LAMINATED_PLASTIC;
    private BigDecimal volume;
    /**
     * Instantiates a new Packaging object.
     *
     * @param material - the Material of the package
    // * @param length - the length of the package
    // * @param width - the width of the package
    // * @param height - the height of the package
     * @param volume - the volume of the package
     *
     */

    public PolyBag(Material material, BigDecimal volume) {
        super(material);
        this.volume = volume;
    }

    public BigDecimal getVolume() {
        return volume;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        PolyBag polyBag = (PolyBag) o;
        return Objects.equals(volume, polyBag.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volume);
    }
}
