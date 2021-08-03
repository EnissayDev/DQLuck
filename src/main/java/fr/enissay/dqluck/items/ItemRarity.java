package fr.enissay.dqluck.items;

import org.fusesource.jansi.Ansi;

public enum ItemRarity {

    COMMON("Common", Ansi.ansi().fg(Ansi.Color.WHITE), 0.08),
    UNCOMMON("Uncommon", Ansi.ansi().fg(Ansi.Color.GREEN), 0.05),
    RARE("Rare", Ansi.ansi().fg(Ansi.Color.CYAN), 0.03),
    EPIC("Epic", Ansi.ansi().fg(Ansi.Color.MAGENTA), 0.01),
    LEGENDARY("Legendary", Ansi.ansi().fgRgb(255, 127, 0), 0.0);

    private String name;
    private Ansi color;
    private double chance;

    ItemRarity(final String name, Ansi color, double chance){
        this.name = name;
        this.color = color;
        this.chance = chance;
    }

    public String getName() {
        return name;
    }

    public Ansi getColor() {
        return color;
    }

    public double getChance() {
        return chance;
    }
}
