package fr.enissay.dqluck.run;

import fr.enissay.dqluck.items.DQItem;

import java.util.LinkedList;

public class DQRun {

    private boolean withExtraItem;
    private LinkedList<DQItem> items;

    public DQRun(boolean withExtraItem, LinkedList<DQItem> items) {
        this.withExtraItem = withExtraItem;
        this.items = items;
    }

    public boolean isWithExtraItem() {
        return withExtraItem;
    }

    public void setWithExtraItem(boolean withExtraItem) {
        this.withExtraItem = withExtraItem;
    }

    public LinkedList<DQItem> getItems() {
        return items;
    }

    public void setItems(LinkedList<DQItem> items) {
        this.items = items;
    }
}
