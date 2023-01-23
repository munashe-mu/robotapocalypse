package com.umlimi.robotapocalypse.enums;

/**
 * @author MUNASHE MURIMI
 * @created 19/01/2023
 */
public enum InventoryType {
    /**
     * Type of food measured in kilograms
     */
    FOOD("food", "kgs"),
    /**
     * Type of water measured in litres
     */
    WATER("water", "litres"),
    /**
     * Type of ammunition with single unit of measure
     */
    AMMUNITION("ammunition", "single"),
    /**
     * Type of medication and with single unit of measure
     */
    MEDICATION("medication", "single");

    private final String inventoryType;
    private final String unitOfMeasure;

    InventoryType(String inventoryType, String unitOfMeasure) {
        this.inventoryType = inventoryType;
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }
}
