package fr.enissay.dqluck.items;

import org.fusesource.jansi.Ansi;

public enum ItemRarity {

    COMMON("Common", Ansi.ansi().fg(Ansi.Color.WHITE)),
    UNCOMMON("Uncommon", Ansi.ansi().fg(Ansi.Color.GREEN)),
    RARE("Rare", Ansi.ansi().fg(Ansi.Color.CYAN)),
    EPIC("Epic", Ansi.ansi().fg(Ansi.Color.MAGENTA)),
    LEGENDARY("Legendary", Ansi.ansi().fgRgb(255, 127, 0));

    private String name;
    private Ansi color;

    ItemRarity(final String name, Ansi color){
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Ansi getColor() {
        return color;
    }
}
