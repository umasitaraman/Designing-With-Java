package com.amazon.ata.types;

import java.math.BigDecimal;
//import java.util.Objects;

/**
 * Represents a packaging option.
 *
 * This packaging supports standard boxes, having a length, width, and height.
 * Items can fit in the packaging so long as their dimensions are all smaller than
 * the packaging's dimensions.
 */
public class Packaging {
    /**
     * The material this packaging is made of.
     */
    private Material material;

    /**
     * This packaging's length.
     */
    //private BigDecimal length;

    /**
     * This packaging's smallest dimension.
     */
    //private BigDecimal width;

    /**
     * This packaging's largest dimension.
     */
    //private BigDecimal height;

    /**
     * Instantiates a new Packaging object.
     * @param material - the Material of the package
     * @param length - the length of the package
     * @param width - the width of the package
     * @param height - the height of the package
     */
    //    public Packaging(Material material, BigDecimal length, BigDecimal width, BigDecimal height) {
    //        this.material = material;
    //        this.length = length;
    //        this.width = width;
    //        this.height = height;
    //    }

    /**
     *
     * @param material Constructor that just takes in the material type.
     */

    public Packaging(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    /**
     * Returns whether the given item will fit in this packaging.
     *
     * @param item the item to test fit for
     * @return whether the item will fit in this packaging
     * */

    public boolean canFitItem(Item item) {
        return false;
    }

    /**
     * Returns the mass of the packaging in grams. The packaging weighs 1 gram per square centimeter.
     * @return the mass of the packaging
     */
    public BigDecimal getMass() {
        return BigDecimal.valueOf(0);
    }

    @Override
    public boolean equals(Object o) {
        // Can't be equal to null
        if (o == null) {
            return false;
        }

        // Referentially equal
        if (this == o) {
            return true;
        }

        // Check if it's a different type
        if (getClass() != o.getClass()) {
            return false;
        }

        Packaging packaging = (Packaging) o;
        return getMaterial() == packaging.getMaterial();
    }

    //@Override
    //public int hashCode() {
    //    return Objects.hash(getMaterial(), this.getlength(), this.getwidth(), this.getheight());
    //}
}
