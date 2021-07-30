package fr.enissay.dqluck.items;

import fr.enissay.dqluck.utils.Logger;

public class DQItem {

    private String name;
    private ItemRarity rarity;
    private ItemType type;
    private double chance;
    private boolean shouldChangeRarity;

    public DQItem(String name, ItemRarity rarity, ItemType type, double chance, boolean shouldChangeRarity) {
        this.name = name;
        this.rarity = rarity;
        this.type = type;
        this.chance = chance;
        this.shouldChangeRarity = shouldChangeRarity;
    }

    public String getName() {
        return name;
    }

    public ItemRarity getRarity() {
        return rarity;
    }

    public double getChance() {
        return chance;
    }

    public ItemType getType() {
        return type;
    }

    public boolean shouldChangeRarity() {
        return shouldChangeRarity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRarity(ItemRarity rarity) {
        this.rarity = rarity;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public void setShouldChangeRarity(boolean shouldChangeRarity) {
        this.shouldChangeRarity = shouldChangeRarity;
    }

    public String toDrop(){
        return rarity.getColor() + name + Logger.cleanCodes("/rs/");
    }
}
