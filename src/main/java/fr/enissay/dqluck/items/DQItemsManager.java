package fr.enissay.dqluck.items;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DQItemsManager {

    private static LinkedList<DQItem> items = new LinkedList<>();

    public void addItem(final DQItem dqItem){
        if (!items.contains(dqItem)) items.add(dqItem);
    }

    public void addItems(final DQItem... dqItems){
        Arrays.stream(dqItems).forEach(item -> addItem(item));
    }

    public LinkedList<DQItem> getItems(){
        return items;
    }

    public List<DQItem> getItems(final ItemRarity rarity){
        return items.stream()
                .filter(item -> item.getRarity() == rarity).collect(Collectors.toList());
    }
}
